package org.crosswire.jsword.book.search.ser;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.crosswire.common.util.Logger;
import org.crosswire.common.util.NetUtil;
import org.crosswire.common.util.Reporter;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.search.Index;
import org.crosswire.jsword.book.search.IndexManager;
import org.crosswire.jsword.util.Project;

/**
 * .
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public class SerIndexManager implements IndexManager
{
    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.search.AbstractIndex#isIndexed()
     */
    public boolean isIndexed(Book book)
    {
        try
        {
            URL storage = getStorageArea(book);
            URL longer = NetUtil.lengthenURL(storage, SerIndex.FILE_INDEX);
            return NetUtil.isFile(longer);
        }
        catch (IOException ex)
        {
            log.error("Failed to find lucene index storage area.", ex); //$NON-NLS-1$
            return false;
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.search.IndexManager#getIndex(org.crosswire.jsword.book.Book)
     */
    public Index getIndex(Book book) throws BookException
    {
        try
        {
            Index reply = (Index) indexes.get(book);
            if (reply == null)
            {
                URL storage = getStorageArea(book);
                reply = new SerIndex(book, storage);
                indexes.put(book, reply);
            }

            return reply;
        }
        catch (IOException ex)
        {
            throw new BookException(Msg.SER_INIT, ex);
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.search.AbstractIndex#generateSearchIndex(org.crosswire.common.progress.Job)
     */
    public void scheduleIndexCreation(final Book book)
    {
        Thread work = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    URL storage = getStorageArea(book);
                    Index index = new SerIndex(book, storage, true);
                    indexes.put(book, index);
                }
                catch (Exception ex)
                {
                    Reporter.informUser(SerIndexManager.this, ex);
                }
            }
        });
        work.start();
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.search.IndexManager#deleteIndex(org.crosswire.jsword.book.Book)
     */
    public void deleteIndex(Book book) throws BookException
    {
        try
        {
            // TODO(joe): This needs some checks that it isn't being used
            URL storage = getStorageArea(book);
            NetUtil.delete(storage);
        }
        catch (IOException ex)
        {
            throw new BookException(Msg.DELETE_FAILED, ex);
        }
    }

    /**
     * Determine where an index should be stored
     * @param book The book to be indexed
     * @return A URL to store stuff in
     * @throws IOException If there is a problem in finding where to store stuff
     */
    protected URL getStorageArea(Book book) throws IOException
    {
        String driverName = book.getBookMetaData().getDriverName();
        String bookName = book.getBookMetaData().getInitials();

        assert driverName != null;
        assert bookName != null;

        URL base = Project.instance().getTempScratchSpace(DIR_SER, false);
        URL driver = NetUtil.lengthenURL(base, driverName);

        return NetUtil.lengthenURL(driver, bookName);
    }

    /**
     * The created indexes
     */
    protected static final Map indexes = new HashMap();

    /**
     * The ser search index directory
     */
    private static final String DIR_SER = "ser"; //$NON-NLS-1$

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(SerIndexManager.class);
}
