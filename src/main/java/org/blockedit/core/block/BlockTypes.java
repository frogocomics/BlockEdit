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

import org.javatuples.Triplet;

/**
 * Contains a list of all the blocks in Minecraft. Each block is formatted like this:
 * <code>
 *    public static final {@link Triplet}&lt;Block Id, Block Name, Display Name&rt; A_BLOCK = new {@link Triplet}&lt;&rt;(4221, "minecraft:some_block", "Some Block");
 * </code>
 *
 * @author Jeff Chen
 */
public class BlockTypes {

    public static final Triplet<Integer, String, String> AIR = new Triplet<>(0, "minecraft:air", "Air");
    public static final Triplet<Integer, String, String> STONE = new Triplet<>(1, "minecraft:stone", "Stone");
    public static final Triplet<Integer, String, String> GRASS = new Triplet<>(2, "minecraft:grass", "Grass Block");
    public static final Triplet<Integer, String, String> DIRT = new Triplet<>(3, "minecraft:dirt", "Dirt");
    public static final Triplet<Integer, String, String> COBBLESTONE = new Triplet<>(4, "minecraft:cobblestone", "Cobblestone");
    public static final Triplet<Integer, String, String> PLANKS = new Triplet<>(5, "minecraft:planks", "Wood Plank");
    public static final Triplet<Integer, String, String> SAPLING = new Triplet<>(6, "minecraft:sapling", "Sapling");
    public static final Triplet<Integer, String, String> BEDROCK = new Triplet<>(7, "minecraft:bedrock", "Bedrock");
    public static final Triplet<Integer, String, String> FLOWING_WATER = new Triplet<>(8, "minecraft:flowing_water", "Flowing Water");
    public static final Triplet<Integer, String, String> STILL_WATER = new Triplet<>(9, "minecraft:water", "Still Water");
    public static final Triplet<Integer, String, String> FLOWING_LAVA = new Triplet<>(10, "minecraft:flowing_lava", "Flowing Lava");
    public static final Triplet<Integer, String, String> STILL_LAVA = new Triplet<>(11, "minecraft:lava", "Still Lava");
    public static final Triplet<Integer, String, String> SAND = new Triplet<>(12, "minecraft:sand", "Sand");
    public static final Triplet<Integer, String, String> GRAVEL = new Triplet<>(13, "minecraft:gravel", "Gravel");
    public static final Triplet<Integer, String, String> GOLD_ORE = new Triplet<>(14, "minecraft:gold_ore", "Gold Ore");
    public static final Triplet<Integer, String, String> IRON_ORE = new Triplet<>(15, "minecraft:iron_ore", "Iron Ore");
    public static final Triplet<Integer, String, String> COAL_ORE = new Triplet<>(16, "minecraft:coal_ore", "Coal Ore");
    public static final Triplet<Integer, String, String> LOG = new Triplet<>(17, "minecraft:log", "Oak Wood");
    public static final Triplet<Integer, String, String> LEAVES = new Triplet<>(18, "minecraft:leaves", "Oak Leaves");
    public static final Triplet<Integer, String, String> SPONGE = new Triplet<>(19, "minecraft:sponge", "Sponge");
    public static final Triplet<Integer, String, String> GLASS = new Triplet<>(20, "minecraft:glass", "Glass");
    public static final Triplet<Integer, String, String> LAPIS_ORE = new Triplet<>(21, "minecraft:lapis_ore", "Lapis Lazuli Ore");
    public static final Triplet<Integer, String, String> LAPIS_BLOCK = new Triplet<>(22, "minecraft:lapis_block", "Lapis Lazuli Block");
    public static final Triplet<Integer, String, String> DISPENSER = new Triplet<>(23, "minecraft:dispenser", "Dispenser");
    public static final Triplet<Integer, String, String> SANDSTONE = new Triplet<>(24, "minecraft:sandstone", "Sandstone");
    public static final Triplet<Integer, String, String> NOTE_BLOCK = new Triplet<>(25, "minecraft:noteblock", "Note Block");
    public static final Triplet<Integer, String, String> BED = new Triplet<>(26, "minecraft:bed", "Bed");
    public static final Triplet<Integer, String, String> POWERED_RAIL = new Triplet<>(27, "minecraft:golden_rail", "Powered Rail");
    public static final Triplet<Integer, String, String> DETECTOR_RAIL = new Triplet<>(28, "minecraft:detector_rail", "Detector Rail");
    public static final Triplet<Integer, String, String> STICKY_PISTON = new Triplet<>(29, "minecraft:sticky_piston", "Sticky Piston");
    public static final Triplet<Integer, String, String> COBWEB = new Triplet<>(30, "minecraft:cobweb", "Cobweb");
    public static final Triplet<Integer, String, String> DEAD_SHRUB = new Triplet<>(31, "minecraft:tallgrass", "Dead Shrub");
    public static final Triplet<Integer, String, String> DEAD_BUSH = new Triplet<>(32, "minecraft:deadbush", "Dead Bush");
    public static final Triplet<Integer, String, String> PISTON = new Triplet<>(33, "minecraft:piston", "Piston");
    public static final Triplet<Integer, String, String> PISTON_HEAD = new Triplet<>(34, "minecraft:piston_head", "Piston Head");
    public static final Triplet<Integer, String, String> WOOL = new Triplet<>(35, "minecraft:wool", "Wool");
    public static final Triplet<Integer, String, String> YELLOW_FLOWER = new Triplet<>(37, "minecraft:yellow_flower", "Dandelion");
    public static final Triplet<Integer, String, String> RED_FLOWER = new Triplet<>(38, "minecraft:red_flower", "Poppy");
    public static final Triplet<Integer, String, String> BROWN_MUSHROOM = new Triplet<>(39, "minecraft:brown_mushroom", "Brown Mushroom");
    public static final Triplet<Integer, String, String> RED_MUSHROOM = new Triplet<>(40, "minecraft:red_mushroom", "Red Mushroom");
    public static final Triplet<Integer, String, String> GOLD_BLOCK = new Triplet<>(41, "minecraft:gold_block", "Gold Block");
    public static final Triplet<Integer, String, String> IRON_BLOCK = new Triplet<>(42, "minecraft:iron_block", "Iron Block");
    public static final Triplet<Integer, String, String> DOUBLE_STONE_SLAB = new Triplet<>(43, "minecraft:double_stone_slab", "Double Stone Slab");
    public static final Triplet<Integer, String, String> STONE_SLAB = new Triplet<>(44, "minecraft:stone_slab", "Stone Slab");
    public static final Triplet<Integer, String, String> BRICKS = new Triplet<>(45, "minecraft:brick_block", "Bricks");
    public static final Triplet<Integer, String, String> TNT = new Triplet<>(46, "minecraft:tnt", "TNT");
    public static final Triplet<Integer, String, String> BOOKSHELF = new Triplet<>(47, "minecraft:bookshelf", "Bookshelf");
    public static final Triplet<Integer, String, String> MOSS_STONE = new Triplet<>(48, "minecraft:mossy_cobblestone", "Moss Stone");
    public static final Triplet<Integer, String, String> OBSIDIAN = new Triplet<>(49, "minecraft:obsidin", "Obsidian");
    public static final Triplet<Integer, String, String> TORCH = new Triplet<>(50, "minecraft.torch", "Torch");
    public static final Triplet<Integer, String, String> FIRE = new Triplet<>(51, "minecraft:fire", "Fire");
    public static final Triplet<Integer, String, String> MOB_SPAWNER = new Triplet<>(52, "minecraft:mob_spawner", "Monster Spawner");
    public static final Triplet<Integer, String, String> OAK_STAIRS = new Triplet<>(53, "minecraft:oak_stairs", "Oak Wood Stairs");
    public static final Triplet<Integer, String, String> CHEST = new Triplet<>(54, "minecraft:chest", "Chest");
    public static final Triplet<Integer, String, String> REDSTONE_WIRE = new Triplet<>(55, "minecraft:redstone_wire", "Redstone Wire");
    public static final Triplet<Integer, String, String> DIAMOND_ORE = new Triplet<>(56, "minecraft:diamond_ore", "Diamond Ore");
    public static final Triplet<Integer, String, String> DIAMOND_BLOCK = new Triplet<>(57, "minecraft:diamond_block", "Diamond Block");
    public static final Triplet<Integer, String, String> CRAFTING_TABLE = new Triplet<>(58, "minecraft:crafting_table", "Crafting Table");
    public static final Triplet<Integer, String, String> WHEAT_CROPS = new Triplet<>(59, "minecraft:wheat", "Wheat");
    public static final Triplet<Integer, String, String> FARMLAND = new Triplet<>(60, "minecraft:farmland", "Farmland");
}
