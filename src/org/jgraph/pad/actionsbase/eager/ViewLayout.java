/*
 * @(#)ViewLayout.java	1.2 02.02.2003
 *
 * Copyright (C) 2001-2004 Gaudenz Alder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.jgraph.pad.actionsbase.eager;

import java.awt.event.ActionEvent;


public class ViewLayout extends AbstractActionRadioButton {

	public static final String NORMAL = "Normal";

	public static final String PAGE = "Page";

	/**
	 * Constructor for ViewLayout.
	 * @param graphpad
	 */
	public ViewLayout() {
		super();
		lastActionCommand = NORMAL;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.lastActionCommand = e.getActionCommand() ;

		if ( PAGE.equals(e.getActionCommand() ))
			getCurrentGPDocument().setPageVisible(true);
		else
			getCurrentGPDocument().setPageVisible(false);

		getCurrentGPDocument().updatePageFormat();
		update();
	}

	/**
	 * @see org.jgraph.pad.actionsbase.eager.AbstractActionRadioButton#getPossibleActionCommands()
	 */
	public String[] getPossibleActionCommands() {
		return new String[]{NORMAL, PAGE};
	}

}
