package me.varmetek.plugin.prisonminerefill.prisonmine;


import me.varmetek.plugin.prisonminerefill.util.StringKey;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

/**
 * Keeps a record of all prison mines
* */
public class PrisonMineManager
{

  private Map<StringKey,PrisonMine> mineMap;

  public PrisonMineManager(){
    mineMap = new HashMap<>();
  }


  public void addMine(PrisonMine mine){

    mineMap.put( mine.getId(), mine);
  }


  public void removeMine(PrisonMine mine){

    removeMine(mine.getId());
  }


  public void removeMine(StringKey key){

    mineMap.remove(key);
  }

  public void removeMine(String name){

    removeMine(new StringKey(name));
  }


  public PrisonMine getMine(StringKey name){
    return mineMap.get(name);
  }

  /**
   * Gets the mine with the given name
   *
   * @param name  the name to look for
   * @return the {@link PrisonMine} with the given name
   *
   *
   * **/
  public boolean hasMine(String name){
    return  hasMine(new StringKey(name));
  }

  public boolean hasMine(StringKey name){
    return mineMap.containsKey(name);
  }

  /**
   * Gets the mine with the given name
   *
   * @param name  the name to look for
   * @return the {@link PrisonMine} with the given name
   *
   *
   * **/
  public PrisonMine getMine(String name){
    return  getMine(new StringKey(name));
  }



  /**
   * Gets the mine with a region that contains the given point
   *
   * @param loc  the location to search
   * @return the {@link PrisonMine}
   *
   *
   * **/
  public Optional<PrisonMine> getMineByPoint(Location loc){
    World world= loc.getWorld();
    return  mineMap.values().stream()
      .filter( mine -> mine.region.world.equals(world))
      .filter(mine -> mine.region.maxVector.getX() > loc.getX() && mine.region.minVector.getX() < loc.getX())
      .filter(mine -> mine.region.maxVector.getY() > loc.getY() && mine.region.minVector.getX() < loc.getY())
      .filter(mine -> mine.region.maxVector.getZ() > loc.getZ() && mine.region.minVector.getZ() < loc.getZ())
      .findAny();

  }

  /**
   * Gets all mines mapped to their names
   *
   * h
   * @return the Map of mines and their names
   *
   *
   * **/
  public Map<StringKey,PrisonMine> getMineMap(){
    return Collections.unmodifiableMap(mineMap);
  }

}
