package me.varmetek.plugin.prisonminerefill;

import me.varmetek.plugin.prisonminerefill.blockplace.ChunkHandler;
import me.varmetek.plugin.prisonminerefill.blockplace.v1_13_R2.ChunkPlacer_v1_13_R2;
import me.varmetek.plugin.prisonminerefill.commands.*;
import me.varmetek.plugin.prisonminerefill.commands.technical.CommandManager;
import me.varmetek.plugin.prisonminerefill.hooks.HookManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
* The entrance point of the PrisonMineRefill plugin
* */
public final class PrisonMineRefillPlugin  extends JavaPlugin
{

  private static PrisonMineRefillPlugin instance;
  private static  MineRefillService SERVICE;

  public PrisonMineRefillPlugin(){
    instance = this;
  }



  public static PrisonMineRefillPlugin getInstance(){
    return instance;
  }


  public static MineRefillService getService(){

    return SERVICE;
  }



  @Override
  public void onEnable(){
    HookManager.getInstance();
    ChunkHandler.setPlacer(new ChunkPlacer_v1_13_R2());

    SERVICE = new MineRefillService(this);

    registerCommands();
  }


  public void registerCommands(){
    getLogger().info("Registering commands");
    CommandManager commandManager = SERVICE.getCommandManager();
    commandManager.setCommand(new CommandMine());
    commandManager.setCommand(new CommandEditMine());
    commandManager.setCommand(new CommandCreateMine());
    commandManager.setCommand(new CommandRemoveMine());
    commandManager.setCommand(new CommandSelectMine());
    commandManager.setCommand(new CommandUnselectMine());


    commandManager.reportUnusedCommands();

  }


}
