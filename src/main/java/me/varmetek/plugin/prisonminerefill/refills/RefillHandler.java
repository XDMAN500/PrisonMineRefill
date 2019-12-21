package me.varmetek.plugin.prisonminerefill.refills;

import com.google.common.base.Preconditions;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import me.varmetek.plugin.prisonminerefill.composition.BlockCompostion;
import me.varmetek.plugin.prisonminerefill.populator.BlockPopulator;
import me.varmetek.plugin.prisonminerefill.prisonmine.PrisonMine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RefillHandler
{
  protected Map<Class<? extends RefillTrigger> ,RefillTrigger> triggers;
  private PrisonMine mine;
  private BlockPopulator populator;

  public RefillHandler (PrisonMine mine){
    triggers = new HashMap<>();
    this.mine = mine;
  }


  public PrisonMine getPrisonMine(){

    return mine;
  }

  public BlockPopulator getDefaultPopulator (){
    return populator;
  }

  public void setDefaultPopulator (BlockPopulator  comp){
    if(comp != null){

      //Preconditions.checkArgument(comp.getPrisonMine().equals(this),"Cant assign a populator not dedicated to mine " + this.getId());
    }
    this.populator = comp;
  }

  public void addTrigger(RefillTrigger refillTrigger){
    Preconditions.checkNotNull(refillTrigger);
    triggers.put(refillTrigger.getClass(),refillTrigger);
  }

  public boolean hasTrigger(Class<? extends RefillTrigger> clazz){
    Preconditions.checkNotNull(clazz);
    return triggers.containsKey(clazz);
  }


  public void removeTrigger(Class<? extends RefillTrigger> clazz){
    Preconditions.checkNotNull(clazz);
    triggers.remove(clazz);
  }


  public void notifyOfRefill(){
      triggers.values().forEach( trigger -> trigger.onNotify());
  }


  public Collection<RefillTrigger> getRefillTriggers(){
    return Collections.unmodifiableCollection(triggers.values());
  }


  public boolean canRefill(){
    return mine.getCompostion() != null && getDefaultPopulator() != null && mine.getRegion() != null;
  }

  public void refillMine(){
    if(canRefill()) {
      BlockPopulator populator = getDefaultPopulator();
      BlockCompostion compostion = mine.getCompostion();
      refillMine(populator,compostion);

    }else{
      PrisonMineRefillPlugin.getInstance().getLogger().info("Cant refile mine " + mine.getId().toString());
    }

  }

  public void refillMine(BlockCompostion comp){

    getDefaultPopulator().populate(comp,mine.getRegion());
    mine.getRefillHandler().notifyOfRefill();
    PrisonMineRefillPlugin.getInstance().getLogger().info("refilled mine " + mine.getId().toString());


  }

  public void refillMine(BlockPopulator populator, BlockCompostion comp ){

    populator.populate(comp,mine.getRegion());
    mine.getRefillHandler().notifyOfRefill();
    PrisonMineRefillPlugin.getInstance().getLogger().info("refilled mine " + mine.getId().toString());


  }
}
