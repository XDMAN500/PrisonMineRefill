package me.varmetek.plugin.prisonminerefill.commands;

import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdCommand;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdSender;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditSession;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditor;
import me.varmetek.plugin.prisonminerefill.populator.BlockPopulatorChunk;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.util.StringKey;
import me.varmetek.plugin.prisonminerefill.util.TextUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandCreateMine extends CmdCommand
{


  public CommandCreateMine () {
    super("createmine");
  }

  @Override
  public void execute (CmdSender sender, String label, String[] args) {
    if(!sender.isPlayer()){
      TextUtil.sendMessage(sender.asSender(), "&c Command is only for players!");
      return;

    }

    if(args.length  == 0){
      TextUtil.sendMessage(sender.asSender(), "&cUsage: /createmine <name>");

      return;
    }


    if(args.length != 1){
      TextUtil.sendMessage(sender.asSender(), "&cUsage: /createmine <name>");
      return;
    }

    String name = args[0];
    Player player = sender.asPlayer();
    performCreate(player,name);




  }



    private void performCreate(Player player, String name){

      MineRefillService service  = PrisonMineRefillPlugin.getService();
      PrisonMineEditor editor = service.getPrisonMineEditor();
      PrisonMineEditSession sessions = editor.getEditSession(player.getUniqueId());
      PrisonMineManager mineManager = service.getPrisonMineManager();

      if(mineManager.hasMine( name)){
        player.sendMessage("&c Mine with name "+ name + " already exists");

        return;
      }


      if(sessions != null){
        player.sendMessage("&eOverriding old session.");

      }

      sessions  = editor.addEditSession(player.getUniqueId());

      PrisonMine mine = new PrisonMine(new StringKey(name));
      mine.setDefaultPopulator(new BlockPopulatorChunk(mine));

      sessions.setCurrentMine(mine);
      player.sendMessage("&a Session created");
      player.sendMessage("&aPerform &7/editmine save&a to save changes");
    }



  @Override
  public List<String> tabComplete (CmdSender sender, String label, String[] args) {
    return null;
  }
}
