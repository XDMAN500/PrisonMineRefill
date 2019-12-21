package me.varmetek.plugin.prisonminerefill.blockplace;

public final class ChunkHandler
{

  private static ChunkPlacer placer;

  public  static ChunkPlacer getPlacer(){
    return placer;
  }


  public static void setPlacer(ChunkPlacer pl){

    placer = pl;
  }



}
