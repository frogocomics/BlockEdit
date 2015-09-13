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

import com.google.common.collect.ImmutableMap;

import org.blockedit.exception.DataException;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Contains a list of all the blocks in Minecraft. Each block is formatted like this:
 * <code>
 *    public static final {@link Triplet}&lt;Block Id, Block Name, Display Name&rt; A_BLOCK = new {@link Triplet}&lt;&rt;(4221, "minecraft:some_block", "Some Block");
 * </code>
 *
 * @author Jeff Chen
 */
public class BlockTypes {

    private static HashMap<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData[]>> blocks = new HashMap<>();
    private static boolean initialized = false;

    //TPDP
    public static HashMap<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData>> initialize() throws DataException {
        /**BlockTypes.blocks.put(0, new Quintet<>(0, "minecraft:air", "Air", false, new Block.BlockData[]{new Block.BlockData.Builder().data(0).build()}));
        BlockTypes.blocks.put(1, new Quintet<>(1, "minecraft:stone", "Stone", false, new Block.BlockData[]{new Block.BlockData.Builder().data(0).build()}));
        BlockTypes.blocks.put(2, new Quintet<>(2, "minecraft:grass", "Grass Block"));
        BlockTypes.blocks.put(3, new Quintet<>(3, "minecraft:dirt", "Dirt"));
        BlockTypes.blocks.put(4, new Quintet<>(4, "minecraft:cobblestone", "Cobblestone"));
        BlockTypes.blocks.put(5, new Quintet<>(5, "minecraft:planks", "Wood Plank"));
        BlockTypes.blocks.put(6, new Quintet<>(6, "minecraft:sapling", "Sapling"));
        BlockTypes.blocks.put(7, new Quintet<>(7, "minecraft:bedrock", "Bedrock"));
        BlockTypes.blocks.put(8, new Quintet<>(8, "minecraft:flowing_water", "Flowing Water"));
        BlockTypes.blocks.put(9, new Quintet<>(9, "minecraft:water", "Still Water"));
        BlockTypes.blocks.put(10, new Quintet<>(10, "minecraft:flowing_lava", "Flowing Lava"));
        BlockTypes.blocks.put(11, new Quintet<>(11, "minecraft:lava", "Still Lava"));
        BlockTypes.blocks.put(12, new Quintet<>(12, "minecraft:sand", "Sand"));
        BlockTypes.blocks.put(13, new Quintet<>(13, "minecraft:gravel", "Gravel"));
        BlockTypes.blocks.put(14, new Quintet<>(14, "minecraft:gold_ore", "Gold Ore"));
        BlockTypes.blocks.put(15, new Quintet<>(15, "minecraft:iron_ore", "Iron Ore"));
        BlockTypes.blocks.put(16, new Quintet<>(16, "minecraft:coal_ore", "Coal Ore"));
        BlockTypes.blocks.put(17, new Quintet<>(17, "minecraft:log", "Oak Wood"));
        BlockTypes.blocks.put(18, new Quintet<>(18, "minecraft:leaves", "Oak Leaves"));
        BlockTypes.blocks.put(19, new Quintet<>(19, "minecraft:sponge", "Sponge"));
        BlockTypes.blocks.put(20, new Quintet<>(20, "minecraft:glass", "Glass"));
        BlockTypes.blocks.put(21, new Quintet<>(21, "minecraft:lapis_ore", "Lapis Lazuli Ore"));
        BlockTypes.blocks.put(22, new Quintet<>(22, "minecraft:lapis_block", "Lapis Lazuli Block"));
        BlockTypes.blocks.put(23, new Quintet<>(23, "minecraft:dispenser", "Dispenser"));
        BlockTypes.blocks.put(24, new Quintet<>(24, "minecraft:sandstone", "Sandstone"));
        BlockTypes.blocks.put(25,new Quintet<>(25, "minecraft:noteblock", "Note Block"));
        BlockTypes.blocks.put(26, new Quintet<>(26, "minecraft:bed", "Bed"));
        BlockTypes.blocks.put(27, new Quintet<>(27, "minecraft:golden_rail", "Powered Rail"));
        BlockTypes.blocks.put(28, new Quintet<>(28, "minecraft:detector_rail", "Detector Rail"));
        BlockTypes.blocks.put(29, new Quintet<>(29, "minecraft:sticky_piston", "Sticky Piston"));
        BlockTypes.blocks.put(30, new Quintet<>(30, "minecraft:cobweb", "Cobweb"));
        BlockTypes.blocks.put(31, new Quintet<>(31, "minecraft:tallgrass", "Dead Shrub"));
        BlockTypes.blocks.put(32, new Quintet<>(32, "minecraft:deadbush", "Dead Bush"));
        BlockTypes.blocks.put(33, new Quintet<>(33, "minecraft:piston", "Piston"));
        BlockTypes.blocks.put(34, new Quintet<>(34, "minecraft:piston_head", "Piston Head"));
        BlockTypes.blocks.put(35, new Quintet<>(35, "minecraft:wool", "Wool"));
        BlockTypes.blocks.put(37, new Quintet<>(37, "minecraft:yellow_flower", "Dandelion"));
        BlockTypes.blocks.put(38, new Quintet<>(38, "minecraft:red_flower", "Poppy"));
        BlockTypes.blocks.put(39, new Quintet<>(39, "minecraft:brown_mushroom", "Brown Mushroom"));
        BlockTypes.blocks.put(40, new Quintet<>(40, "minecraft:red_mushroom", "Red Mushroom"));
        BlockTypes.blocks.put(41, new Quintet<>(41, "minecraft:gold_block", "Gold Block"));
        BlockTypes.blocks.put(42, new Quintet<>(42, "minecraft:iron_block", "Iron Block"));
        BlockTypes.blocks.put(43, new Quintet<>(43, "minecraft:double_stone_slab", "Double Stone Slab"));
        BlockTypes.blocks.put(44, new Quintet<>(44, "minecraft:stone_slab", "Stone Slab"));
        BlockTypes.blocks.put(45, new Quintet<>(45, "minecraft:brick_block", "Bricks"));
        BlockTypes.blocks.put(46, new Quintet<>(46, "minecraft:tnt", "TNT"));
        BlockTypes.blocks.put(47, new Quintet<>(47, "minecraft:bookshelf", "Bookshelf"));
        BlockTypes.blocks.put(48, new Quintet<>(48, "minecraft:mossy_cobblestone", "Moss Stone"));
        BlockTypes.blocks.put(49, new Quintet<>(49, "minecraft:obsidin", "Obsidian"));
        BlockTypes.blocks.put(50, new Quintet<>(50, "minecraft.torch", "Torch"));
        BlockTypes.blocks.put(51, new Quintet<>(51, "minecraft:fire", "Fire"));
        BlockTypes.blocks.put(52, new Quintet<>(52, "minecraft:mob_spawner", "Monster Spawner"));
        BlockTypes.blocks.put(53, new Quintet<>(53, "minecraft:oak_stairs", "Oak Wood Stairs"));
        BlockTypes.blocks.put(54, new Quintet<>(54, "minecraft:chest", "Chest"));
        BlockTypes.blocks.put(55, new Quintet<>(55, "minecraft:redstone_wire", "Redstone Wire"));
        BlockTypes.blocks.put(56, new Quintet<>(56, "minecraft:diamond_ore", "Diamond Ore"));
        BlockTypes.blocks.put(57, new Quintet<>(57, "minecraft:diamond_block", "Diamond Block"));
        BlockTypes.blocks.put(58, new Quintet<>(58, "minecraft:crafting_table", "Crafting Table"));
        BlockTypes.blocks.put(59, new Quintet<>(59, "minecraft:wheat", "Wheat"));
        BlockTypes.blocks.put(60, new Quintet<>(60, "minecraft:farmland", "Farmland"));
        BlockTypes.initialized = true;
        return BlockTypes.blocks;**/
        return null;
    }

    public static Optional<Quintet<Integer, String, String, Boolean, Block.BlockData[]>> fromId(int id) {
        if(!BlockTypes.initialized) {
            return Optional.empty();
        } else {
            if(BlockTypes.blocks.containsKey(id)) {
                return Optional.of(BlockTypes.blocks.get(id));
            } else {
                return Optional.empty();
            }
        }
    }

    public static Optional<String> getBlockDisplayName(int id) {
        if(BlockTypes.blocks.isEmpty()) {
            return Optional.empty();
        } else {
            if(BlockTypes.blocks.containsKey(id)) {
                return Optional.of(BlockTypes.blocks.get(id).getValue2());
            } else {
                return Optional.empty();
            }
        }
    }

    public static Optional<String> getBlockName(int id) {
        if(BlockTypes.blocks.isEmpty()) {
            return Optional.empty();
        } else {
            if(BlockTypes.blocks.containsKey(id)) {
                return Optional.of(BlockTypes.blocks.get(id).getValue1());
            } else {
                return Optional.empty();
            }
        }
    }

    public static ImmutableMap<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData[]>> getBlocks() {
        ImmutableMap.Builder<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData[]>> builder = new ImmutableMap.Builder<>();
        Iterator<Map.Entry<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData[]>>> iterator = BlockTypes.blocks.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer, Quintet<Integer, String, String, Boolean, Block.BlockData[]>> next = iterator.next();
            builder.put(next.getKey(), next.getValue());
        }
        return builder.build();
    }
}
