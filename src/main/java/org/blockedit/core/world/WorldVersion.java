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

package org.blockedit.core.world;

import org.apache.commons.lang3.StringUtils;
import org.blockedit.exception.ParseException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author Jeff Chen
 */
public final class WorldVersion {

    private byte version = 0;
    private byte subVersion = 0;
    private byte release = 0;
    private boolean isRelease = true;
    private boolean isSnapshot = false;
    private String snapshotVersion;

    private WorldVersion(byte version, byte subVersion, byte release) {
        this.version = version;
        this.subVersion = subVersion;
        this.release = release;
    }

    private WorldVersion(String snapshotVersion) {
        this.isRelease = false;
        this.isSnapshot = true;
        this.snapshotVersion = snapshotVersion;
    }

    /**
     * Get the version if it was set.
     *
     * @return Returns the version, only if it was set
     */
    public Optional<Byte> getVersion() {
        if(this.version != 0) {
            return Optional.of(this.version);
        }
        return Optional.empty();
    }

    public Optional<Byte> getSubVersion() {
        if(this.subVersion != 0) {
            return Optional.of(this.subVersion);
        }
        return Optional.empty();
    }

    public Optional<Byte> getReleaseVersion() {
        if(this.release != 0) {
            return Optional.of(this.release);
        }
        return Optional.empty();
    }

    /**
     * Check if the version is a release.
     *
     * @return Returns if the version is a release
     */
    public boolean isRelease() {
        return this.isRelease;
    }

    /**
     * Check if the version is a snapshot.
     *
     * @return Returns if the version is a snapshot
     */
    public boolean isSnapshot() {
        return this.isSnapshot;
    }

    /**
     * Convert the world version to a {@link String}.
     *
     * @return Returns the world version
     */
    @Override
    public String toString() {
        if(isSnapshot()) {
            return this.snapshotVersion;
        } else {
            return this.version + "." + this.subVersion + "." + this.release;
        }
    }

    /**
     * Create the WorldVersion from a {@link String}.
     *
     * @param s The string that holds the version
     * @return Returns a new instance of {@link WorldVersion}
     * @throws ParseException If the string is not a correct version
     * @throws NumberFormatException If the program was unable to parse the string to a {@link Byte}
     */
    public static WorldVersion fromString(String s) throws ParseException, NumberFormatException {
        Matcher snapshotMatcher = Pattern.compile("^[0-9]{1,2}[a-z][0-9]{1,2}[a-z]$").matcher(s);
        Matcher versionMatcher = Pattern.compile("^[1].[0-9].([0-9]{1,2})$").matcher(s);
        Matcher preMatcher = Pattern.compile("^[1].[0-9].([0-9]{1,2})-(pre[0-9]{1}|pre)$").matcher(s);
        Matcher rcMatcher = Pattern.compile("^1.0.0-RC[1-2]$").matcher(s);
        if(rcMatcher.matches()) {
            throw new ParseException("Version " + s + " is obsolete and no longer supported!");
        }
        if (snapshotMatcher.matches()) {
            return new WorldVersion(s);
        }
        if (versionMatcher.matches() && preMatcher.matches()) {
            String[] values = StringUtils.split(s, '.');
            byte version = Byte.parseByte(values[0]);
            byte subVersion = Byte.parseByte(values[1]);
            byte release = Byte.parseByte(values[2]);
            return new WorldVersion(version, subVersion, release);
        } else {
            throw new ParseException("Invaild Format: " + s);
        }
    }

    /**
     * Create a new WorldVersion.
     *
     * @param version The Minecraft version
     * @param subVersion The subversion
     * @param releaseVersion The release number
     * @return Returns a new instance of {@link WorldVersion}
     */
    public static WorldVersion create(byte version, byte subVersion, byte releaseVersion) {
        return new WorldVersion(version, subVersion, releaseVersion);
    }

    public ArrayList<String> getVersions() {
        ArrayList<String> versions = new ArrayList<>();
        return versions;
    }
}
