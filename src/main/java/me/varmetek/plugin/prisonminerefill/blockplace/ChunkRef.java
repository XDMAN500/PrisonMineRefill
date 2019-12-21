package me.varmetek.plugin.prisonminerefill.blockplace;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class ChunkRef {

  private World world;
  private int x;
  private int z;

  public ChunkRef (World world, int x, int z) {

    this.world = world;
    this.x = x;
    this.z = z;
  }

  public ChunkRef (Chunk chunk) {
    this(chunk.getWorld(), chunk.getX(), chunk.getZ());
  }

  public ChunkRef (Location loc) {
    this(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
  }


  public World getWorld () {
    return world;

  }


  public int getX () {
    return x;
  }


  public int getZ() {
    return z;
  }


  public Chunk getChunk() {
    return world.getChunkAt(x,z);
  }


  public boolean isLoaded() {
    return world.isChunkLoaded(x,z);
  }


  public void load(){
    world.loadChunk(x,z);
  }



  @Override
  public boolean equals(Object other) {
    if(this == other) {
      return true;
    }


    if(other instanceof ChunkRef) {
      ChunkRef chunk = (ChunkRef)other;

      if(this.world.equals(chunk.world) && this.x == chunk.x && this.z == chunk.z){
        return true;
      }
    }

    return false;
  }


  @Override
  public int hashCode() {
    int prime =17;
    int value = 3;
    value = value * world.hashCode();
    value = value * x;
    value = value * z;

    return value;

  }

}
