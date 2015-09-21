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

import org.blockedit.utils.Debugger;
import org.blockedit.utils.MiniConsole;
import org.blockedit.utils.UserInformation;
import org.blockedit.utils.VersionReference;
import org.blockedit.windows.dialog.CustomBlocksDialog;
import org.blockedit.windows.dialog.ImportSchematicDialog;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    public Scene scene;
    public MenuBar menu = new MenuBar();
    public ToolBar toolBar = new ToolBar();
    public BorderPane pane = new BorderPane();
    public TabPane tabPane = new TabPane();
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create the main gui window.
     *
     * @param stage The main stage
     * @throws Exception If a unknown exception occurs. (Exceptions are not excepted to occur)
     */
    @Override
    public void start(final Stage stage) throws Exception {
        MainGui.stage = stage;
        this.scene = new Scene(this.container);
        this.scene.setFill(Color.WHITE);
        this.container.setMaxWidth(UserInformation.getWindowWidth());
        this.container.setMaxHeight(UserInformation.getWindowHeight());
        this.container.setId("background");
        HBox tabContainer = new HBox();
        this.container.getChildren().addAll(getOrCreateMenubar(), getOrCreateToolBar(), new Separator(Orientation.HORIZONTAL), this.pane);
        Debugger debugger = Debugger.getInstance();
        this.pane.setLeft(tabContainer);
        Separator separator = new Separator(Orientation.VERTICAL);
        tabContainer.getChildren().addAll(getOrCreateTabPane(), separator);
        MainGui.stage.setScene(this.scene);
        //<editor-fold desc="Set window title">
        if(VersionReference.DEV) {
            stage.setTitle("BlockEdit Developmental \u2012 " + VersionReference.getVersion());
        }
        if(VersionReference.PRE_RELEASE) {
            stage.setTitle("BlockEdit PreRelease \u2012 " + VersionReference.getVersion());
        }
        if(VersionReference.RELEASE) {
            stage.setTitle("BlockEdit \u2012 " + VersionReference.getVersion());
        }
        //</editor-fold>
        this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.tabPane.setPrefWidth((Double) newValue / 4);
        });
        this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            tabPane.setPrefHeight((Double) newValue);
        });
        debugger.start();
        MainGui.stage.setMaxWidth(UserInformation.getWindowWidth());
        MainGui.stage.setMaxHeight(UserInformation.getWindowHeight());
        MainGui.stage.setMinWidth(UserInformation.getWindowWidth() / 2.5);
        MainGui.stage.setMinHeight(UserInformation.getWindowHeight() / 2.5);
        MainGui.stage.setOnCloseRequest(event -> {
            debugger.printEvent(event.getEventType(), this.getClass());
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
                    System.exit(0);
                }
                if (response == exit) {
                    Platform.exit();
                    System.exit(0);
                } else {
                    event.consume();
                }
            });
        });
        this.scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800italic,800,700italic,600,600italic,300,300italic&subset=latin,greek-ext,greek,cyrillic,vietnamese,cyrillic-ext,latin-ext");
        this.scene.getStylesheets().add("file:///" + new File("src/main/resources/style.css").getAbsolutePath().replace("\\", "/"));
        MainGui.stage.show();
        System.out.println("BlockEdit  Copyright (C) 2015  Jeff Chen and others\n" +
                "This program comes with ABSOLUTELY NO WARRANTY;\n" +
                "This is free software, and you are welcome to redistribute it\n" +
                "under certain conditions;");
        debugger.print("Components successfully loaded");
    }

    public MenuBar getOrCreateMenubar() {
        this.menu.setId("menuB");
        Menu fileMenu = new Menu("File");
        fileMenu.setId("menu");
        Menu editMenu = new Menu("Edit");
        editMenu.setId("menu");
        Menu customizeMenu = new Menu("Customize");
        customizeMenu.setId("menu");
        Menu viewMenu = new Menu("View");
        viewMenu.setId("menu");
        Menu helpMenu = new Menu("Help");
        helpMenu.setId("menu");
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
            for (Node node : this.container.getChildren()) {
                node.setDisable(true);
            }
            dialog.setOnCloseRequest(event1 -> {
                for (Node node : this.container.getChildren()) {
                    node.setDisable(false);
                }
            });
            dialog.show();
        });
        customizeMenu.getItems().add(blockCustomizeItem);
        //</editor-fold>
        this.menu.getMenus().addAll(fileMenu, editMenu, customizeMenu, viewMenu, helpMenu);
        this.menu.getStylesheets().add("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800italic,800,700italic,600,600italic,300,300italic&subset=latin,greek-ext,greek,cyrillic,vietnamese,cyrillic-ext,latin-ext");
        this.menu.getStylesheets().add("file:///" + new File("src/main/resources/menubar.css").getAbsolutePath().replace("\\", "/"));
        return this.menu;
    }

    public TabPane getOrCreateTabPane() {
        Tab inspectorTab = new Tab("Inspector");
        Pane inspectorPane = new Pane();
        Tab worldInformationTab = new Tab("Level Information");
        Pane worldInformationPane = new Pane();

        Tab debugTab = new Tab("Debug");
        debugTab.setClosable(false);
        MiniConsole console = new MiniConsole();
        console.start();
        debugTab.setContent(console.getConsole().get());
        console.getConsole().get().setId("debugConsole");

        inspectorTab.setContent(inspectorPane);
        worldInformationTab.setContent(worldInformationPane);

        inspectorPane.setPrefHeight(UserInformation.getWindowHeight());
        inspectorPane.setPrefWidth(UserInformation.getWindowWidth());
        inspectorPane.setId("infopane");
        worldInformationPane.setId("infopane");

        inspectorTab.setClosable(false);
        worldInformationTab.setClosable(false);
        this.tabPane.getTabs().addAll(inspectorTab, worldInformationTab, debugTab);
        this.tabPane.setId("tabpane");
        this.tabPane.getStylesheets().add("https://fonts.googleapis.com/css?family=Inconsolata:400,700&subset=latin,latin-ext");
        this.tabPane.getStylesheets().add("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800italic,800,700italic,600,600italic,300,300italic&subset=latin,greek-ext,greek,cyrillic,vietnamese,cyrillic-ext,latin-ext");
        this.tabPane.getStylesheets().add("file:///" + new File("src/main/resources/tab.css").getAbsolutePath().replace("\\", "/"));
        this.tabPane.setMaxHeight(UserInformation.getWindowHeight());
        this.tabPane.setPrefHeight(UserInformation.getWindowHeight());
        return this.tabPane;
    }

    public ToolBar getOrCreateToolBar() {
        return this.toolBar;
    }
}