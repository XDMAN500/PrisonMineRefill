package me.varmetek.plugin.prisonminerefill.composition;

import junit.framework.TestCase;
import org.bukkit.Material;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockCompositionTest extends TestCase
{


  @Test
  public void testCompositionOrder(){
    BlockCompositionBuilder builder = new BlockCompositionBuilder();
    builder.setBlockWeight(Material.STONE,100);


    builder.setBlockWeight(Material.EMERALD_BLOCK,11000);
    builder.setBlockWeight(Material.LAPIS_BLOCK, 10);

    BlockCompostion compostion = new BlockCompostion(builder);

    List<WeightedBlockType> blocks = compostion.getOrderedBlocks();
    assertEquals(Material.LAPIS_BLOCK, blocks.get(0).getBlockType());


  }

  @Test
  public void testCompositionNotBlock(){
    BlockCompositionBuilder builder = new BlockCompositionBuilder();
    Throwable error = null;
    try {
      builder.setBlockWeight(Material.EMERALD, 100);
    }catch (Throwable throwable){
      error = throwable;
    }

    assertTrue(error instanceof IllegalArgumentException);
  }

  @Test
  public void testCompositionEmptyRandom(){
   BlockCompostion blockCompostion = new BlockCompostion(new HashMap<>());

    Throwable error = null;
    try {
      blockCompostion.getRandomBlockType();
    }catch (Throwable throwable){
      error = throwable;
    }

    assertTrue(error instanceof RuntimeException);
  }

  @Test
  public void testCompositionZeroWeight(){
    Map<Material,Integer> weights = new HashMap<>();
    weights.put(Material.LAPIS_BLOCK,0);
    BlockCompostion blockCompostion = new BlockCompostion(weights);

    Throwable error = null;
    try {
      blockCompostion.getRandomBlockType();
    }catch (Throwable throwable){
      error = throwable;
    }

    assertTrue(error instanceof RuntimeException);
  }
}
