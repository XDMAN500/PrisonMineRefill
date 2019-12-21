package me.varmetek.plugin.prisonminerefill.editor;

import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;

public interface PrisonMineEditSession
{

  void setCurrentMine(PrisonMine mine);

  PrisonMine getCurrentMine();

}
