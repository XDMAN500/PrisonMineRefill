package me.varmetek.plugin.prisonminerefill.blockplace.v1_13_R2;

import com.google.common.base.Preconditions;
import me.varmetek.plugin.prisonminerefill.blockplace.ChunkBlock;
import me.varmetek.plugin.prisonminerefill.blockplace.ChunkPlacer;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_13_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_13_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

public class ChunkPlacer_v1_13_R2 implements ChunkPlacer
{

  protected static  Field F_SECTION;
  protected static  Field F_UPDATE;


  static{
    Class<net.minecraft.server.v1_13_R2.Chunk> chunkClass = net.minecraft.server.v1_13_R2.Chunk.class;
    try {
      F_SECTION = chunkClass.getDeclaredField( "sections");
      F_SECTION.setAccessible(true);


      F_UPDATE = chunkClass.getDeclaredField("x");
      F_UPDATE.setAccessible(true);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void placeAll (Chunk chunk, Collection<ChunkBlock> changes) {
    net.minecraft.server.v1_13_R2.Chunk nmsChunk  = ((CraftChunk)chunk).getHandle();
    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|placeAll");

    try {
      placeNms(nmsChunk, changes);
    }catch (IllegalAccessException | IllegalArgumentException ex){
      Bukkit.getLogger().log(Level.WARNING, "Exception placing all chunk", ex);
      return;

    }
  }

  @Override
  public void place (Chunk chunk, ChunkBlock change) {


    net.minecraft.server.v1_13_R2.Chunk nmsChunk  = ((CraftChunk)chunk).getHandle();
    try {
      placeNms(nmsChunk, change);
    }catch (IllegalAccessException | IllegalArgumentException ex){
      Bukkit.getLogger().log(Level.WARNING, "Could not single place",ex);
      return;

    }
  }



  protected void placeNms(net.minecraft.server.v1_13_R2.Chunk chunk, ChunkBlock change) throws IllegalAccessException, IllegalArgumentException{
      ChunkSection[] sections = (ChunkSection[])F_SECTION.get(chunk);


      chunkPlace2(chunk,change);
      F_UPDATE.setBoolean(chunk,true);

  }

  protected void placeNms(net.minecraft.server.v1_13_R2.Chunk chunk, Collection<ChunkBlock> change) throws IllegalAccessException, IllegalArgumentException{
    ChunkSection[] sections = (ChunkSection[])F_SECTION.get(chunk);

    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|placeNms");
    for(ChunkBlock ch : change){

      chunkPlace(chunk,sections,ch);
    }

    F_UPDATE.setBoolean(chunk,true);

  }


  public void sendBlockChanges(Player player, Chunk chunk, Collection<ChunkBlock> changes){
    CraftPlayer cPlayer = (CraftPlayer)player;
    PlayerConnection connection = cPlayer.getHandle().playerConnection;
    net.minecraft.server.v1_13_R2.Chunk nmsChunk = ((CraftChunk)chunk).getHandle();
    short[] ebe = new short[changes.size()];
    int index = 0;

    for(ChunkBlock chunkBlock : changes){
      ebe[index] = (short) chunkBlock.getCompactCoords();
      index +=1;
    }

    PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange(ebe.length,ebe,nmsChunk);

    connection.sendPacket(packet);
  }

  protected void chunkPlace2(net.minecraft.server.v1_13_R2.Chunk chunk, ChunkBlock change) {
    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|chunkPlace2");
    BlockPosition pos = new BlockPosition(
      (chunk.locX << 4) | change.getX(),
      change.getY(),
      (chunk.locZ << 4) | change.getZ());

    try {
      Bukkit.getLogger().info("-ChunkPos : " + change.getX() + " " + change.getY() + " " + change.getZ());
      Bukkit.getLogger().info("-Position : " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
      ChunkBlock check = new ChunkBlock(change.getId(), pos.getX(), pos.getY(), pos.getZ());
      Bukkit.getLogger().info("-PosCheck : " + check.getX() + " " + check.getY() + " " + check.getZ());


      org.bukkit.Material material = change.getMaterial();
      World world = chunk.world;

      setBlock(world, material, pos);
    }catch (Exception ex){

      Bukkit.getLogger().log(Level.WARNING , "Error in chunkPlace2", ex);
    }
  }

  protected void chunkPlace(net.minecraft.server.v1_13_R2.Chunk chunk, ChunkSection[] sections, ChunkBlock change) {
    BlockPosition blockposition = new BlockPosition(chunk.locX << 4 | change.getX(), change.getY(), chunk.locZ << 4 | change.getZ());

    int cy = change.getY() & 0xFF;
    int cx = change.getX();
    int cz = change.getZ();

    if(cy >> 4 < 0) {
      Bukkit.broadcastMessage(" Weird cy " + cy + " "+ (cy >> 4));
    }
    org.bukkit.Material material = change.getMaterial();
    Preconditions.checkNotNull(material, "Material for chunk place is null");
    IBlockData newBlock = CraftMagicNumbers.getBlock(material).getBlockData();

    //int l = ((HeightMap)chunk.heightMap.get(HeightMap.Type.LIGHT_BLOCKING)).a(change.getX(), change.getZ());
    IBlockData oldBlock = chunk.getType(blockposition);
    if (oldBlock == newBlock) {
      return;
    } else {
      Block block = newBlock.getBlock();
      Block block1 = oldBlock.getBlock();
      ChunkSection chunksection = sections[cy >> 4];

      if (chunksection == null) {
        if (newBlock.isAir()) {
          return;
        }

        chunksection = new ChunkSection(cy >> 4 << 4, chunk.world.worldProvider.g());
        sections[cy >> 4] = chunksection;
      }

      chunksection.setType(cx, cy & 15, cz, newBlock);
      ((HeightMap) chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING)).a(cx, cy, cz, newBlock);
      ((HeightMap) chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES)).a(cx, cy, cz, newBlock);
      ((HeightMap) chunk.heightMap.get(HeightMap.Type.OCEAN_FLOOR)).a(cx, cy, cz, newBlock);
      ((HeightMap) chunk.heightMap.get(HeightMap.Type.WORLD_SURFACE)).a(cx, cy, cz, newBlock);
      if (!chunk.world.isClientSide) {
        oldBlock.remove(chunk.world, blockposition, newBlock, false);
      } else if (block1 != block && block1 instanceof ITileEntity) {
        chunk.world.n(blockposition);
      }

      if (chunksection.getType(cx, cy & 15, cz).getBlock() != block) {
        return;
      } else {


        TileEntity tileentity;
        if (block1 instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity != null) {
            tileentity.invalidateBlockCache();
          }
        }


        if (block instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity == null) {
            tileentity = ((ITileEntity) block).a(chunk.world);
            chunk.world.setTileEntity(blockposition, tileentity);
          } else {
            tileentity.invalidateBlockCache();
          }
        }

      }
    }
  }



  public void setBlock(World world, org.bukkit.Material type, BlockPosition pos) {
    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|setBlock");
    setTypeAndData(world,((CraftBlockData)type.createBlockData()).getState() ,pos);
  }

  public boolean setTypeAndData(World world, IBlockData blockData, BlockPosition pos) {
   Block oldNmsBlock =  world.getType(pos).getBlock();
    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|setTypeAndData1");
    boolean applyPhysics = false;
    if (!blockData.isAir() && blockData.getBlock() instanceof BlockTileEntity && blockData.getBlock() != oldNmsBlock) {
      setTypeAndData(world,pos, Blocks.AIR.getBlockData(), 0);
    }

    if (applyPhysics) {
      return  setTypeAndData(world,pos, blockData, 3);
      //return this.world.setTypeAndData(this.position, blockData, 3);
    } else {
      IBlockData old = world.getType(pos);
     // boolean success = world.setTypeAndData(pos, blockData, 1042);
      boolean success =  setTypeAndData(world,pos, blockData, 1042);
      if (success) {
       // this.world.getMinecraftWorld().notify(this.position, old, blockData, 3);
      }

      return success;
    }
  }


  public boolean setTypeAndData(World world ,BlockPosition blockposition, IBlockData iblockdata, int i) {
    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|setTypeAndData2");
    if (world.captureTreeGeneration) {
      Bukkit.getLogger().info("-Capturing trees");
      CraftBlockState blockstate = null;
      Iterator it = world.capturedBlockStates.iterator();

      while(it.hasNext()) {
        CraftBlockState previous = (CraftBlockState)it.next();
        if (previous.getX() == blockposition.getX() && previous.getY() == blockposition.getY() && previous.getZ() == blockposition.getZ()) {
          blockstate = previous;
          it.remove();
          break;
        }
      }

      if (blockstate == null) {
        blockstate = CraftBlockState.getBlockState(world, blockposition, i);
      }

      blockstate.setData(iblockdata);
      world.capturedBlockStates.add(blockstate);
      return true;
    } else if (world.k(blockposition)) {
      Bukkit.getLogger().info("-Not valid position y: " +blockposition.getY());
      return false;
    } else if (!world.isClientSide && world.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
      Bukkit.getLogger().info("-Debug stuff");
      return false;
    } else {
      net.minecraft.server.v1_13_R2.Chunk chunk = world.getChunkAtWorldCoords(blockposition);
      iblockdata.getBlock();
      CraftBlockState blockstate = null;
      if (world.captureBlockStates) {
        blockstate = CraftBlockState.getBlockState(world, blockposition, i);
        world.capturedBlockStates.add(blockstate);
      }

     // IBlockData iblockdata1 = chunk.a(blockposition, iblockdata, (i & 64) != 0, (i & 1024) == 0);
      IBlockData iblockdata1 = chunkA(chunk,blockposition, iblockdata, (i & 64) != 0, (i & 1024) == 0);
      if (iblockdata1 == null) {
        if (world.captureBlockStates) {
          world.capturedBlockStates.remove(blockstate);
        }
        //Bukkit.getLogger().info("-old block is null");
        return false;
      } else {
        IBlockData iblockdata2 = world.getType(blockposition);
        if (iblockdata2.b(world, blockposition) != iblockdata1.b(world, blockposition) || iblockdata2.e() != iblockdata1.e()) {
          world.methodProfiler.a("checkLight");
          world.r(blockposition);
          world.methodProfiler.e();
        }

        if (!world.captureBlockStates) {
         // world.notifyAndUpdatePhysics(blockposition, chunk, iblockdata1, iblockdata, iblockdata2, i);
        }

        return true;
      }
    }
  }



  @Nullable
  public IBlockData chunkA(net.minecraft.server.v1_13_R2.Chunk chunk,BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean doPlace) {

    Bukkit.getLogger().info(ChunkPlacer_v1_13_R2.class.getSimpleName()+"|chunkA");
    int i = blockposition.getX() & 15;
    int j = blockposition.getY();
    int k = blockposition.getZ() & 15;

    ChunkSection[] sections = null;

    try{

      sections = (ChunkSection[] )F_SECTION.get(chunk);
    }catch(IllegalAccessException | IllegalArgumentException ex){
     throw new RuntimeException("Could not get sections for chunk",ex);

    }
    int l = ((HeightMap)chunk.heightMap.get(HeightMap.Type.LIGHT_BLOCKING)).a(i, k);
    IBlockData iblockdata1 = chunk.getType(blockposition);
    if (iblockdata1 == iblockdata) {
      return null;
    } else {
      Block block = iblockdata.getBlock();
      Block block1 = iblockdata1.getBlock();
      ChunkSection chunksection = sections[j >> 4];
      boolean flag1 = false;
      if (chunksection == null) {
        if (iblockdata.isAir()) {
          return null;
        }

        chunksection = new ChunkSection(j >> 4 << 4, chunk.world.worldProvider.g());
        sections[j >> 4] = chunksection;
        flag1 = j >= l;
      }

      chunksection.setType(i, j & 15, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.OCEAN_FLOOR)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.WORLD_SURFACE)).a(i, j, k, iblockdata);
      if (!chunk.world.isClientSide) {
        iblockdata1.remove(chunk.world, blockposition, iblockdata, flag);
      } else if (block1 != block && block1 instanceof ITileEntity) {
        chunk.world.n(blockposition);
      }

      if (chunksection.getType(i, j & 15, k).getBlock() != block) {
        return null;
      } else {
        if (flag1) {
          chunk.initLighting();
        } else {
          int i1 = iblockdata.b(chunk.world, blockposition);
          int j1 = iblockdata1.b(chunk.world, blockposition);
          //chunk.a(i, j, k, iblockdata);
          if (i1 != j1 && (i1 < j1 || chunk.getBrightness(EnumSkyBlock.SKY, blockposition) > 0 || chunk.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 0)) {
            //chunk.c(i, k);
          }
        }

        TileEntity tileentity;
        if (block1 instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity != null) {
            tileentity.invalidateBlockCache();
          }
        }

        if (!chunk.world.isClientSide && doPlace && (!chunk.world.captureBlockStates || block instanceof BlockTileEntity)) {
          iblockdata.onPlace(chunk.world, blockposition, iblockdata1);
        }

        if (block instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity == null) {
            tileentity = ((ITileEntity)block).a(chunk.world);
            chunk.world.setTileEntity(blockposition, tileentity);
          } else {
            tileentity.invalidateBlockCache();
          }
        }


        try {
          //chunk.x = true;
          F_UPDATE.setBoolean(chunk,true);
        } catch (IllegalAccessException e) {
          throw new RuntimeException("Could not get update for chunk",e);
        }

        return iblockdata1;
      }
    }
  }
/*  @Nullable
  public IBlockData a(BlockPosition blockposition, IBlockData iblockdata) {

    final boolean flag = false;
    final boolean doPlace = false;


    int i = blockposition.getX() & 15;
    int j = blockposition.getY();
    int k = blockposition.getZ() & 15;
    net.minecraft.server.v1_13_R2.Chunk chunk = null;
    int l = ((HeightMap)chunk.heightMap.get(HeightMap.Type.LIGHT_BLOCKING)).a(i, k);
    IBlockData iblockdata1 = chunk.getType(blockposition);
    if (iblockdata1 == iblockdata) {
      return null;
    } else {
      Block block = iblockdata.getBlock();
      Block block1 = iblockdata1.getBlock();
      ChunkSection chunksection = chunk.sections[j >> 4];
      boolean flag1 = false;
      if (chunksection == null) {
        if (iblockdata.isAir()) {
          return null;
        }

        chunksection = new ChunkSection(j >> 4 << 4, chunk.world.worldProvider.g());
        chunk.sections[j >> 4] = chunksection;
        flag1 = j >= l;
      }

      chunksection.setType(i, j & 15, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.OCEAN_FLOOR)).a(i, j, k, iblockdata);
      ((HeightMap)chunk.heightMap.get(HeightMap.Type.WORLD_SURFACE)).a(i, j, k, iblockdata);
      if (!chunk.world.isClientSide) {
        iblockdata1.remove(chunk.world, blockposition, iblockdata, flag);
      } else if (block1 != block && block1 instanceof ITileEntity) {
        chunk.world.n(blockposition);
      }

      if (chunksection.getType(i, j & 15, k).getBlock() != block) {
        return null;
      } else {
        //if (flag1) {
       //   this.initLighting();
      //  } else {
      //    int i1 = iblockdata.b(this.world, blockposition);
      //    int j1 = iblockdata1.b(this.world, blockposition);
      //    this.a(i, j, k, iblockdata);
       //   if (i1 != j1 && (i1 < j1 || this.getBrightness(EnumSkyBlock.SKY, blockposition) > 0 || this.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 0)) {
       ////     this.c(i, k);
      //    }
      //  }

        TileEntity tileentity;
        if (block1 instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity != null) {
            tileentity.invalidateBlockCache();
          }
        }

       // if (!chunk.world.isClientSide && doPlace && (!chunk.world.captureBlockStates || block instanceof BlockTileEntity)) {
      //    iblockdata.onPlace(chunk.world, blockposition, iblockdata1);
      //  }

        if (block instanceof ITileEntity) {
          tileentity = chunk.a(blockposition, net.minecraft.server.v1_13_R2.Chunk.EnumTileEntityState.CHECK);
          if (tileentity == null) {
            tileentity = ((ITileEntity)block).a(chunk.world);
            chunk.world.setTileEntity(blockposition, tileentity);
          } else {
            tileentity.invalidateBlockCache();
          }
        }

        this.x = true;
        return iblockdata1;
      }
    }
  }*/
}
