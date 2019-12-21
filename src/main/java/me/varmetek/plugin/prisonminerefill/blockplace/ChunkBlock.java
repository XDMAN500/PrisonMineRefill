package me.varmetek.plugin.prisonminerefill.blockplace;

import org.bukkit.Material;

public class ChunkBlock
{

  protected static final Material[] MATS = Material.values();
  byte x, z;
  short y;
  short id;

  public ChunkBlock(int id, int x, int y , int z){

    this. id = (short)id;
    this.x = (byte)(x & 0xF);
    this.z = (byte)(z & 0xF);
    this.y = (short)(y & 0xFF);
  }


  public ChunkBlock(Material mat, int x, int y , int z){

    this. id = (short)mat.ordinal();
    this.x = (byte)(x & 0xF);
    this.z = (byte)(z & 0xF);
    this.y = (short)(y & 0xFF);
  }




  public short getId(){
    return id;
  }


  public Material getMaterial(){
    return MATS[id];
  }

  public byte getX(){
    return x;
  }


  public byte getZ(){

    return z;
  }


  public short getY(){
    return y;
  }


  public int getCompactCoords(){

    return compactCoords(x,y,z);
  }


  public static int compactCoords( int x, int y , int z){

    return  (y & 0xFF) | ((z & 0xF) << 8) |((x & 0xF) << 12);
   // byte chunkY = (byte)(y & 0xFF);
   // byte chunkX = (byte)(x & 0xF);
   // byte chunkZ = (byte)(z & 0xF);

    //return( (chunkY) | (chunkX << 12) | (chunkZ << 8) );

  }
}
