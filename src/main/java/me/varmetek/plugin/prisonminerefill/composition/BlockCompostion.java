package me.varmetek.plugin.prisonminerefill.composition;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;

import java.util.*;


/**
 * Holds materials and their weighted percentage
 *
 * **/
public class BlockCompostion implements Iterable<WeightedBlockType>
{
  protected final int totalWeight;
  protected List<WeightedBlockType> orderedBlocks;
  protected Map<Material,WeightedBlockType> source;

  /**
   * Construct with a builder
   *
   * @param blockCompositionBuilder the builder to construct this composition with
   *
   * **/
  public BlockCompostion (BlockCompositionBuilder blockCompositionBuilder){
    this(blockCompositionBuilder.materials);
  }


  /**
   * Construct with raw materials mapped to their weights
   *
   * @param compostion a map of materials and their weighted percentages
   *
   * **/
  public BlockCompostion (Map<Material, Integer> compostion) {
    int tWeight = 0;

    List<WeightedBlockType> toBeSorted = new ArrayList<>(compostion.size());
    source = new HashMap<>();

    for (Map.Entry<Material,Integer> entry : compostion.entrySet()) {
      if (entry.getKey() == null || entry.getValue() == null) continue;
      if(!entry.getKey().isBlock()) continue;

      tWeight += entry.getValue();
      WeightedBlockType weightedBlockType = new WeightedBlockType(entry.getKey(), entry.getValue());
      toBeSorted.add(weightedBlockType);
      source.put( entry.getKey(), weightedBlockType);

    }


    totalWeight = tWeight;
    toBeSorted.sort( (block1,block2) -> block1.getWeight() - block2.getWeight());
    this.orderedBlocks = ImmutableList.copyOf(toBeSorted);

  }

  public int getTotalWeight() {
    return totalWeight;
  }


  public List<WeightedBlockType> getOrderedBlocks() {
    return orderedBlocks;
  }

  /**
   *  Returns a random material from this composition based
   *  on the materials' weighted percentages
   *
   * @return a random material
   * @throws RuntimeException if a a random material could not be generated
   * **/
  public Material getRandomBlockType() {
    int total = totalWeight;
    Random random = new Random();
    for (int i = 0; i< orderedBlocks.size(); ++i) {
      if (total < 1) continue;
      int roll = random.nextInt(total);
      WeightedBlockType type = orderedBlocks.get(i);
      if(roll < type.getWeight() ){
        return type.getBlockType();
      }

      total -= type.getWeight();

    }


    throw new  RuntimeException(String.format("Could not generate a random block type (Total: %d, Size: &d )",
    total, orderedBlocks.size()
    ));

  }



  /**
   *  Gets the {@link WeightedBlockType } associated with a material
   *
   * @param mat the material to search with
   * @return the {@link WeightedBlockType } associated with a material
   * @throws NullPointerException if mat is null
   * @throws  IllegalArgumentException if the material is not registered in this composition
   * **/
  public WeightedBlockType getMaterial(Material mat){
    Preconditions.checkNotNull(mat, "Material cannot be null");
    Preconditions.checkArgument(source.containsKey(mat), "Material is not registered in this composition");

    return source.get(mat);
  }

  @Override
  public Iterator<WeightedBlockType> iterator () {
    return orderedBlocks.iterator();
  }


  public boolean isEmpty(){

    return orderedBlocks.isEmpty();
  }
}



