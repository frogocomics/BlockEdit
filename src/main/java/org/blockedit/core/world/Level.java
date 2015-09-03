package org.blockedit.core.world;

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.stream.NbtOutputStream;
import com.evilco.mc.nbt.tag.TagType;
import org.javatuples.Pair;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

        public void write() throws IOException {

        }
    }
}
