package me.varmetek.plugin.prisonminerefill.commands.technical;

import java.util.*;

public abstract class CmdCommand
{

  protected String name;
  protected List<String> aliases;


  public CmdCommand(String name, Collection<String> alias){
    this.name = name;
    this.aliases = new ArrayList<>(alias);
  }

  public CmdCommand(String name, String... alias){
    this.name = name;
    this.aliases = Arrays.asList(alias);
  }

  public CmdCommand(String name){
    this.name = name;
    this.aliases = new ArrayList<>();

  }


  public String getName(){

    return name;
  }

  public List<String> getAliases(){

    return Collections.unmodifiableList(aliases);
  }

  public abstract void execute (CmdSender sender, String label, String[] args);

  public abstract List<String> tabComplete (CmdSender sender, String label, String[] args);
}
