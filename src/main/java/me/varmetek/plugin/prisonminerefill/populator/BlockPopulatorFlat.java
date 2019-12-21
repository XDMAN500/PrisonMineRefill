package me.varmetek.plugin.prisonminerefill.populator;


import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.logging.Level;

/***
 * Attempts to fill the entire region all at once
 * */
public class BlockPopulatorFlat implements BlockPopulator
{



  protected PrisonMine mine;

  public BlockPopulatorFlat (PrisonMine mine){
    this.mine = mine;
  }


  @Override
  public void populate (BlockCompostion composition, PrisonMineRegion region){

    Bukkit.getLogger().info("PopulatorFlat populating " + mine.getId());

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
            region.getWorld().getBlockAt(x, y, z).setType(mat,false);
            count +=1;
          }catch (RuntimeException exception){
            Bukkit.getLogger().log(Level.WARNING , "Encounter Exception ", exception);
            continue;
          }
        }
      }
    }

    Bukkit.getLogger().info( " Populated "+ count  + " / " + total);
  }


}
