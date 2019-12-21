package me.varmetek.plugin.prisonminerefill.editor.impl;

import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditSession;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;

public class StandardEditSession implements PrisonMineEditSession
{

  private PrisonMine mine;

  public StandardEditSession(PrisonMine mine){
    this.mine = mine;
  }

  @Override
  public void setCurrentMine (PrisonMine mine) {
    this.mine = mine;
  }

  @Override
  public PrisonMine getCurrentMine () {
    return mine;
  }

}
