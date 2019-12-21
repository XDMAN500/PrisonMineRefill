package me.varmetek.plugin.prisonminerefill.refills;

import com.google.common.base.Preconditions;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import org.bukkit.Bukkit;

public class RefillTriggerLocalTimer extends AbstractRefillTrigger
{
  protected  int tickCount;
  protected int tickLimit;


  public RefillTriggerLocalTimer (RefillHandler handle){
    this(handle,20*60*10);


  }

  public RefillTriggerLocalTimer (RefillHandler handle, int tickLimit){
    super(handle);

    this.tickLimit = tickLimit;

    Bukkit.getScheduler().runTaskTimer(PrisonMineRefillPlugin.getInstance(),
      ()->{
        tickCount += 1;
        if(tickCount % tickLimit == 0){
            refill();
        }
      },0L, 1L);
  }



  
  @Override
  public void onNotify () {
   tickCount = 0;
   //Start clock over when refilled
  }

  public void setTickLimit(int tickLimit){
    Preconditions.checkArgument( tickLimit > 1, "Tick limit must be larger than 2 ticks ");
    this.tickLimit = tickLimit;
  }

  public int getTickLimit(){
    return tickLimit;
  }


  public int getCurrentTick(){
    return tickCount;
  }
}
