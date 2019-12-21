package me.varmetek.plugin.prisonminerefill.prisonmine;

import me.varmetek.plugin.prisonminerefill.util.ImmutableVector;
import org.bukkit.World;
import org.bukkit.util.Vector;


/***
 *
 *  Determines the region where the mine can hold it's blocks
 *
 *
 * */
public class PrisonMineRegion
{
  protected World world;
  protected ImmutableVector maxVector;
  protected ImmutableVector minVector;


  /**
   * Construct the object.
   *
   * @param world the world that this region lies within
   * @param  position1 the first position to make the region
   * @param  position1 the second position to make the region
   *
   */
  public PrisonMineRegion ( World world, Vector position1, Vector position2){
    this.world = world;
    this.maxVector = new ImmutableVector(Vector.getMaximum(position1,position2));
    this.minVector = new ImmutableVector(Vector.getMinimum(position1,position2));
  }

  /**
   * Gets the world this region is in
   *
   * @return the world this region is in
   *
   */
  public World getWorld(){
    return world;
  }



  /**
   * Gets the maximum coordinates of this region
   *
   * @return the maximum coordinates of this region
   *
   */
  public ImmutableVector getMaxVector(){
    return maxVector;
  }



  /**
   * Gets the minimum coordinates of this region
   *
   * @return the minimum coordinates of this region
   *
   */
  public ImmutableVector getMinVector(){
    return minVector;
  }


}
