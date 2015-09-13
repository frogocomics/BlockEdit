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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Represents a Minecraft chunk section, which holds 16 by 16 by 16 blocks.
 *
 * @author Jeff Chen
 */
public class Section {

    private byte y = 0;
    private byte[] blocks = new byte[4096];
    private byte[] add = new byte[4096];
    private byte[] data = new byte[2048];
    private byte[] blockLight = new byte[2048];
    private byte[] skyLight = new byte[2048];

    private Section(byte y, byte[] blocks, byte[] add, byte[] data, byte[] blockLight, byte[] skyLight) {
        this.y = y;
        this.blocks = blocks;
        this.add = add;
        this.data = data;
        this.blockLight = blockLight;
        this.skyLight = skyLight;
    }

    public ArrayList<Byte> getBlocks() {
        return getArrayList(this.blocks);
    }

    public Optional<ArrayList<Byte>> getAdditionalBlockData() {
        if (ArrayUtils.isNotEmpty(this.add)) {
            return Optional.of(getArrayList(this.add));
        }
        return Optional.empty();
    }

    public ArrayList<Byte> getBlockLight() {
        return getArrayList(this.blockLight);
    }

    public ArrayList<Byte> getSkyLight() {
        return getArrayList(this.blockLight);
    }

    private ArrayList<Byte> getArrayList(byte[] original) {
        Byte[] byteArray = new Byte[original.length];
        Arrays.setAll(byteArray, n -> original[n]);
        return new ArrayList<>(Arrays.asList(byteArray));
    }

    public int getY() {
        return this.y;
    }

    public Builder getBuilder() {
        return new Builder();
    }

    /**
     * Create a empty section filled with air.
     *
     * @param i The vertical location of the section
     * @return An empty section
     * @throws ParseException This should never be thrown
     */
    public static Section empty(byte i) throws ParseException {
        byte[] blocks = new byte[4096];
        Arrays.fill(blocks, (byte) 0);
        byte[] data = new byte[2048];
        Arrays.fill(data, (byte) 0);
        byte[] blockLight = new byte[2048];
        byte[] skyLight = new byte[2048];
        Arrays.fill(blockLight, (byte) -1);
        Arrays.fill(skyLight, (byte) -1);
        return new Builder().y(i).blocks(blocks).data(data).blockLight(blockLight).skyLight(skyLight).build().get();
    }

    public static Section fill(byte i, byte id) throws ParseException {
        byte[] blocks = new byte[4096];
        Arrays.fill(blocks, id);
        byte[] data = new byte[2048];
        Arrays.fill(data, id);
        byte[] blockLight = new byte[2048];
        byte[] skyLight = new byte[2048];
        Arrays.fill(blockLight, (byte) -1);
        Arrays.fill(skyLight, (byte) -1);
        return new Builder().y(i).blocks(blocks).data(data).blockLight(blockLight).skyLight(skyLight).build().get();
    }

    public static class Builder {

        public byte y = 0;
        private byte[] blocks = new byte[4096];
        private byte[] add = new byte[4096];
        private byte[] data = new byte[2048];
        private byte[] blockLight = new byte[2048];
        private byte[] skyLight = new byte[2048];

        public Builder() {
        }

        public Builder y(byte y) {
            this.y = y;
            return this;
        }

        public Builder blocks(byte[] blocks) throws ParseException {
            if (blocks.length != 4096) {
                throw new ParseException("The array length can only be 4096");
            }
            this.blocks = blocks;
            return this;
        }

        public Builder add(byte[] add) {
            this.add = add;
            return this;
        }

        public Builder data(byte[] data) throws ParseException {
            if (data.length != 2048) {
                throw new ParseException("The array length can only be 2048");
            }
            this.data = data;
            return this;
        }

        public Builder blockLight(byte[] blockLight) throws ParseException {
            if (blockLight.length != 2048) {
                throw new ParseException("The array length can only be 2048");
            }
            this.blockLight = blockLight;
            return this;
        }

        public Builder skyLight(byte[] skyLight) throws ParseException {
            if (skyLight.length != 2048) {
                throw new ParseException("The array length can only be 2048");
            }
            this.skyLight = skyLight;
            return this;
        }

        public Optional<Section> build() {
            return Optional.of(new Section(this.y, this.blocks, this.add, this.data, this.blockLight, this.skyLight));
        }
    }
}
