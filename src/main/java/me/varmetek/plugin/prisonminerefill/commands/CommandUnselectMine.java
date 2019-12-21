package me.varmetek.plugin.prisonminerefill.commands;

import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdCommand;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdSender;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditSession;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditor;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.util.TextUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandUnselectMine extends CmdCommand
{


  public CommandUnselectMine () {
    super("unselectmine","unselmine");
  }

  @Override
  public void execute (CmdSender sender, String label, String[] args) {
    if(!sender.isPlayer()){
      TextUtil.sendMessage(sender.asSender(), "&c Command is only for players!");
      return;

    }

    if(args.length  == 0){

      performUnselect(sender.asPlayer());



    }else{

      TextUtil.sendMessage(sender.asSender(), "&cUsage: /unselectmine");
    }




    }



  private void performUnselect(Player player){

    MineRefillService service  = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession sessions = editor.getEditSession(player.getUniqueId());
    PrisonMineManager mineManager = service.getPrisonMineManager();



    if(sessions == null){
      player.sendMessage("&cNo mine is currently selected.");
      return;
    }

    PrisonMine mine = sessions.getCurrentMine();


    player.sendMessage("&a Unselected mine "+ mine.getId().toString());

    }





  @Override
  public List<String> tabComplete (CmdSender sender, String label, String[] args) {
    return null;
  }
}
