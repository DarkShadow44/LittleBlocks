package net.slimevoid.littleblocks.compat;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import codechicken.multipart.handler.MultipartSaveLoad;

public final class ForgeMultipartCompat {

    public void loadTiles(World world, Map<ChunkPosition, TileEntity> tiles) {
        MultipartSaveLoad.loadTiles(world, tiles);
    }
}
