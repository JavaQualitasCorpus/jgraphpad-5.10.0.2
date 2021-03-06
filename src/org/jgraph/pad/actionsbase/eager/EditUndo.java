/*
 * @(#)EditUndo.java	1.2 30.01.2003
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.undo.CannotUndoException;

import org.jgraph.pad.jgraphpad.GPAction;
import org.microplatform.BarFactory;
import org.microplatform.loaders.Translator;

public class EditUndo extends GPAction {

	protected Vector menuItems = new Vector();

	public void actionPerformed(ActionEvent e) {
		try {
			getCurrentGPDocument().getGraphUndoManager().undo(
				getCurrentGPDocument().getGraph().getGraphLayoutCache());
		} catch (CannotUndoException ex) {
			System.out.println("Unable to graphUndoManager: " + ex);
			ex.printStackTrace();
		}
		mdiContainer.update();
	}

	public void update() {
		Enumeration e_num = menuItems.elements();

		while (e_num.hasMoreElements()) {
			JMenuItem item = (JMenuItem) e_num.nextElement();
			if (getCurrentGPDocument() != null &&
				getCurrentGPDocument().getGraphUndoManager().canUndo(getCurrentGraphLayoutCache())) {
				setEnabled(true);
				item.setText(
					getCurrentGPDocument().getGraphUndoManager().getUndoPresentationName());
			} else {
				setEnabled(false);
				item.setText(Translator.getString("Component.EditUndo.Text"));
			}
		}
	}

	/**
	 * @see org.jgraph.pad.actionsbase.lazy.GPAction#getMenuComponent(String)
	 */
	protected Component getMenuComponent(String actionCommand) {
		JMenuItem item = new JMenuItem(this);

		BarFactory.fillMenuButton(item, getName(), actionCommand);

		menuItems.add(item);
		return item;
	}

}
