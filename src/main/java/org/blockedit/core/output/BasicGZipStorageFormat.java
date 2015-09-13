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

package org.blockedit.core.output;

import org.blockedit.utils.Format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BasicGZipStorageFormat implements Format {

    @Override
    public void write(File file, String s) throws IOException, NullPointerException {
        if (s == null || s.isEmpty()) {
            throw new NullPointerException("String s is null!");
        }
        BufferedWriter writer;
        GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(file));
        writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));
        writer.append(s);
        if(writer != null) {
            writer.close();
        }
        if(zip != null) {
            zip.close();
        }
    }

    @Override
    public String read(File file) throws IOException {
        GZIPInputStream zip = new GZIPInputStream(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(zip, "UTF-8"));
        String outString = "";
        String line;
        while((line = reader.readLine()) != null) {
            outString += line;
        }
        zip.close();
        reader.close();
        return outString;
    }
}
