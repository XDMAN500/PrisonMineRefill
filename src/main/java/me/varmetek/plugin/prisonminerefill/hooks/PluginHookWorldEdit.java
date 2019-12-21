package me.varmetek.plugin.prisonminerefill.hooks;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;

public class PluginHookWorldEdit implements PluginHook
{

  @Override
  public boolean hasHook (){
    return Bukkit.getPluginManager().isPluginEnabled("WorldEdit");
  }


  public WorldEditPlugin getWorldEditPlugin(){
    return (WorldEditPlugin )Bukkit.getPluginManager().getPlugin("WorldEdit");
  }
}
