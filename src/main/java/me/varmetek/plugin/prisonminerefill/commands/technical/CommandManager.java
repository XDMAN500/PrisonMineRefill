package me.varmetek.plugin.prisonminerefill.commands.technical;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter
{
  protected Map<String,CmdCommand> commandMap;
  protected JavaPlugin plugin;


  public CommandManager(JavaPlugin plugin){
    this.plugin = plugin;
    this.commandMap = new HashMap<>();

    PluginDescriptionFile desc = plugin.getDescription();
    for(String key: desc.getCommands().keySet()){
      PluginCommand command = plugin.getCommand(key);
      command.setExecutor(this);
      command.setTabCompleter(this);

    }
  }


  public void setCommand(CmdCommand command){
    commandMap.put(command.getName(),command);
    plugin.getLogger().info("  Command Registered: "+ command.getName());
  }


  public void reportUnusedCommands(){
    plugin.getLogger().info("Reporting unused commands");
    PluginDescriptionFile desc = plugin.getDescription();
    for(String key: desc.getCommands().keySet()){
      if(!hasCommand(key)){
          plugin.getLogger().info("Could not find command: " + key);

      }

    }

  }

  public boolean hasCommand(String command){
    return commandMap.containsKey(command);
  }


  public boolean hasCommand(Command command){
    return hasCommand(command.getName());

  }

  public CmdCommand getCommand(String command){

    return commandMap.get(command);
  }

  public CmdCommand getCommand(Command command){

    return getCommand(command.getName());
  }



  @Override
  public boolean onCommand (CommandSender commandSender, Command command, String s, String[] strings) {

    CmdCommand cmd = getCommand(command);
    if(cmd == null){
      StringBuilder message = new StringBuilder(100);
      message.append(ChatColor.RED.toString())
        .append("Command ").append(s)
        .append( " (").append(command.getName()) .append(") ")
        .append("has not been initialized yet!");
      commandSender.sendMessage(message.toString() );
    }else {

      cmd.execute(new CmdSender(commandSender), s, strings);
    }
    return false;
  }

  @Override
  public List<String> onTabComplete (CommandSender commandSender, Command command, String s, String[] strings) {
    CmdCommand cmd = getCommand(command);
    if(cmd == null){
      StringBuilder message = new StringBuilder(100);
      message.append(ChatColor.RED.toString())
        .append("Command ").append(s)
        .append( " (").append(command.getName()) .append(") ")
        .append("has not been initialized yet!");
      commandSender.sendMessage(message.toString() );
      return Collections.emptyList();
    }else {

      return cmd.tabComplete(new CmdSender(commandSender), s, strings);
    }
  }
}
