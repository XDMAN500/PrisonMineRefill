package me.varmetek.plugin.prisonminerefill.commands;

import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdCommand;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdSender;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditSession;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditor;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.util.TextUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandRemoveMine extends CmdCommand
{


  public CommandRemoveMine () {
    super("removemine");
  }

  @Override
  public void execute (CmdSender sender, String label, String[] args) {
    if(!sender.isPlayer()){
      TextUtil.sendMessage(sender.asSender(), "&c Command is only for players!");
      return;

    }

    if(args.length  == 0){
      TextUtil.sendMessage(sender.asSender(), "&cUsage: /removemine <name>");

      return;
    }

    if(args.length != 1){
      TextUtil.sendMessage(sender.asSender(), "&cUsage: /removemine <name>");
      return;
    }

    String name = args[0];
    Player player = sender.asPlayer();
    performRemove(player,name);

    }




    private void performRemove(Player player, String name){

      MineRefillService service  = PrisonMineRefillPlugin.getService();
      PrisonMineEditor editor = service.getPrisonMineEditor();
      PrisonMineEditSession sessions = editor.getEditSession(player.getUniqueId());
      PrisonMineManager mineManager = service.getPrisonMineManager();

      if(!mineManager.hasMine( name)){
        player.sendMessage("&c Mine with name "+ name + " does not exists");

        return;
      }


      mineManager.removeMine(name);
      player.sendMessage("&a Mine " + name + " removed");

    }





  @Override
  public List<String> tabComplete (CmdSender sender, String label, String[] args) {
    return null;
  }
}
