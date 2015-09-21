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

package org.blockedit.windows.dialog;

import org.blockedit.utils.UserInformation;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomBlocksDialog extends AbstractDialog {

    private VBox root = new VBox();
    private TableView blocks = new TableView();

    @Override
    @SuppressWarnings("unchecked")
    public Stage getOrCreateDialog(double width, double height) {
        Stage stage = new Stage();
        Scene scene = new Scene(this.root);
        stage.setResizable(false);
        stage.setWidth(UserInformation.getWindowWidth() / 2);
        stage.setHeight(UserInformation.getWindowHeight() / 2);
        stage.setTitle("BlockEdit \u2012 Custom Blocks");
        stage.initModality(Modality.APPLICATION_MODAL);

        this.blocks.setEditable(false);
        TableColumn path = new TableColumn("Location");
        TableColumn block = new TableColumn("Block");
        this.blocks.getColumns().addAll(path, block);

        path.setCellValueFactory(new PropertyValueFactory("locationProperty"));
        path.setCellValueFactory(new PropertyValueFactory("displayNameProperty"));

        Label title = new Label("Custom Blocks");
        this.root.getChildren().addAll(title, this.blocks);
        stage.setScene(scene);
        return stage;
    }
}
