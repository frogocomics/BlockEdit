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
package org.blockedit.utils;

import com.google.common.annotations.Beta;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A simple class that contains methods for display exceptions to users in a clean manner.
 *
 * @author Jeff Chen
 */
public class ExceptionDialog {

    /**
     * Create a exception alert that is displayed to the user.
     *
     * @param message The message to show the user
     * @param title The title of the window
     * @param header The message header
     * @param exception The exception that triggered this window to be displayed to the user
     * @return A created alert
     */
    @Beta
    public static Alert getDialog(String message, String title, String header, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String exceptionText = exception.toString();
        Label label = new Label("The exception stacktrace was:");
        TextArea exceptionArea = new TextArea(exceptionText);
        exceptionArea.setEditable(false);
        exceptionArea.setWrapText(true);
        exceptionArea.setMaxWidth(UserInformation.getWindowWidth() / 2);
        exceptionArea.setMaxHeight(UserInformation.getWindowHeight() / 2);
        GridPane.setVgrow(exceptionArea, Priority.ALWAYS);
        GridPane.setHgrow(exceptionArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(UserInformation.getWindowWidth() / 2);
        expContent.add(label, 0, 0);
        expContent.add(exceptionArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        return alert;
    }
}
