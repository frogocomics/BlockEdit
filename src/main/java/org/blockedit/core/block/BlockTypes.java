package org.blockedit.core.block;

import org.javatuples.Triplet;

/**
 * Contains a list of all the blocks in Minecraft. Each block is formatted like this:
 * <code>
*     public static final {@link Triplet}&lt;Block Id, Block Name, Display Name&rt; A_BLOCK = new {@link Triplet}&lt;&rt;(4221, "minecraft:some_block", "Some Block");
 * </code>
 *
 *
 */
public class BlockTypes {

    public static final Triplet<Integer, String, String> AIR = new Triplet<>(0, "minecraft:air", "Air");
}
