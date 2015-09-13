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

package org.blockedit.core.block;


import org.blockedit.core.output.BlockStorageFormat;
import org.blockedit.exception.DataBuildException;
import org.blockedit.exception.DataException;
import org.javatuples.Quintet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class Block implements Serializable {

    private int id = 0;
    private String name;
    private String displayName;
    private boolean isModded = false;
    private BlockData[] blockData;

    private Block(Quintet<Integer, String, String, Boolean, Block.BlockData[]> blockData) {
        this.id = blockData.getValue0();
        this.name = blockData.getValue1();
        this.displayName = blockData.getValue2();
        this.isModded = blockData.getValue3();
        this.blockData = blockData.getValue4();
    }

    public BlockStorageFormat getExporter() {
        return new BlockStorageFormat();
    }

    public int getId() {
        return this.id;
    }

    public String getBlockName() {
        return this.name;
    }

    public String getBlockDisplayName() {
        return this.displayName;
    }

    public boolean isModded() {
        return this.isModded;
    }

    public BlockData[] getBlockData() {
        return this.blockData;
    }

    public static class Builder {

        private int id = 0;
        private String name;
        private String displayName;
        private BlockData[] blockData;
        private boolean isModded = false;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder blockName(String name) {
            this.name = name;
            return this;
        }

        public Builder blockDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder blockDataValues(BlockData[] data) {
            this.blockData = data;
            return this;
        }


        public Block.BlockData[] getBlockDataValues() {
            return this.blockData;
        }

        public Block build() throws DataBuildException {
            if (name == null || displayName == null) {
                throw new DataBuildException("name and displayName were left null!");
            }
            for (Map.Entry<Integer, Quintet<Integer, String, String, Boolean, BlockData[]>> next : BlockTypes.getBlocks().entrySet()) {
                if (next.getKey() == this.id) {
                    this.isModded = true;
                }
            }
            return new Block(new Quintet<>(this.id, this.name, this.displayName, this.isModded, this.blockData));
        }
    }

    public static class BlockData {

        private int data = 0;
        private File image;

        private BlockData(int data, File image) {
            this.data = data;
            this.image = image;
        }

        public int getDataValue() {
            return this.data;
        }

        public File getImageLocation() {
            return this.image;
        }

        public Builder getBuilder() {
            return new Builder();
        }

        public static class Builder {

            private int data = 0;
            private File image;

            public Builder data(int data) throws DataException {
                if (data < 0) {
                    throw new DataException("Data cannot be a negative value!");
                } else {
                    this.data = 0;
                }
                return this;
            }

            public Builder image(File image) {
                this.image = image;
                return this;
            }

            public BlockData build() {
                return new BlockData(this.data, this.image);
            }
        }
    }
}
