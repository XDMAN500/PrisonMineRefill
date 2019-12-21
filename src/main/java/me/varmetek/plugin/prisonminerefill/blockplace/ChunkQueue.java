package me.varmetek.plugin.prisonminerefill.blockplace;

import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkQueue {

  private Map<ChunkRef, ChunkRefill> delayedRefill;



  List<ChunkRefill> divideRegion(BlockCompostion compostion, PrisonMineRegion region){
    int minX =  (int)region.getMinVector().getX();
    int minZ = (int)region.getMinVector().getZ();

    int maxX =  (int)region.getMaxVector().getX();
    int maxZ = (int)region.getMaxVector().getZ();


    int minCX =  minX >> 4;
    int minCZ =  minZ >> 4;
    int maxCX =  maxX >> 4;
    int maxCZ =  maxZ >> 4;


    for(int x = minCX ;x<= maxCX; x++ ){

      for(int z = minCZ ;z<= maxCZ; z++ ){
        ChunkRef ref = new ChunkRef(region.getWorld(),x,z);

        int minCHx, maxCHx, minCHy, maxCHy, minCHz, maxCHz;

        if(x == minCX){
          mineCh
        }
      }


      }
    }



  private class ChunkRefill{

    private ChunkRef chunk;
    private int xMin, xMax, zMin, zMax, yMin, yMax;
    private BlockCompostion composition;



  }
}
