package me.varmetek.plugin.prisonminerefill.util;

import com.google.common.base.Preconditions;
import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;
import org.bukkit.Bukkit;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class GlobalPrisonTimer
{
   private int tickCount;
   private int tickLimit;
   private List<WeakReference<Runnable>> callbacks;


   public GlobalPrisonTimer( int  tickLimit){
    this.tickLimit = tickLimit;
    callbacks = new ArrayList<>();
     Bukkit.getScheduler().runTaskTimer(PrisonMineRefillPlugin.getInstance(),
       ()->{
        tickCount += 1;
       if(tickCount % tickLimit == 0){
         List<WeakReference<Runnable>> refs = new ArrayList<>(callbacks);
         for(int i = refs.size() -1; i >= 0; i-=1){
           WeakReference<Runnable> callBack = refs.get(i);
           if(callBack.get() == null){
             callbacks.remove(i);
           }else{
             callBack.get().run();;
           }
         }
       }
       },0L, 1L);
   }


   public void registerCallback(Runnable run){
     callbacks.add(new WeakReference<>(run));
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
