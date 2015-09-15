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
package org.blockedit.windows;

import org.blockedit.utils.UserInformation;
import org.blockedit.utils.VersionReference;
import org.blockedit.windows.dialog.CustomBlocksDialog;
import org.blockedit.windows.dialog.ImportSchematicDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Represents the main gui of the application.
 *
 * @author Jeff Chen
 */
public class MainGui extends Application {

    public VBox container = new VBox();
    public Scene scene = new Scene(container, UserInformation.getWindowWidth(), UserInformation.getWindowHeight(), Color.WHITE);
    public MenuBar menu = new MenuBar();
    public ToolBar toolBar = new ToolBar();
    public BorderPane pane = new BorderPane();
    public TabPane tabPane = new TabPane();
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        MainGui.stage = stage;
        this.container.getChildren().addAll(getOrCreateMenubar(), this.toolBar, this.pane);
        this.pane.setLeft(getOrCreateTabPane());
        MainGui.stage.setScene(this.scene);
        //<editor-fold desc="Set window title">
        if(VersionReference.DEV) {
            stage.setTitle("BlockEdit Developmental - " + VersionReference.getVersion());
        }
        if(VersionReference.PRE_RELEASE) {
            stage.setTitle("BlockEdit PreRelease - " + VersionReference.getVersion());
        }
        if(VersionReference.RELEASE) {
            stage.setTitle("BlockEdit - " + VersionReference.getVersion());
        }
        //</editor-fold>
        this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            tabPane.setPrefWidth((Double) newValue / 5);
        });
        this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {

        });
        MainGui.stage.setMaxWidth(UserInformation.getWindowWidth());
        MainGui.stage.setMaxHeight(UserInformation.getWindowHeight());
        MainGui.stage.setMinWidth(UserInformation.getWindowWidth() / 2.5);
        MainGui.stage.setMinHeight(UserInformation.getWindowHeight() / 2.5);
        MainGui.stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you would like to quit without saving?");
            alert.setContentText("Unsaved changes will be lost!");
            ButtonType saveAndExit = new ButtonType("Save and Exit", ButtonBar.ButtonData.SMALL_GAP);
            ButtonType exit = new ButtonType("Exit without Saving");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(saveAndExit, exit, cancel);
            alert.initStyle(StageStyle.UTILITY);
            alert.setWidth(UserInformation.getWindowWidth() / 4);
            alert.setHeight(UserInformation.getWindowHeight() / 4);
            alert.showAndWait().ifPresent(response -> {
                if (response == saveAndExit) {
                    Platform.exit();
                }
                if (response == exit) {
                    Platform.exit();
                } else {
                    event.consume();
                }
            });
        });
        MainGui.stage.show();
    }

    public MenuBar getOrCreateMenubar() {
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu customizeMenu = new Menu("Customize");
        Menu viewMenu = new Menu("View");
        Menu helpMenu = new Menu("Help");
        //<editor-fold desc="Menu File">
        //File
        Menu importSubMenu = new Menu("Import");
        //<editor-fold desc="Menu File>>Import">
        MenuItem worldMenuItem = new MenuItem("Minecraft Level");
        MenuItem schematicMenuItem = new MenuItem("Schematic");
        schematicMenuItem.setOnAction(event -> {
            ImportSchematicDialog schematicDialog = new ImportSchematicDialog();
            Stage dialog = schematicDialog.getOrCreateDialog(UserInformation.getWindowWidth() / 2, UserInformation.getWindowHeight() / 2);
            dialog.initStyle(StageStyle.DECORATED);
            dialog.initOwner(stage);
            dialog.show();
            for (Node node : this.container.getChildren()) {
                node.setDisable(true);
            }
            dialog.setOnCloseRequest(event1 -> {
                for (Node node : this.container.getChildren()) {
                    node.setDisable(false);
                }
            });
        });
        MenuItem bobV2MenuItem = new MenuItem("BO2 File");
        MenuItem bobV3MenuItem = new MenuItem("BO3 File");
        importSubMenu.getItems().addAll(worldMenuItem, schematicMenuItem, bobV2MenuItem, bobV3MenuItem);
        //</editor-fold>
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As");
        MenuItem exportMenuItem = new MenuItem("Export");
        MenuItem exportAsMenuItem = new MenuItem("Export As");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you would like to quit without saving?");
            alert.setContentText("Unsaved changes will be lost!");
            ButtonType saveAndExit = new ButtonType("Save and Exit", ButtonBar.ButtonData.SMALL_GAP);
            ButtonType exit = new ButtonType("Exit without Saving");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(saveAndExit, exit, cancel);
            alert.initStyle(StageStyle.UTILITY);
            alert.setWidth(UserInformation.getWindowWidth() / 4);
            alert.setHeight(UserInformation.getWindowHeight() / 4);
            alert.showAndWait().ifPresent(response -> {
                if(response==saveAndExit) {
                    Platform.exit();
                }
                if(response==exit) {
                    Platform.exit();
                }
            });
        });
        fileMenu.getItems().addAll(importSubMenu, saveMenuItem, saveAsMenuItem, exportMenuItem, exportAsMenuItem, new SeparatorMenuItem(), exitMenuItem);
        //</editor-fold>
        //<editor-fold desc="Menu Customize">
        MenuItem blockCustomizeItem = new MenuItem("Custom Blocks");
        blockCustomizeItem.setOnAction(event -> {
            CustomBlocksDialog blocksDialog = new CustomBlocksDialog();
            Stage dialog = blocksDialog.getOrCreateDialog(UserInformation.getWindowWidth() / 2, UserInformation.getWindowHeight() / 2);
            dialog.initOwner(stage);
            for(Node node : this.container.getChildren()) {
                node.setDisable(true);
            }
            dialog.setOnCloseRequest(event1 -> {
                for(Node node : this.container.getChildren()) {
                    node.setDisable(false);
                }
            });
            dialog.show();
        });
        customizeMenu.getItems().add(blockCustomizeItem);
        //</editor-fold>
        this.menu.getMenus().addAll(fileMenu, editMenu, customizeMenu, viewMenu, helpMenu);
        return this.menu;
    }

    public TabPane getOrCreateTabPane() {
        Tab inspectorTab = new Tab("Inspector");
        Tab worldInformationTab = new Tab("Level Information");
        inspectorTab.setClosable(false);
        worldInformationTab.setClosable(false);
        this.tabPane.getTabs().addAll(inspectorTab, worldInformationTab);
        return this.tabPane;
    }

    public void setMenuBar(MenuBar menu) {
        this.menu = menu;
    }

    public void setTabPane(TabPane pane) {
        this.tabPane = pane;
    }

    public static Stage getWindow() {
        return MainGui.stage;
    }
}