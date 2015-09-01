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
import org.apache.maven.project.MavenProject;
import org.blockedit.utils.contribution.ContributionLevel;
import org.javatuples.Pair;

import java.util.ArrayList;

public class Author {


    @Beta
    @Deprecated
    public static ArrayList<Pair<String, ContributionLevel>> getAuthors() {
        MavenProject project;
        return null;
    }
}
