/*
 * BlockEdit, a general purpose software to edit Minecraft
 * Copyright (c) 2015. Jeff Chen and others
 *
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.blockedit.utils;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

public class ConsoleOutputStream extends OutputStream {

    private TextArea output;

    public ConsoleOutputStream(TextArea area) {
        this.output = area;
    }

    @Override
    public void write(int i) throws IOException {
        output.appendText(String.valueOf((char) i));
    }
}
