package me.varmetek.plugin.prisonminerefill.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.varmetek.plugin.prisonminerefill.MineRefillService;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdCommand;
import me.varmetek.plugin.prisonminerefill.commands.technical.CmdSender;
import me.varmetek.plugin.prisonminerefill.composition.BlockCompositionBuilder;
import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.composition.WeightedBlockType;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditSession;
import me.varmetek.plugin.prisonminerefill.editor.PrisonMineEditor;
import me.varmetek.plugin.prisonminerefill.hooks.HookManager;
import me.varmetek.plugin.prisonminerefill.hooks.PluginHookWorldEdit;
import me.varmetek.plugin.prisonminerefill.populator.BlockPopulator;
import me.varmetek.plugin.prisonminerefill.populator.BlockPopulatorChunk;
import me.varmetek.plugin.prisonminerefill.populator.BlockPopulatorFawe;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineManager;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMineRegion;
import me.varmetek.plugin.prisonminerefill.refills.RefillHandler;
import me.varmetek.plugin.prisonminerefill.refills.RefillTriggerGlobalTimer;
import me.varmetek.plugin.prisonminerefill.refills.RefillTriggerManual;
import me.varmetek.plugin.prisonminerefill.util.ImmutableVector;
import me.varmetek.plugin.prisonminerefill.util.StringKey;
import me.varmetek.plugin.prisonminerefill.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class CommandEditMine extends CmdCommand
{


  public CommandEditMine () {
    super("editmine");
  }

  @Override
  public void execute (CmdSender sender, String label, String[] args) {
    if(!sender.isPlayer()){
      TextUtil.sendMessage(sender.asSender(), "&c Command is only for players!");
      return;

    }
    Player player = sender.asPlayer();

    if(args.length == 0){
      TextUtil.sendMessage(sender.asSender(),
        "&7/editmine edit <mine>",
        "&7/editmine blocks",
        "&7/editmine populator",
        "&7/editmine region",
        "&7/editmine status",
        "&7/editmine trigger",
        "&7/editmine gui",
        "&7/editmine save"

        );
      return;
    }


    if("blocks".equalsIgnoreCase(args[0])){

      if(args.length ==1){

        TextUtil.sendMessage(sender.asSender(),
          "&7/editmine blocks set <type> <weight>",
          "&7/editmine blocks remove <type> ",
          "&7/editmine blocks replace <type> <other_type>",
          "&7/editmine blocks list"

        );
        return;
      }


      if("remove".equalsIgnoreCase(args[1])){
        if(args.length != 3){
          TextUtil.sendMessage(player,"&7editmine blocks remove <type>");
          return;
        }


        Material type = null;

        try{

          type = Material.getMaterial(args[2]);
        }catch (RuntimeException ex){

          TextUtil.sendMessage(player,"&cCould not find material ");
          return;
        }

        performRemBlock(player,type);
      }else if("set".equalsIgnoreCase(args[1])){
        if(args.length != 4){
          TextUtil.sendMessage(player, "&7/editmine blocks set  <type> <weight>" );
          return;
        }


        Material type = null;

        try{

          type = Material.getMaterial(args[2]);
        }catch (RuntimeException ex){

          TextUtil.sendMessage(player,"&cCould not find material ");
          return;
        }


        Integer weight = null;

        try{

          weight = Integer.valueOf(args[3]);

        }catch (NumberFormatException ex){


          TextUtil.sendMessage(player,"&cCould parse the number ");
          return;
        }

        performSetBlock(player, type, weight.intValue());


      }else if("replace".equalsIgnoreCase(args[1])){
        Material type = null;
        Material other = null;

        try{

          type = Material.getMaterial(args[2]);
        }catch (RuntimeException ex){

          TextUtil.sendMessage(player,"&cCould not find material ");
          return;
        }

        try{

         other = Material.getMaterial(args[3]);
        }catch (RuntimeException ex){

          TextUtil.sendMessage(player,"&cCould not find material ");
          return;
        }


      }else if("list".equalsIgnoreCase(args[1])){

        performListBlocks(player);
      }else{


      }



    }else if("populator".equalsIgnoreCase(args[0])){

      if(args.length ==1){

        TextUtil.sendMessage(sender.asSender(),
          "&7/editmine populator set <type>",
          "&7/editmine populator get ",
          "&7/editmine populator list "

        );

        return;
      }


      if("set".equalsIgnoreCase(args[1])){

      }else if("get".equalsIgnoreCase(args[1])){



      }else if("list".equalsIgnoreCase(args[1])){


      }else{


      }

    }else if("region".equalsIgnoreCase(args[0])){

      PluginHookWorldEdit hookWorldEdit = HookManager.getInstance().getHookWorldEdit();
      if(!hookWorldEdit.hasHook()){
          TextUtil.sendMessage(sender.asSender(),"&cWorldEdit is not enabled!");
        return;

      }

      WorldEditPlugin worldEditPlugin = hookWorldEdit.getWorldEditPlugin();


      if(args.length ==1){

        TextUtil.sendMessage(sender.asSender(),
          "&7/editmine region set ",
          "&7/editmine region get "

        );

        return;
      }


      if("set".equalsIgnoreCase(args[1])){

        performSetRegion(player,worldEditPlugin);





      }else if("get".equalsIgnoreCase(args[1])){
          performGetRegion(player);


      }else{
        TextUtil.sendMessage(sender.asSender(),
          "&7/editmine region set ",
          "&7/editmine region get "

        );

      }
    }else if("trigger".equalsIgnoreCase(args[0])){

      if(args.length ==1){

        TextUtil.sendMessage(sender.asSender(),
          "&7/editmine trigger set <type> ",
          "&7/editmine trigger status ",
          "&7/editmine trigger list  ",
          "&7/editmine trigger configure <type> "
        );
      }
    }else if("gui".equalsIgnoreCase(args[0])){


    }else if("save".equalsIgnoreCase(args[0])) {
      performSave(player);

    } else if("status".equalsIgnoreCase(args[0])){

      performStatus(player);
    }else{

      TextUtil.sendMessage(player, "&cUnknown subcommand " + args[0]);
    }




    }


  @Override
  public List<String> tabComplete (CmdSender sender, String label, String[] args) {
    return null;
  }






  private void performListBlocks(Player player){
    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();
    BlockCompostion comp = mine.getCompostion();

    TextUtil.sendMessage(player, "&7 Listing blocks for mine &e "+ mine.getId().toString());

    if(comp == null){

      TextUtil.sendMessage(player,"&c no compositon has been set");
    }else {
      for (WeightedBlockType type : comp) {

        TextUtil.sendMessage(player, "&7 - &e" + type.getBlockType().name() + "  " + type.getWeight());
      }
    }








  }


  private void performStatus(Player player){

    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();
    boolean canRefill = mine.getRefillHandler().canRefill();
    boolean hasComposition = mine.getCompostion() != null && mine.getCompostion().isEmpty();
    boolean  hasRegion = mine.getRegion() != null;
    boolean hasPopulator = mine.getRefillHandler().getDefaultPopulator() != null;


    TextUtil.sendMessage(player, "Status of mine "+ mine.getId());
    TextUtil.sendMessage(player, "Can Refill : " + String.valueOf(canRefill));
    TextUtil.sendMessage(player, "Has Composition: " + String.valueOf(hasComposition) );
    TextUtil.sendMessage(player, "Has region: " + String.valueOf(hasRegion));
    TextUtil.sendMessage(player,"Has populator: " + String.valueOf(hasPopulator));


  }


  private void performSave(Player player) {

    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session = editor.getEditSession(player.getUniqueId());


    if (session == null) {

      TextUtil.sendMessage(player, "&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();


    PrisonMineManager mineManager = service.getPrisonMineManager();
    mineManager.addMine(mine);
    editor.removeEditSession(player.getUniqueId());


    TextUtil.sendMessage(player, "&cSaved mine. You are no longer editing.");



  }


  private void performSetBlock(Player player, Material mat, int weight){

    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();
    BlockCompostion comp = mine.getCompostion();
    BlockCompositionBuilder builder = comp == null ? new BlockCompositionBuilder() : new BlockCompositionBuilder(comp);
    builder.setBlockWeight(mat,weight);

    BlockCompostion newComp = new BlockCompostion(builder);
    mine.setCompostion(newComp);

    TextUtil.sendMessage(player,"&c Removed material " + mat.name() + " from the mine " + mine.getId());

  }

  private void performRemBlock(Player player, Material mat){

    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();
    BlockCompostion comp = mine.getCompostion();
    BlockCompositionBuilder builder = comp == null ? new BlockCompositionBuilder() : new BlockCompositionBuilder(comp);
    builder.removeBlock(mat);

    BlockCompostion newComp = new BlockCompostion(builder);
    mine.setCompostion(newComp);

    TextUtil.sendMessage(player,"&c Removed material " + mat.name() + " from the mine " + mine.getId());

  }


  private void performGetRegion(Player player){
    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    PrisonMine mine = session.getCurrentMine();
    PrisonMineRegion region = mine.getRegion();
    if(region == null){

      TextUtil.sendMessage(player,"&c No region is set for this mine.");
    }else{

      World world = region.getWorld();
      StringBuilder maxPos = new StringBuilder();
      StringBuilder minPos = new StringBuilder();
      ImmutableVector maxVec = region.getMaxVector();
      ImmutableVector minVec = region.getMaxVector();

      maxPos.append('<')
        .append(maxVec.getX()).append(", ")
        .append(maxVec.getY()).append(", ")
        .append(maxVec.getZ())
        .append('>');

      minPos.append('<')
        .append(minVec.getX()).append(", ")
        .append(minVec.getY()).append(", ")
        .append(minVec.getZ())
        .append('>');



      TextUtil.sendMessage(player,
        "&aRegion of mine "+ mine.getId().toString(),
        "&bWorld: &7" + world.getName(),
        "&bMax Pos: &7" + maxPos.toString(),
        "&bMin Pos: &7" + minPos.toString()


      );

    }

  }

  private void performSetRegion(Player player, WorldEditPlugin worldEditPlugin){

    MineRefillService service = PrisonMineRefillPlugin.getService();
    PrisonMineEditor editor = service.getPrisonMineEditor();
    PrisonMineEditSession session  = editor.getEditSession(player.getUniqueId());


    if(session == null){

      TextUtil.sendMessage(player,"&cYou are not editing a mine. Try /editmine edit <mine>");
      return;
    }

    LocalSession lsession = worldEditPlugin.getSession(player);

    Region region =  null;
    try{
      region = lsession.getSelection(BukkitUtil.getLocalWorld(player.getWorld()));

    }catch (IncompleteRegionException ex){

      TextUtil.sendMessage(player,"&c You must complete the region");
      return;
    }

    Vector first = new Vector(
      region.getMaximumPoint().getBlockX(),
      region.getMaximumPoint().getBlockY(),
      region.getMaximumPoint().getBlockZ()

    );
    Vector second = new Vector(
      region.getMinimumPoint().getBlockX(),
      region.getMinimumPoint().getBlockY(),
      region.getMinimumPoint().getBlockZ()

    );

    PrisonMineRegion mineRegion = new PrisonMineRegion(
      player.getWorld(),
      first,
      second
    );

    PrisonMine mine = session.getCurrentMine();
    mine.setRegion(mineRegion);

    TextUtil.sendMessage(player, "&a Region set");
  }

  private void performTest(Player player){


    //  if(!HookManager.getInstance().getHookWorldEdit().hasHook()){
    //   TextUtil.sendMessage(sender.asSender(), "&c World edit must be present!");
    //    return;
    //  }
///
    //  WorldEditPlugin plugin = HookManager.getInstance().getHookWorldEdit().getWorldEditPlugin();
    // LocalSession session = plugin.getSession(sender.asPlayer());

    //  Region region  = null;
    //   try {
    //     region = session.getWorldSelection();
    //   }catch (IncompleteRegionException ex){
    //     TextUtil.sendMessage(sender.asSender(), "&c No region is selected");
    //     return;
    //   }


    //   if(!(region instanceof CuboidRegion)){
    //     TextUtil.sendMessage(sender.asSender(), "&c Selection needs to be a cuboid");
//      return;
    // }


    Vector max  = player.getLocation().add(10,10,10).toVector();
    Vector min  = player.getLocation().add(-10,-10,-10).toVector();
    String testName = "test";



    PrisonMine mine = new PrisonMine(new StringKey(testName));
    RefillHandler manager = mine.getRefillHandler();

    manager.addTrigger(new RefillTriggerManual(manager));
    manager.addTrigger(new RefillTriggerGlobalTimer(manager));


    PrisonMineRegion mineRegion = new PrisonMineRegion(player.getWorld(),
      new org.bukkit.util.Vector(max.getBlockX(), max.getBlockY(),max.getBlockZ()),
      new org.bukkit.util.Vector(min.getBlockX(),min.getBlockY(),min.getBlockZ())

    );

    mine.setRegion(mineRegion);

    BlockCompositionBuilder builder = new BlockCompositionBuilder();
    builder.setBlockWeight(Material.STONE,100);
    builder.setBlockWeight(Material.CYAN_WOOL,100);
    BlockCompostion comp = new BlockCompostion(builder);

    mine.setCompostion(comp);

    BlockPopulator populator = null;

    if(HookManager.getInstance().getHookFAWE().hasHook()){
      populator = new BlockPopulatorFawe(mine);
    }else{
      populator = new BlockPopulatorChunk( mine);


    }

    mine.getRefillHandler().setDefaultPopulator(populator);
    mine.setSafeLocation(player.getLocation());


    mine.getRefillHandler().refillMine();


    player.sendMessage("Refilled mine "+ mine.getId());
  }
}
