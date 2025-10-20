package net.slimevoid.littleblocks.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LittleFakeChunk extends Chunk {

    public LittleFakeChunk(World world, int x, int z) {
        super(world, x, z);
    }

    @Override
    public boolean isAtLocation(int x, int z) {
        throw new RuntimeException();
    }

    @Override
    public int getHeightValue(int x, int z) {
        throw new RuntimeException();
    }

    @Override
    public int getTopFilledSegment() {
        throw new RuntimeException();
    }

    @Override
    public ExtendedBlockStorage[] getBlockStorageArray() {
        throw new RuntimeException();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void generateHeightMap() {
        throw new RuntimeException();
    }

    @Override
    public void generateSkylightMap() {
        throw new RuntimeException();
    }

    @Override
    public int func_150808_b(int p_150808_1_, int p_150808_2_, int p_150808_3_) {
        throw new RuntimeException();
    }

    @Override
    public Block getBlock(final int p_150810_1_, final int p_150810_2_, final int p_150810_3_) {
        throw new RuntimeException();
    }

    @Override
    public int getBlockMetadata(int p_76628_1_, int p_76628_2_, int p_76628_3_) {
        throw new RuntimeException();
    }

    public boolean func_150807_a(int p_150807_1_, int p_150807_2_, int p_150807_3_, Block p_150807_4_,
        int p_150807_5_) {
        throw new RuntimeException();
    }

    @Override
    public boolean setBlockMetadata(int p_76589_1_, int p_76589_2_, int p_76589_3_, int p_76589_4_) {
        throw new RuntimeException();
    }

    @Override
    public int getSavedLightValue(EnumSkyBlock p_76614_1_, int p_76614_2_, int p_76614_3_, int p_76614_4_) {
        throw new RuntimeException();
    }

    @Override
    public void setLightValue(EnumSkyBlock p_76633_1_, int p_76633_2_, int p_76633_3_, int p_76633_4_, int p_76633_5_) {
        throw new RuntimeException();
    }

    @Override
    public int getBlockLightValue(int p_76629_1_, int p_76629_2_, int p_76629_3_, int p_76629_4_) {
        throw new RuntimeException();
    }

    @Override
    public void addEntity(Entity p_76612_1_) {
        throw new RuntimeException();
    }

    @Override
    public void removeEntity(Entity p_76622_1_) {
        throw new RuntimeException();
    }

    @Override
    public void removeEntityAtIndex(Entity p_76608_1_, int p_76608_2_) {
        throw new RuntimeException();
    }

    @Override
    public boolean canBlockSeeTheSky(int p_76619_1_, int p_76619_2_, int p_76619_3_) {
        throw new RuntimeException();
    }

    @Override
    public TileEntity func_150806_e(int p_150806_1_, int p_150806_2_, int p_150806_3_) {
        throw new RuntimeException();
    }

    @Override
    public void addTileEntity(TileEntity p_150813_1_) {
        throw new RuntimeException();
    }

    @Override
    public void func_150812_a(int p_150812_1_, int p_150812_2_, int p_150812_3_, TileEntity p_150812_4_) {
        throw new RuntimeException();
    }

    @Override
    public void removeTileEntity(int p_150805_1_, int p_150805_2_, int p_150805_3_) {
        throw new RuntimeException();
    }

    @Override
    public void onChunkLoad() {
        throw new RuntimeException();
    }

    @Override
    public void onChunkUnload() {
        throw new RuntimeException();
    }

    @Override
    public void setChunkModified() {
        throw new RuntimeException();
    }

    @Override
    public void getEntitiesWithinAABBForEntity(Entity p_76588_1_, AxisAlignedBB p_76588_2_,
        List<net.minecraft.entity.Entity> p_76588_3_, IEntitySelector p_76588_4_) {
        throw new RuntimeException();
    }

    @Override
    public <T> void getEntitiesOfTypeWithinAAAB(Class<T> p_76618_1_, AxisAlignedBB p_76618_2_, List<T> p_76618_3_,
        IEntitySelector p_76618_4_) {
        throw new RuntimeException();
    }

    @Override
    public boolean needsSaving(boolean p_76601_1_) {
        throw new RuntimeException();
    }

    @Override
    public Random getRandomWithSeed(long p_76617_1_) {
        throw new RuntimeException();
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException();
    }

    @Override
    public void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int p_76624_3_, int p_76624_4_) {
        throw new RuntimeException();
    }

    @Override
    public int getPrecipitationHeight(int p_76626_1_, int p_76626_2_) {
        throw new RuntimeException();
    }

    @Override
    public void func_150804_b(boolean p_150804_1_) {
        throw new RuntimeException();
    }

    @Override
    public boolean func_150802_k() {
        throw new RuntimeException();
    }

    @Override
    public ChunkCoordIntPair getChunkCoordIntPair() {
        throw new RuntimeException();
    }

    @Override
    public boolean getAreLevelsEmpty(int p_76606_1_, int p_76606_2_) {
        throw new RuntimeException();
    }

    @Override
    public void setStorageArrays(ExtendedBlockStorage[] p_76602_1_) {
        throw new RuntimeException();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void fillChunk(byte[] p_76607_1_, int p_76607_2_, int p_76607_3_, boolean p_76607_4_) {
        throw new RuntimeException();
    }

    @Override
    public BiomeGenBase getBiomeGenForWorldCoords(int p_76591_1_, int p_76591_2_, WorldChunkManager p_76591_3_) {
        throw new RuntimeException();
    }

    @Override
    public byte[] getBiomeArray() {
        throw new RuntimeException();
    }

    @Override
    public void setBiomeArray(byte[] p_76616_1_) {
        throw new RuntimeException();
    }

    @Override
    public void resetRelightChecks() {
        throw new RuntimeException();
    }

    @Override
    public void enqueueRelightChecks() {
        throw new RuntimeException();
    }

    @Override
    public void func_150809_p() {
        throw new RuntimeException();
    }

    @Override
    public TileEntity getTileEntityUnsafe(int x, int y, int z) {
        throw new RuntimeException();
    }

    @Override
    public void removeInvalidTileEntity(int x, int y, int z) {
        throw new RuntimeException();
    }
}
