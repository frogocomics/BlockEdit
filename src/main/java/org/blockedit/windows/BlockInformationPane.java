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

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.blockedit.utils.UserInformation;

public class BlockInformationPane {

    private static TabPane tab;

    public static TabPane getOrCreate() {
        Tab saveStructure = new Tab("Structure");
        Tab information = new Tab("Information");
        BlockInformationPane.tab = new TabPane();
        BlockInformationPane.tab.getTabs().addAll(saveStructure,information);
        tab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tab.setMaxWidth(UserInformation.getWindowWidth() / 3);
        tab.setMaxHeight(UserInformation.getWindowHeight() / 3);
        tab.setPrefWidth(tab.getMaxWidth());
        tab.setPrefHeight(tab.getMaxHeight());
        tab.setMinWidth(UserInformation.getWindowWidth() / 7);
        tab.setMinHeight(UserInformation.getWindowHeight() / 7);
        return tab;
    }

    public static void replace(TabPane newTabPane) {
        BlockInformationPane.tab = newTabPane;
    }

    public static TabPane getTabPane() {
        return BlockInformationPane.tab;
    }
}
