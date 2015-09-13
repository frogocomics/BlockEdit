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

import org.apache.commons.lang3.ArrayUtils;
import org.blockedit.exception.ParseException;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.Optional;

public class Chunk {

    private HashMap<Integer, Section> sections = new HashMap<>();
    private int xPos = -1;
    private int zPos = -1;
    private long lastUpdate = -1;
    private boolean lightPopulated = false;
    private boolean terrainPopulated = false;
    private long inhabitedTime = -1;
    private byte[] biomes = new byte[256];
    private int[] heightMap = new int[1024];

    private Chunk(HashMap<Integer, Section> sections, int xPos, int zPos, long lastUpdate, boolean lightPopulated, boolean terrainPopulated, long inhabitedTime, byte[] biomes, int[] heightMap) {
        this.sections = sections;
        this.xPos = xPos;
        this.zPos = zPos;
        this.lastUpdate = lastUpdate;
        this.lightPopulated = lightPopulated;
        this.terrainPopulated = terrainPopulated;
        this.inhabitedTime = inhabitedTime;
        this.biomes = biomes;
        this.heightMap = heightMap;
    }

    public Optional<Section> getSection(byte y) {
        if(this.sections.containsKey(y)) {
            return Optional.of(this.sections.get(y));
        } else {
            if(y >= 1 | y <= 16) {
                try {
                    return Optional.of(Section.empty(y));
                } catch (ParseException e) {} //A exception should NEVER be thrown
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public HashMap<Integer, Section> getSections() {
        return this.sections;
    }

    public Pair<Integer, Integer> getChunkPos() {
        return new Pair<>(this.xPos, this.zPos);
    }

    public Optional<Long> getLastChunkUpdate() {
        if(this.lastUpdate > 0) {
            return Optional.of(this.lastUpdate);
        } else {
            return Optional.empty();
        }
    }

    public static class Builder {

        private HashMap<Integer, Section> sections = new HashMap<>();
        private int xPos = -1;
        private int zPos = -1;
        private long lastUpdate = -1;
        private boolean lightPopulated = false;
        private boolean terrainPopulated = false;
        private long inhabitedTime = -1;
        private byte[] biomes = new byte[256];
        private int[] heightMap = new int[1024];

        public Builder addSection(Section section) {
            if (sections.size() <= 16) {
                this.sections.put(section.getY(), section);
            }
            return this;
        }

        public Builder xPos(int i) {
            this.xPos = 1;
            return this;
        }

        public Builder zPos(int i) {
            this.zPos = 1;
            return this;
        }

        public Builder lightPopulated(boolean b) {
            this.lightPopulated = b;
            return this;
        }

        public Builder terrainPopulated(boolean b) {
            this.terrainPopulated = b;
            return this;
        }

        public Builder biomes(byte[] b) {
            this.biomes = b;
            return this;
        }

        public Builder heightMap(int[] i) {
            this.heightMap = i;
            return this;
        }

        public Chunk build() throws ParseException {
            if (this.sections.isEmpty() || this.xPos == -1 || this.zPos == -1 || ArrayUtils.isEmpty(this.biomes) || ArrayUtils.isEmpty(this.heightMap)) {
                throw new ParseException("Build failed: A value was unset.");
            }
            return new Chunk(this.sections, this.xPos, this.zPos, this.lastUpdate, this.lightPopulated, this.terrainPopulated, this.inhabitedTime, this.biomes, this.heightMap);
        }
    }
}
