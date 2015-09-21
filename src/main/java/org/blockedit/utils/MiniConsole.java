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

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.PrintStream;
import java.util.Optional;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;

public class MiniConsole {

    private TextArea area;
    private ConsoleOutputStream output;
    private PrintStream printStream;
    private ContextMenu rightClick;

    public void start() {
        this.area = new TextArea();
        this.area.setWrapText(true);
        this.area.setEditable(false);
        this.area.setPrefColumnCount(150);
        this.output = new ConsoleOutputStream(this.area);
        this.printStream = new PrintStream(this.output, true);
        System.setOut(this.printStream);
        System.setErr(this.printStream);

        this.rightClick = new ContextMenu();
        this.rightClick.setId("debugMenu");
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> {
            if (this.area.getSelectedText().isEmpty()) {
                event.consume();
            } else {
                StringSelection selection = new StringSelection(this.area.getSelectedText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            }
        });
        MenuItem selectAllItem = new MenuItem("Select All");
        selectAllItem.setOnAction(event -> {
            this.area.selectAll();
        });
        MenuItem deselectItem = new MenuItem("Deselect");
        deselectItem.setOnAction(event -> {
            this.area.deselect();
        });
        this.rightClick.getItems().addAll(copyItem, new SeparatorMenuItem(), selectAllItem, deselectItem);
        this.area.setContextMenu(this.rightClick);
        this.area.getStylesheets().add("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800italic,800,700italic,600,600italic,300,300italic&subset=latin,greek-ext,greek,cyrillic,vietnamese,cyrillic-ext,latin-ext");
        this.area.getStylesheets().add("file:///" + new File("src/main/resources/miniconsole.css").getAbsolutePath().replace("\\", "/"));
    }

    public Optional<TextArea> getConsole() {
        if (this.area == null) {
            return Optional.empty();
        }
        return Optional.of(this.area);
    }

    public Optional<ConsoleOutputStream> getConsoleOutputStream() {
        if (this.output == null) {
            return Optional.empty();
        }
        return Optional.of(this.output);
    }

    public Optional<PrintStream> getPrintStream() {
        if (this.printStream == null) {
            return Optional.empty();
        }
        return Optional.of(this.printStream);
    }
}
