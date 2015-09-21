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

import org.blockedit.utils.ExceptionDialog;
import org.blockedit.utils.UserInformation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A dialog used to select <a href="http://minecraft.gamepedia.com/Schematic_file_format">.schematic</a> file.
 *
 * @author Jeff Chen
 **/
@ParametersAreNonnullByDefault
public class ImportSchematicDialog extends AbstractDialog {

    private Stage stage = new Stage();
    private Pane pane = new Pane();
    private MenuBar tools = new MenuBar();
    private Button addSchematic = new Button("Add File");

    @Override
    public Stage getOrCreateDialog(double width, double height) {
        GridPane grid = new GridPane();
        Scene schematicDialog = new Scene(this.pane, Color.WHITE);
        this.pane.getChildren().addAll(grid);
        @Nullable final StringBuilder schematicFilePathBuilder = new StringBuilder();
        //<editor-fold desc="Set the menubar">
        Menu getFilesMenu = new Menu("Get Schematics");
        Menu treesMenu = new Menu("Custom Trees");
        MenuItem lentebrijseCustomTree = createLink("http://www.planetminecraft.com/project/native-trees-of-europe-template-repository-1779952/", "Lentebrijse's Custom Tree Repository");
        MenuItem lentebrijseFantasyTree = createLink("http://www.planetminecraft.com/project/fantasy-tree-repository/", "Lentebrijse's Fantasy Tree Repository");
        getFilesMenu.getItems().addAll(treesMenu);
        treesMenu.getItems().addAll(lentebrijseCustomTree, lentebrijseFantasyTree);
        this.tools.getMenus().addAll(getFilesMenu);
        //</editor-fold>
        //<editor-fold desc="Set stage">
        this.stage.setScene(schematicDialog);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
        this.stage.setMinWidth(width / 2);
        this.stage.setMinHeight(width / 2);
        this.stage.setMaxWidth(this.stage.getWidth() + 50);
        this.stage.setMaxHeight(this.stage.getHeight() + 50);
        this.stage.setTitle("BlockEdit \u2012 Import .schematic");
        this.stage.initModality(Modality.WINDOW_MODAL);
        final String resourcePath = "file:///" + System.getProperty("user.dir") + "\\src\\main\\resource\\";
        this.stage.toFront();
        //</editor-fold>
        //<editor-fold desc="Set grid layout">
        Region space = new Region();
        space.setPrefHeight(this.stage.getHeight() / 10);
        grid.add(this.tools, 0, 0);
        grid.add(space, 0, 1);
        grid.add(new Separator(Orientation.HORIZONTAL), 0, 2);
        grid.add(this.addSchematic, 0, 3);
        GridPane.setHgrow(this.tools, Priority.ALWAYS);
        GridPane.setVgrow(this.tools, Priority.ALWAYS);
        GridPane.setHgrow(space, Priority.ALWAYS);
        GridPane.setVgrow(space, Priority.ALWAYS);
        GridPane.setVgrow(this.addSchematic, Priority.ALWAYS);
        GridPane.setHgrow(this.addSchematic, Priority.ALWAYS);
        //</editor-fold>
        this.addSchematic.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Schematic");
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Minecraft Schematic", "*.schematic"));
            File selectedFile = chooser.showOpenDialog(stage);
            if (selectedFile == null) {
                event.consume();
            } else {
                schematicFilePathBuilder.append(selectedFile.getAbsolutePath());
                System.out.println(schematicFilePathBuilder.toString());
                System.out.println(new File(schematicFilePathBuilder.toString()).isFile());
            }
        });
        this.tools.setPrefWidth(stage.getWidth());
        return this.stage;
    }

    private MenuItem createLink(String link, String name) {
        MenuItem item = new MenuItem(name);
        item.setOnAction(event -> {
            try {
                if (!UserInformation.hasInternet()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Blockedit \u2012 Warning");
                    alert.setHeaderText("Unable to open the link");
                    alert.setContentText("Your appear to have no internet connection. If you do have internet connection, please check if any firewalls are blocking this program.");
                    alert.setWidth(UserInformation.getWindowHeight() / 3);
                    alert.setHeight(UserInformation.getWindowHeight() / 3);
                    alert.setResizable(false);
                    alert.show();
                    event.consume();
                } else {
                    Desktop desktop = Desktop.getDesktop();
                    URI website = new URI(link);
                    desktop.browse(website);
                }
            } catch (URISyntaxException | IOException e) {
                ExceptionDialog.getDialog("A unexpected exception occurred. Please contact the developers.", "BlockEdit \u2012 " + e.getClass().getName() + " occurred", "An unexpected exception occurred", e).show();
            }
        });
        return item;
    }
}
