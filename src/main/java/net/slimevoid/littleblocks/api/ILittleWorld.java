package net.slimevoid.littleblocks.api;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface ILittleWorld extends IBlockAccess {

    public World getParentWorld();

    public boolean isOutdated(World world);

    public boolean isOutSideLittleWorld(int x, int y, int z);

    public void activeChunkPosition(ChunkPosition chunkposition, boolean forced);

    public HashMap<ChunkCoordinates, TileEntity> getAllTileEntities();

    public void addLoadedTileEntity(TileEntity tile);
}
