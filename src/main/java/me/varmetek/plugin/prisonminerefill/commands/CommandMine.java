package me.varmetek.plugin.prisonminerefill.commands;

import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdCommand;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdSender;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.refills.RefillHandler;
import me.varmetek.plugin.prisonminerefill.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandMine extends CmdCommand
{


  public CommandMine () {
    super("mine");
  }

  @Override
  public void execute (CmdSender sender, String label, String[] args) {
    PrisonMineRefillPlugin plugin = PrisonMineRefillPlugin.getInstance();
    PrisonMineManager manager = PrisonMineRefillPlugin.getService().getPrisonMineManager();

    if(args.length == 0) {
      for (PrisonMine mine : manager.getMineMap().values()) {
        sender.asSender().sendMessage(ChatColor.BLUE + " " + mine.getId());

      }
    }else{

      if("refill".equalsIgnoreCase(args[0])){

        if(args.length == 2){
          Player player = sender.asPlayer();
          MineRefillService service  = PrisonMineRefillPlugin.getService();

          PrisonMineManager mineManager = service.getPrisonMineManager();
          String name = args[1];
          if(!mineManager.hasMine( name)){
            TextUtil.sendMessage(player,"&c Mine with name "+ name + " does not exists");

            return;
          }

          PrisonMine mine = mineManager.getMine(name);
          RefillHandler handler = mine.getRefillHandler();

          if(handler.canRefill()) {
            mine.getRefillHandler().refillMine();
            TextUtil.sendMessage(player,"&aMine refilled");
          }else{

           TextUtil.sendMessage(player,"&c Cannot refill at this time");
          }

        }
      }
    }
  }

  @Override
  public List<String> tabComplete (CmdSender sender, String label, String[] args) {
    return null;
  }
}
