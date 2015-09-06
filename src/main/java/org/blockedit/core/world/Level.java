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
import org.jnbt.ByteArrayTag;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.DoubleTag;
import org.jnbt.EndTag;
import org.jnbt.FloatTag;
import org.jnbt.IntArrayTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.LongTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
    private final TreeMap<String, Object> gameRules;
    private final Triplet<Integer, String, Boolean> worldVersion;

    private Level(Map<String, Tag> levelData, TreeMap<String, Object> gameRules, Triplet<Integer, String, Boolean> worldVersion) {
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
        private TreeMap<String, Object> gameRules = new TreeMap<>();
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
        public Builder gameRule(String rule, Object value) {
            this.gameRules.put(rule, value);
            return this;
        }

        /**
         * Inherit game rules from a parent.
         *
         * @param gameRules The game rules
         * @return Returns this builder, for chaining
         */
        public Builder gameRule(TreeMap<String, Object> gameRules) {
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
        private final TreeMap<String, Object> gameRules = new TreeMap<>();
        private Triplet<Integer, String, Boolean> worldVersion;

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

        public TreeMap<String, Object> loadGameRules() throws IOException, NumberFormatException, DataDoesNotExistException {

            TreeMap<String, Object> rules = new TreeMap<>();

            if (this.stream.readTag() instanceof CompoundTag) {
                CompoundTag levelSettings = (CompoundTag) this.stream.readTag();

                if (levelSettings.getValue().get("GameRules") != null) {
                    CompoundTag gameRules = (CompoundTag) levelSettings.getValue().get("GameRules");
                    Iterator<Map.Entry<String, Tag>> gameRuleIterator = gameRules.getValue().entrySet().iterator();

                    while (gameRuleIterator.hasNext()) {

                        Map.Entry<String, Tag> next = gameRuleIterator.next();

                        if (next.getValue() instanceof StringTag) {

                            if (next.getValue().getName().equals("randomTickSpeed")) {
                                rules.put(gameRuleIterator.next().getKey(), Integer.parseInt(((StringTag) gameRuleIterator.next().getValue()).getValue()));
                            } else {
                                rules.put(gameRuleIterator.next().getKey(), Boolean.parseBoolean(((StringTag) gameRuleIterator.next().getValue()).getValue()));
                            }
                        }
                    }
                } else {
                    throw new DataDoesNotExistException("The gamerules do not exist!");
                }
            }
            if (rules == null) {
                throw new DataDoesNotExistException("The gamerules do not exist!");
            } else {
                return rules;
            }
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

                if(this.levelData.containsKey("GameRules")) {

                }
                if(this.levelData.containsKey("Version")) {
                    
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
        private final TreeMap<String, Object> gameRules;
        private final File location;

        private Exporter(NBTOutputStream stream, Map<String, Tag> levelData, TreeMap<String, Object> gameRules, File location) {
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

            if (this.gameRules.get("commandBlockOutput") == null) {
                this.gameRules.put("commandBlockOutput", true);
            }
            if (this.gameRules.get("doDaylightCycle") == null) {
                this.gameRules.put("doDaylightCycle", true);
            }
            if (this.gameRules.get("doEntityDrops") == null) {
                this.gameRules.put("doEntityDrops", true);
            }
            if (this.gameRules.get("doFireTick") == null) {
                this.gameRules.put("doFireTick", true);
            }
            if (this.gameRules.get("doMobLoot") == null) {
                this.gameRules.put("doMobLoot", true);
            }
            if (this.gameRules.get("doMobSpawning") == null) {
                this.gameRules.put("doMobSpawning", true);
            }
            if (this.gameRules.get("doTileDrops") == null) {
                this.gameRules.put("doTileDrops", true);
            }
            if (this.gameRules.get("keepInventory") == null) {
                this.gameRules.put("keepInventory", false);
            }
            if (this.gameRules.get("logAdminCommands") == null) {
                this.gameRules.put("logAdminCommands", true);
            }
            if (this.gameRules.get("mobGriefing") == null) {
                this.gameRules.put("mobGriefing", true);
            }
            if (this.gameRules.get("naturalRegeneration") == null) {
                this.gameRules.put("naturalRegeneration", true);
            }
            if (this.gameRules.get("randomTickSpeed") == null) {
                this.gameRules.put("randomTickSpeed", 3);
            }
            if (this.gameRules.get("reducedDebugInfo") == null) {
                this.gameRules.put("reducedDebugInfo", false);
            }
            if (this.gameRules.get("sendCommandFeedback") == null) {
                this.gameRules.put("sendCommandFeedback", true);
            }
            if (this.gameRules.get("showDeathMessages") == null) {
                this.gameRules.put("showDeathMessages", true);
            }

            Iterator<Map.Entry<String, Object>> iterator = this.gameRules.entrySet().iterator();
            Map<String, Tag> gameRuleData = new HashMap<>();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                if (next.getValue() instanceof Boolean) {
                    gameRuleData.put(next.getKey(), new StringTag(next.getKey(), String.valueOf((boolean) next.getValue())));
                }
                if (next.getValue() instanceof Integer) {
                    if (next.getKey().equals("randomTickSpeed")) {
                        gameRuleData.put(next.getKey(), new StringTag(next.getKey(), String.valueOf((int) next.getValue())));
                    }
                }
            }

            CompoundTag gameRuleTag = new CompoundTag("GameRules", gameRuleData);

            Map<String, Tag> data = new HashMap<>();

            //<editor-fold desc="Level Settings">
            for (Map.Entry<String, Tag> entry : this.levelData.entrySet()) {
                String key = entry.getKey();
                data.put("GameRules", gameRuleTag);

                Tag type = entry.getValue();

                if (type instanceof ByteTag) {
                    if (!(entry.getValue().getValue() instanceof Byte)) {
                        throw new DataException("Value " + key + " is not a byte");
                    }
                    data.put(key, new ByteTag(key, (byte) entry.getValue().getValue()));
                }
                if (type instanceof ByteArrayTag) {
                    if (!(entry.getValue().getValue() instanceof Byte[])) {
                        throw new DataException("Value " + key + " is not a byte array");
                    }
                    data.put(key, new ByteArrayTag(key, (byte[]) entry.getValue().getValue()));
                }
                if (type instanceof DoubleTag) {
                    if (!(entry.getValue().getValue() instanceof Double)) {
                        throw new DataException("Value " + key + " is not a double");
                    }
                    data.put(key, new DoubleTag(key, (double) entry.getValue().getValue()));
                }
                if (type instanceof EndTag) {
                    throw new DataException("End Tags are not allowed!");
                }
                if (type instanceof FloatTag) {
                    if (!(entry.getValue().getValue() instanceof Float)) {
                        throw new DataException("Value " + key + " is not a float!");
                    }
                    data.put(key, new FloatTag(key, (float) entry.getValue().getValue()));
                }
                if (type instanceof IntTag) {
                    if (!(entry.getValue().getValue() instanceof Integer)) {
                        throw new DataException("Value " + key + " is not an integer!");
                    }
                    data.put(key, new IntTag(key, (int) entry.getValue().getValue()));
                }
                if (type instanceof IntArrayTag) {
                    if (!(entry.getValue().getValue() instanceof Integer[])) {
                        throw new DataException("Value " + key + " is not an integer array!");
                    }
                    data.put(key, new IntArrayTag(key, (int[]) entry.getValue().getValue()));
                }
                if (type instanceof ListTag) {
                    throw new DataException("List Tags are not allowed!");
                }
                if (type instanceof LongTag) {
                    if (!(entry.getValue().getValue() instanceof Long)) {
                        throw new DataException("Value " + key + " is not a long");
                    }
                    data.put(key, new LongTag(key, (long) entry.getValue().getValue()));
                }
                if (type instanceof ShortTag) {
                    if (!(entry.getValue().getValue() instanceof Short)) {
                        throw new DataException("Value " + key + " is not a short");
                    }
                    data.put(key, new ShortTag(key, (short) entry.getValue().getValue()));
                }
                if (type instanceof StringTag) {
                    if (!(entry.getValue().getValue() instanceof String)) {
                        throw new DataException("value " + key + " is not a string");
                    }
                    data.put(key, new StringTag(key, (String) entry.getValue().getValue()));
                }
            }
            //</editor-fold>

            data.put(gameRuleTag.getName(), gameRuleTag);

            for (Map.Entry<String, Object> writableData : this.gameRules.entrySet()) {
                data.put(writableData.getKey(), new StringTag(writableData.getKey(), String.valueOf(writableData.getValue())));
            }

            CompoundTag levelInformation = new CompoundTag("Data", data);

            this.stream.writeTag(levelInformation);
            this.stream.close();
        }

        public File getDestination() {
            return this.location;
        }
    }
}
