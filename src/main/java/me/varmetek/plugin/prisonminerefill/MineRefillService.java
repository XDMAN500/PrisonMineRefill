package me.varmetek.plugin.prisonminerefill;

import me.varmetek.plugin.prisonminerefill.commands.technical.CommandManager;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditor;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.util.GlobalPrisonTimer;

public final class MineRefillService
{

   private final PrisonMineRefillPlugin plugin;


  private final GlobalPrisonTimer globalPrisonTimer;
  private final PrisonMineManager prisonMineManager;
  private final CommandManager commandManager;
  private final PrisonMineEditor prisonMineEditor;

   MineRefillService(PrisonMineRefillPlugin plugin){

     this.plugin = plugin;


     this.prisonMineManager = new PrisonMineManager();
     this.globalPrisonTimer = new GlobalPrisonTimer(20*60*10);
     this.commandManager = new CommandManager(plugin);
     this.prisonMineEditor = new PrisonMineEditor(this);
   }



  public CommandManager getCommandManager(){
    return commandManager;
  }

  public PrisonMineManager getPrisonMineManager(){
    return prisonMineManager;
  }


  public GlobalPrisonTimer getGlobalPrisonTimer(){
    return globalPrisonTimer;
  }


  public PrisonMineRefillPlugin getPlugin(){
     return plugin;
  }

  public PrisonMineEditor getPrisonMineEditor(){
     return prisonMineEditor;
  }
}
