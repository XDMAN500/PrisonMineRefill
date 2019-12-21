package me.varmetek.plugin.prisonminerefill.prisonmine;

import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.refills.RefillHandler;
import me.varmetek.plugin.prisonminerefill.util.StringKey;
import org.bukkit.Location;



/**
 * The region that handles the placing of blocks
 *
 * **/
public class PrisonMine
{
  protected StringKey id;

  protected PrisonMineRegion region;
  protected Location safeLocation;
  protected RefillHandler refillHandler;
  protected BlockCompostion compostion;


  public PrisonMine(StringKey id){
    this.id= id;
    this.refillHandler = new RefillHandler(this);
  }


  public StringKey getId(){
    return id;
  }




  public BlockCompostion getCompostion (){
    return compostion;
  }

  public void setCompostion(BlockCompostion comp){
    this.compostion = comp;
  }

  public PrisonMineRegion getRegion (){
    return region;
  }

  public void  setRegion (PrisonMineRegion region){

    this.region = region;
  }


  public RefillHandler getRefillHandler (){
    return refillHandler;
  }


  public Location getSafeLocation (){
    return safeLocation;
  }

  public void setSafeLocation(Location loc){
    safeLocation = loc;
  }

  @Override
  public boolean equals(Object obj){

    if(this == obj)return true;
    if(obj instanceof PrisonMine){
      return  id.equals (((PrisonMine) obj).id);
    } else return false;
  }

  @Override
  public int hashCode(){

    return id.hashCode();
  }



}
