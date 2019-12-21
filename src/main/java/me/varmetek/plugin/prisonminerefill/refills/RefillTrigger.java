package me.varmetek.plugin.prisonminerefill.refills;

public interface RefillTrigger
{

  void refill();

  void onNotify();

  RefillHandler getHandler();
}
