package me.varmetek.plugin.prisonminerefill.refills;

import org.bukkit.Bukkit;

public class RefillTriggerDebug extends AbstractRefillTrigger
{
  public RefillTriggerDebug (RefillHandler handle) {
    super(handle);
  }

  @Override
  public void onNotify () {
    Bukkit.broadcastMessage( "DTRIG Mine " + getHandler().getPrisonMine().getId().toString() + " has reset");
  }


}
