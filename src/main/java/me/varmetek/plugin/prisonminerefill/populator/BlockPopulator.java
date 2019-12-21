package me.varmetek.plugin.prisonminerefill.populator;

import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;

public interface BlockPopulator
{

  /**
   *Populates the region with blocks
   *
   */

  void populate(BlockCompostion comp, PrisonMineRegion region);


}
