package org.blockedit.core.world;

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.stream.NbtOutputStream;
import com.evilco.mc.nbt.tag.*;
import org.javatuples.Pair;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

public class Level {

    public TreeMap<String, Pair<Object, TagType>> levelData;

    private Level(TreeMap<String, Pair<Object, TagType>> levelData) {
        this.levelData = levelData;
    }

    public TreeMap<String, Pair<Object, TagType>> getLevel() {
        return this.levelData;
    }

    public Exporter getLevelExporter(NbtOutputStream stream) {
        return new Exporter(stream, this.levelData);
    }

    public static class Builder {

        private TreeMap<String, Pair<Object, TagType>> levelData = new TreeMap<>();

        public Builder() {}

        public Builder(TreeMap<String, Pair<Object, TagType>> parent) {
            this.levelData = parent;
        }

        public static Builder fromParent(Level parent) {
            return new Builder(parent.getLevel());
        }

        public Builder setting(String k, Pair<Object, TagType> v) {
            this.levelData.put(k, v);
            return this;
        }

        public Level build() {
            return new Level(this.levelData);
        }
    }

     public static class Loader {

        private NbtInputStream stream;

        public Loader(File location) throws IOException {
            this.stream = new NbtInputStream(new GZIPInputStream(new DataInputStream(new FileInputStream(location))));
        }

        public NbtInputStream getInputStream() {
            return this.stream;
        }
    }

    private class Exporter {

        private NbtOutputStream stream;
        private TreeMap<String, Pair<Object, TagType>> levelData;

        private Exporter(NbtOutputStream stream, TreeMap<String, Pair<Object, TagType>> levelData) {
            this.stream = stream;
            this.levelData = levelData;
        }

        public void write() throws IOException, DataException {

            TagCompound data = new TagCompound("Data");

            for(Map.Entry<String, Pair<Object, TagType>> entry : this.levelData.entrySet()) {
                String key = entry.getKey();

                TagType type = entry.getValue().getValue1();
                if(type == TagType.BYTE) {
                    if(!(entry.getValue().getValue0() instanceof Byte)) {
                        throw new DataException("Value " + entry.getKey() + " is not a byte");
                    }
                    data.getTags().put(entry.getKey(), new TagByte(entry.getKey(), (byte) entry.getValue().getValue0()));
                }
                if(type == TagType.BYTE_ARRAY) {
                    if(!(entry.getValue().getValue0() instanceof Byte[])) {
                        throw new DataException("Value " + entry.getKey() + " is not a byte array");
                    }
                    data.getTags().put(entry.getKey(), new TagByteArray(entry.getKey(), (byte[]) entry.getValue().getValue0()));
                }
                if(type == TagType.COMPOUND) {
                    throw new DataException("Compound Tags are not allowed!");
                }
                if(type == TagType.DOUBLE) {
                    if(!(entry.getValue().getValue0() instanceof Double)) {
                        throw new DataException("Value " + entry.getKey() + " is not a double");
                    }
                    data.getTags().put(entry.getKey(), new TagDouble(entry.getKey(), (double) entry.getValue().getValue0()));
                }
                if(type == TagType.END) {
                    throw new DataException("End Tags are not allowed!");
                }
                if(type == TagType.FLOAT) {
                    if(!(entry.getValue().getValue0() instanceof Float)) {
                        throw new DataException("Value " + entry.getKey() + " is not a float!");
                    }
                }
                if(type == TagType.INTEGER) {
                    if(!(entry.getValue().getValue0() instanceof Integer)) {
                        throw new DataException("Value " + entry.getKey() + " is not an integer!");
                    }
                }
                if(type == TagType.INTEGER_ARRAY) {
                    if(!(entry.getValue().getValue0() instanceof Integer[])) {
                        throw new DataException("Value " + entry.getKey() + " is not an integer array!");
                    }
                }
                if(type == TagType.LIST) {
                    throw new DataException("List Tags are not allowed!");
                }
                if(type == TagType.LONG) {
                    if(!(entry.getValue().getValue0() instanceof Long)) {
                        throw new DataException("Value " + entry.getKey() + " is not a long");
                    }
                }
                if(type == TagType.SHORT) {
                    if(!(entry.getValue().getValue0() instanceof Short)) {
                        throw new DataException("Value " + entry.getKey() + " is not a short");
                    }
                }
                if(type == TagType.STRING) {
                    if(!(entry.getValue().getValue0() instanceof String)) {
                        throw new DataException("value " + entry.getKey() + " is not a string");
                    }
                }
            }

            this.stream.write(data);
        }
    }
}
