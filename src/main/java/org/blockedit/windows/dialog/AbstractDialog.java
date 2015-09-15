/*
BlockEdit, a general Minecraft program that is in heavy development
    Copyright (C) 2015  Jeff Chen

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockedit.windows.dialog;

import javafx.stage.Stage;

/**
 * Represents the structure of a dialog that is used to do various things for the user.
 *
 * @author Jeff Chen
 */
public abstract class AbstractDialog {

    /**
     * Get or create a new dialog.
     *
     * @param width The window width
     * @param height The window height
     * @return The created dialog
     */
    public abstract Stage getOrCreateDialog(double width, double height);
}
