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

import org.blockedit.exception.DataDoesNotExistException;
import org.blockedit.exception.DataException;
import org.javatuples.Triplet;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.StringTag;
import org.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Utilities to create, export, edit, and load Minecraft <a href="http://minecraft.gamepedia.com/Level_format">Level
 * Format</a> files. <p><img src="http://i.imgur.com/QWLwKFp.png" alt="Image not available" /> This
 * diagram shows how the Nbt data is created and transferred through {@link Level} and exported
 * through {@link Level.Exporter}. Now let's look at the code snippet below:<hr /> <code> try {
 * Level aMinecraftLevel = new Level.Loader(new File("C:\\RandomFolder\\level.dat").load(); //Load
 * the level //Now we export the level aMinecraftLevel.getLevelExporter( new NbtOutputStream( new
 * DataOutputStream( new FileOutputStream( new File("C:\\AnotherFolder\\levelNew.dat") ) ) ) , new
 * File("C:\\AnotherFolder\\levelNew.dat")).write(); } catch(IOException e) { e.printStackTrace(); }
 * </code> Please keep in mind that {@link CompoundTag} and {@link ByteTag} will
 * <strong>not</strong> be allowed as all gamerules will be created through {@link
 * Level.Builder#gameRule(String, Object)} instead.</p>
 *
 * @author Jeff Chen
 */
@ParametersAreNonnullByDefault
public final class Level {

    public static void main(String[] args) {
        try {
            Level level = new Level.Loader(new File("C:\\Users\\Jeff\\AppData\\Roaming\\.minecraft\\saves\\BO2 Objects\\level.dat")).load();
            System.out.println(level.getLevel().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Map<String, Tag> levelData;
    private final Map<String, Tag> gameRules;
    private final Triplet<Integer, String, Boolean> worldVersion;

    private Level(Map<String, Tag> levelData, Map<String, Tag> gameRules, Triplet<Integer, String, Boolean> worldVersion) {
        this.levelData = levelData;
        this.gameRules = gameRules;
        this.worldVersion = worldVersion;
    }

    /**
     * Get the level information.
     *
     * @return Returns the level information
     */
    public Map<String, Tag> getLevel() {
        return this.levelData;
    }

    /**
     * Get the game rules
     *
     * @return Returns the game rules
     */
    public Map<String, Tag> getGameRules() {
        return this.gameRules;
    }

    /**
     * Get the Nbt level.dat exporter.
     *
     * @param stream The output stream used to write the file
     * @return Returns the level exporter
     */
    public Exporter getLevelExporter(NBTOutputStream stream, File location) {
        return new Exporter(stream, this.levelData, this.gameRules, location);
    }

    /**
     * Get the id of the world version.
     *
     * @return Returns the version id
     */
    public int getId() {
        return this.worldVersion.getValue0();
    }

    /**
     * Get the name of the version, ex. 1.8.8
     *
     * @return Returns the version
     */
    public String getVersion() {
        return this.worldVersion.getValue1();
    }

    /**
     * Check if the version is a snapshot.
     *
     * @return Returns if the version is a snapshot
     */
    public boolean isSnapshot() {
        return this.worldVersion.getValue2();
    }

    /**
     * Get the world version data.
     *
     * @return Returns all the world data
     */
    public Triplet<Integer, String, Boolean> getWorldVersionInformation() {
        return this.worldVersion;
    }

    /**
     * Get the level editor.
     *
     * @return Returns the level builder
     */
    public Builder getLevelBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private Map<String, Tag> levelData = new TreeMap<>();
        private Map<String, Tag> gameRules = new TreeMap<>();
        private Triplet<Integer, String, Boolean> worldVersion;

        /**
         * Prevent instantiation
         */
        public Builder() {}

        public Builder(Level parent) {
            levelData = parent.getLevel();
        }

        /**
         * Inherit all the features of a parent.
         *
         * @param parent The parent
         * @return Returns a new level builder
         */
        public static Builder fromParent(Level parent) {
            return new Builder(parent);
        }

        /**
         * Add a nbt tag.
         *
         * @param k The name of the tag
         * @param v The value of the tag, The type of the tag
         * @return Returns this builder, for chaining
         */
        public Builder setting(String k, Tag v) {
            this.levelData.put(k, v);
            return this;
        }

        /**
         * Set a game rule.
         *
         * @param rule  The name of the specific rule
         * @param value The value of the rule
         * @return Returns this builder, for chaining
         */
        public Builder gameRule(String rule, Tag value) {
            this.gameRules.put(rule, value);
            return this;
        }

        /**
         * Inherit game rules from a parent.
         *
         * @param gameRules The game rules
         * @return Returns this builder, for chaining
         */
        public Builder gameRule(Map<String, Tag> gameRules) {
            this.gameRules = gameRules;
            return this;
        }

        /**
         * Set the world version data.
         *
         * @param id       The version id
         * @param version  The version name
         * @param snapshot If the version is a snapshot
         * @return Returns this builder, for chaining
         */
        public Builder version(int id, String version, boolean snapshot) {
            this.worldVersion = new Triplet<>(id, version, snapshot);
            return this;
        }

        /**
         * Create the new level.
         *
         * @return A new level
         * @throws DataException If the version has not been set
         */
        public Level build() throws DataException {
            if (this.worldVersion == null) {
                throw new DataException("The version has not been specified");
            }
            return new Level(this.levelData, this.gameRules, this.worldVersion);
        }
    }

    public static class Loader {

        private NBTInputStream stream;
        private Map<String, Tag> levelData = new TreeMap<>();
        private Map<String, Tag> gameRules = new TreeMap<>();
        private Triplet<Integer, String, Boolean> worldVersion;
        private int id = 0;
        private String version;
        private boolean snapshot = false;

        public Loader(File location) throws IOException {
            this.stream = new NBTInputStream(new FileInputStream(location));
        }

        /**
         * Get the nbt input stream.
         *
         * @return Returns the nbt input stream
         */
        public NBTInputStream getInputStream() {
            return this.stream;
        }

        public Map<String, Tag> loadGameRules() throws IOException, NumberFormatException, DataDoesNotExistException {
            Map<String, Tag> rules = new TreeMap<>();
            return rules;
        }

        /**
         * Load a Minecraft Level file.
         *
         * @return Returns a new instance of {@link Level}
         * @throws DataException If the level file is corrupted
         * @throws IOException   If there are any IO related problems
         */
        public Level load() throws DataException, IOException {

            Tag rootTag = this.stream.readTag();

            if (rootTag.getName().equals("Data") && rootTag instanceof CompoundTag) {

                CompoundTag levelSettings = (CompoundTag) rootTag;
                System.out.println(levelSettings.getValue());
                this.levelData = levelSettings.getValue();

                if(this.levelData.containsKey("GameRules") & this.levelData.get("GameRules") instanceof CompoundTag) {
                    Map<String, Tag> gameRuleSettings = ((CompoundTag) this.levelData.get("GameRules")).getValue();
                    this.gameRules = gameRuleSettings; 
                }
                if(this.levelData.containsKey("Version") & this.levelData.get("Version") instanceof CompoundTag) {
                    Map<String, Tag> versionSettings = ((CompoundTag) this.levelData.get("Version")).getValue();

                    if(versionSettings.containsKey("Id")) {
                        if(versionSettings.get("Id") instanceof StringTag) {
                            this.id = Integer.parseInt(((StringTag) versionSettings.get("Id")).getValue());
                        }
                    }
                    if(versionSettings.containsKey("Name")) {
                        if(versionSettings.get("Name") instanceof StringTag) {
                            this.version = ((StringTag) versionSettings.get("Name")).getValue();
                        }
                    }
                    if(versionSettings.containsKey("Snapshot")) {
                         if(versionSettings.get("Snapshot") instanceof StringTag) {
                             if(((StringTag) versionSettings.get("Snapshot")).getValue().equals("1")) {
                                 this.snapshot = true;
                             }
                             if(((StringTag) versionSettings.get("Snapshot")).getValue().equals("0")) {
                                 this.snapshot = false;
                             } else {
                                 throw new DataException("Invaild Value: Value must be 0 or 1.");
                             }
                         }
                    }
                    if(this.version == null) {
                        this.version = "1.8.8";
                    }
                    this.worldVersion = new Triplet<>(this.id, this.version, this.snapshot);
                }
                if (this.worldVersion == null) {
                    this.worldVersion = new Triplet<>(0, "1.8.8", false);
                }

                return new Level(this.levelData, this.gameRules, this.worldVersion);
            } else {
                throw new DataException("Corrupt Level File");
            }
        }
    }

    /**
     * The exporter used to export the newly created NBT {@link Level}. <p>The {@link
     * Exporter#write()} method exports the file.</p>
     */
    private class Exporter {

        private final NBTOutputStream stream;
        private final Map<String, Tag> levelData;
        private final Map<String, Tag> gameRules;
        private final File location;

        private Exporter(NBTOutputStream stream, Map<String, Tag> levelData, Map<String, Tag> gameRules, File location) {
            this.stream = stream;
            this.levelData = levelData;
            this.gameRules = gameRules;
            this.location = location;
        }

        /**
         * Write the created level.dat file to a location specified by the output stream.
         *
         * @throws IOException   If the program is unable to write to the specified location
         * @throws DataException If a disallowed {@link Tag} was used
         */
        public void write() throws DataException, IOException {
            //TODO
        }

        public File getDestination() {
            return this.location;
        }
    }
}
