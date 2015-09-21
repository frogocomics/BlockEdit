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

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.EventType;

public class Debugger {

    private static Debugger ourInstance = new Debugger();
    private final String VERSION = "0.2.1";

    public static Debugger getInstance() {
        return ourInstance;
    }

    private Debugger() {
    }

    public void start() {
        System.out.println("__________\n" +
                "BlockEdit Debugger Version " + this.VERSION + " has started.\n" +
                "Debugger created by Jeff Chen <frogocomics@gmail.com>\n" + "" +
                "__________");
    }

    public void print(String message) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("[HH:mm:ss:SSSS]");
        System.out.println(dateFormatter.format(new Date()) + " " + message);
    }

    public void print(Object object) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("[HH:mm:ss:SSSS]");
        System.out.println(dateFormatter.format(new Date()) + " " + object);
    }

    public void printEvent(EventType type, Class clazz) {
        print("Event " + type.getName() + " has been triggered at " + clazz.getName());
    }

    public void printExpectedException(Exception e) {
        print(e.getClass().getName() + " has has been triggered. The full stack trace is as follows: " + ExceptionUtils.getStackTrace(e));
    }

    public String getVersion() {
        return this.VERSION;
    }
}
