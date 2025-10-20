package net.slimevoid.littleblocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.slimevoid.littleblocks.api.ILittleWorld;

public class BlockLBPistonBase {

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By
     * default, passes it on to the tile entity at this location. Args: world,
     * x, y, z, blockID, EventID, event parameter
     */
    public static boolean onEventReceived(Block block, World world, int x, int y, int z, int eventId, int eventData) {
        int xOffset = Facing.offsetsXForSide[eventData];
        int yOffset = Facing.offsetsYForSide[eventData];
        int zOffset = Facing.offsetsZForSide[eventData];
        if (eventId == 0 && world instanceof ILittleWorld) {
            for (int i = 0; i < 14; i++) {
                int x2 = x + i * xOffset;
                int y2 = y + i * yOffset;
                int z2 = z + i * zOffset;
                if (((ILittleWorld) world).isOutSideLittleWorld(x2, y2, z2)) {
                    return false;
                }
                if (world.isAirBlock(x2, y2, z2)) {
                    break;
                }
            }
        }
        return block.onBlockEventReceived(world, x, y, z, eventId, eventData);
    }
}
