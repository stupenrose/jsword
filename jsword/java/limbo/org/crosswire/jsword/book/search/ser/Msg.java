package org.crosswire.jsword.book.search.ser;

import org.crosswire.common.util.MsgBase;

/**
 * Compile safe Msg resource settings.
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
class Msg extends MsgBase
{
    static final Msg INDEX_START = new Msg("SerIndex.Indexing"); //$NON-NLS-1$
    static final Msg SER_INIT = new Msg("SerIndex.Init"); //$NON-NLS-1$
    static final Msg DELETE_FAILED = new Msg("SerIndex.DeleteFailed"); //$NON-NLS-1$
    static final Msg WRITE_ERROR = new Msg("SerIndex.WriteError"); //$NON-NLS-1$
    static final Msg FINDING_WORDS = new Msg("SerIndex.FindingWords"); //$NON-NLS-1$
    static final Msg WRITING_WORDS = new Msg("SerIndex.WritingWords"); //$NON-NLS-1$
    static final Msg SAVING = new Msg("SerIndex.Saving"); //$NON-NLS-1$
    static final Msg INSTALL_FAIL = new Msg("SerIndex.InstallFailed"); //$NON-NLS-1$

    /**
     * Passthrough ctor 
     */
    private Msg(String name)
    {
        super(name);
    }
}