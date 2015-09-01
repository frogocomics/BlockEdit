package org.blockedit.core.world;

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.tag.TagCompound;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class WorldLoader {


    public static void main(String[] args) {
        try {
            NbtInputStream stream = new NbtInputStream(new DataInputStream(new FileInputStream(new File("C:\\Users\\Jeff\\AppData\\Roaming\\.minecraft\\saves\\New World\\region\\r.-3.-2.mca"))));
            TagCompound tag = new TagCompound(stream, false);
            System.out.println(tag.getTagID());
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
