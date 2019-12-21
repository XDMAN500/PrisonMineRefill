package me.varmetek.plugin.prisonminerefill.composition;

import com.google.common.base.Preconditions;
import org.bukkit.Material;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;


/**
 * Holds the weighted chance for every block
 *
 * */
public class BlockCompositionBuilder
{
  protected Map<Material,Integer> materials;


  public BlockCompositionBuilder (){
    materials = new EnumMap<>(Material.class);
  }

  public BlockCompositionBuilder(BlockCompostion comp){

    materials = new EnumMap<>(Material.class);
    for(WeightedBlockType type : comp){
      setBlockWeight(type.getBlockType(),type.getWeight());
    }
  }



  /**
   * Registers a block to this composition builder
   * with a given weight.
   *
   * The material must not be null and must be a block
   *
   * If the material is already registered by this composition builder
   * then the weight for that material will be overridden with the new weight.
   *
   * If the material is not registered by this composition builder then it will be
   * registered with the given weight
   *
   * the weight parameter must be larger than 0
   *
   * @param  mat the material to register
   * @param  weight the weight of the material
   * @throws NullPointerException when material is null
   * @throws IllegalArgumentException when material is not a block
   * @throws IllegalArgumentException when weight is not larger than 0
   *
   *
   * */
  public void setBlockWeight(Material mat, int weight){
    Preconditions.checkNotNull(mat, "Material cannot be null");
    Preconditions.checkArgument(mat.isBlock() , "Material "+ mat.name() + " is not a block");
    Preconditions.checkArgument(weight > 0 , "Material weight must be larger than 0");
    materials.put(mat,weight);
  }



  /***
   * Returns the weight of the material if it is registered
   *
   * @param material the material to retuirn the weight of
   * @return  the weight of the material
   * @throws  NullPointerException if material is null
   * @throws  IllegalArgumentException if material is not tracked
   *
   * */

  public int getWeight(Material material) {
    Preconditions.checkNotNull(material, "Material cannot be null");
    Preconditions.checkArgument(materials.containsKey(material),"Material "+ material+ " is not tracked by this composition" );
    return materials.get(material);
  }


  /***
   * unregisters the provided material
   *
   * @param material the material to unregister
   * @throws  NullPointerException if material is null
   *
   * */
  public void removeBlock(Material material){
    Preconditions.checkNotNull(material, "Material cannot be null");
    materials.remove(material);
  }

  public boolean hasBlock(Material material){
    Preconditions.checkNotNull(material, "Material cannot be null");
    return materials.containsKey(material);
  }

  public Set<Material> getBlocks(){
    return Collections.unmodifiableSet(materials.keySet());
  }




}
