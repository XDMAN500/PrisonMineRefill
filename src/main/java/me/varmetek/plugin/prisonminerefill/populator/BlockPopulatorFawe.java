package me.varmetek.plugin.prisonminerefill.populator;


import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.ImmutableDatalessBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.history.changeset.BlockOptimizedHistory;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.hooks.HookManager;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;
import org.bukkit.Material;

/***
 * Attempts to fill the entire region all at once
 * */
public class BlockPopulatorFawe implements BlockPopulator
{


  protected PrisonMine mine;

  public BlockPopulatorFawe (PrisonMine mine){
    this.mine = mine;
  }


  @Override
  public void populate (BlockCompostion composition, PrisonMineRegion region){
    if(!HookManager.getInstance().getHookFAWE().hasHook()) return;


    EditSession session = new EditSessionBuilder (BukkitUtil.getLocalWorld(region.getWorld())).fastmode(true).build();
    Region blocks = new CuboidRegion(BukkitUtil.toVector(region.getMaxVector().toVector()),BukkitUtil.toVector(region.getMinVector().toVector()));
    BlockOptimizedHistory history = new BlockOptimizedHistory();


    Pattern pattern = new Pattern()
    {
      @Override
      public BaseBlock apply (Vector position){
        Material mat =composition.getRandomBlockType();
        return new ImmutableDatalessBlock(mat.getId());
      }
    };


    session.setBlocks(blocks,pattern);

    /*for(int y = (int)region.getMinVector().getY();  y< (int)region.getMaxVector().getY(); y++){
      for(int x = (int)region.getMinVector().getX();  x< (int)region.getMaxVector().getZ(); x++){
        for(int z = (int)region.getMinVector().getZ();  z< (int)region.getMaxVector().getZ(); z++){
          try {
            BlockVector vector = new BlockVector(x,y,z);
            Material mat =compostion.getRandomBlockType();
            history.add(new BlockChange(vector, null, new BaseBlock(mat.getId(),0)));

           /// region.getWorld().getBlockAt(x, y, z).setType(compostion.getRandomBlockType());
          }catch (RuntimeException exception){
            continue;
          }
        }
      }


    }*/

  }

}
