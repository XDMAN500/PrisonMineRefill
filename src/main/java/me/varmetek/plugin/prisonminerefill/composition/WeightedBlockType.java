package me.varmetek.plugin.prisonminerefill.composition;

import org.bukkit.Material;


/***
 *
 * Holds the association of one blocktype to it's weighted percentage
 * */
public class WeightedBlockType
{
  protected final Material blockType;
  protected final int weight;

  /***
   *
   * Constructs the object
   *
   *
   * @param blockType the material represented by this object
   * @param weight the weighted percentage
   * */
  public WeightedBlockType(Material blockType, int weight){
    this.blockType = blockType;
    this.weight = weight;
  }



  /**
   * Returns the block type
   *
   *
   * @return the blocktype
   * */
  public Material getBlockType(){
    return blockType;
  }


  /**
   * Returns the weighted percentage
   *
   *
   * @return the weighted percentage
   * */
  public int getWeight(){
    return weight;
  }


}
