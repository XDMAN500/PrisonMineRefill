package me.varmetek.plugin.prisonminerefill.hooks;

import org.bukkit.Bukkit;

public class PluginHookFAWE implements PluginHook
{

  @Override
  public boolean hasHook (){
    return Bukkit.getPluginManager().isPluginEnabled("FAWE");
  }
}
