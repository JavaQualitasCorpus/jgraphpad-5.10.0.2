/*
 * Copyright (C) 2001-2004 Gaudenz Alder
 *
 * GPGraphpad is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * GPGraphpad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GPGraphpad; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.microplatform.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import org.microplatform.ActionCommand;
import org.microplatform.io.ExtensionFilter;
import org.microplatform.io.FileService;
import org.microplatform.io.PreferencesService;
import org.microplatform.loaders.Translator;
import org.microplatform.loaders.PluginInvoker.NamedInputStream;
import org.microplatform.loaders.PluginInvoker.NamedInputStreamProvider;

public class FileOpen extends ActionCommand implements NamedInputStreamProvider {

	public void actionPerformed(ActionEvent e) {
		NamedInputStream input = provideInput(Translator.getString("FileExtension"));
		String newDocName = input.name;
		InputStream newIn = null;
		if (newDocName !=null && newDocName.endsWith("gz") || newDocName.indexOf(".") == -1) {
			try {
				newIn = new GZIPInputStream(input.in);
			} catch (IOException e1) {
				newIn = input.in;
			}
			
		}
		if (input != null) {
			mdiContainer.addDocument(newDocName, newIn);
		}
	}
	
	public NamedInputStream provideInput(String fileExtension) {
		PreferencesService preferences = PreferencesService
				.getInstance(FileOpen.class);
		ExtensionFilter fileExtensionFilter = new ExtensionFilter(Translator
				.getString("DefaultFileName"), new String[] { fileExtension });

		ArrayList recentFiles = new ArrayList();
		File lastDir = new File(".");
		String recent = preferences.get("recent", "").trim();
		if (recent.length() > 0) {
			recentFiles.addAll(Arrays.asList(recent.split("[|]")));
			lastDir = new File((String) recentFiles.get(0)).getParentFile();
		}
		FileService fileService = FileService.getInstance(lastDir);
		try {
			FileService.Open open = fileService.open(null, null,
					fileExtensionFilter);
            return new NamedInputStream(open.getInputStream(), open.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Empty implementation. This Action should be available each time.
	 */
	public void update() {
	};
}
