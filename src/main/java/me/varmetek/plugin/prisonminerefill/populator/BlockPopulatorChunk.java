package me.varmetek.plugin.prisonminerefill.populator;


import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.blockplace.ChunkBlock;
import me.varmetek.plugin.prisonminerefill.blockplace.ChunkHandler;
import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

/***
 * Attempts to fill the entire region all at once
 * */
public class BlockPopulatorChunk implements BlockPopulator
{



  protected PrisonMine mine;

  public BlockPopulatorChunk (PrisonMine mine){
    this.mine = mine;
  }


  @Override
  public void populate (BlockCompostion composition, PrisonMineRegion region){
    World world = region.getWorld();
    Bukkit.getLogger().info("PopulatorChunkFlat populating " + mine.getId());
    Map<Chunk,Set<ChunkBlock>> blocks = new HashMap<>();

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
          Chunk chunk = world.getChunkAt(x,z);
          Set<ChunkBlock> blockSet = new HashSet<>();
          if(!chunk.isLoaded()){
            chunk.load();
            Bukkit.getLogger().info("Loading chunk " + chunk.toString());
          }
          blocks.put(chunk,blockSet);
      }
    }





    long total = ( ((int)region.getMaxVector().getX() -(int)region.getMinVector().getX()+1 )
                   * ((int)region.getMaxVector().getZ() -(int)region.getMinVector().getZ()+1 )
                    *((int)region.getMaxVector().getY() -(int)region.getMinVector().getY() +1 )

    );


    long count = 0;
    for(int y = (int)region.getMinVector().getY();  y<= (int)region.getMaxVector().getY(); y++){
      for(int x = (int)region.getMinVector().getX();  x<= (int)region.getMaxVector().getX(); x++){
        for(int z = (int)region.getMinVector().getZ();  z<= (int)region.getMaxVector().getZ(); z++){
          try {
            Material mat = composition.getRandomBlockType();
            if(mat == null){

              Bukkit.getLogger().info("Populated material is nu;;");
            }

            int cx = x >> 4;
            int cz = z >> 4;

            Chunk chunk = world.getChunkAt(cx,cz);

            ChunkBlock block = new ChunkBlock(mat,x ,y,z );

           Set<ChunkBlock> blockSet=  blocks.get(chunk);
           blockSet.add(block);


            //region.getWorld().getBlockAt(x, y, z).setType(mat);
            count +=1;
          }catch (RuntimeException exception){
            Bukkit.getLogger().log(Level.WARNING , "Encounter Exception ", exception);
            continue;
          }
        }
      }
    }


    for(Chunk ch: blocks.keySet()){
      ChunkHandler.getPlacer().placeAll(ch,blocks.get(ch));
    }


    Bukkit.getScheduler().runTaskAsynchronously(PrisonMineRefillPlugin.getInstance(), ()->{


      for(Player player :  world.getPlayers()){

        for(Chunk ch: blocks.keySet()) {
          ChunkHandler.getPlacer().sendBlockChanges(player,ch,blocks.get(ch));
        }


      }
    });
    Bukkit.broadcastMessage( " Populated "+ count  + " / " + total);
  }

}
