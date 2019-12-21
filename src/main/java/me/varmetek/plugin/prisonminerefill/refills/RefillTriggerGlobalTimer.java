package me.varmetek.plugin.prisonminerefill.refills;

import me.varmetek.plugin.prisonminerefill.PrisonMineRefillPlugin;


/**
 *
 * Refill trigger bound by the global timer settings
 * */
public class RefillTriggerGlobalTimer extends AbstractRefillTrigger
{

  protected Runnable globalTimerCallback;

  public RefillTriggerGlobalTimer(RefillHandler handle){
    super(handle);

    globalTimerCallback = createTimerCallback();

    PrisonMineRefillPlugin.getService().getGlobalPrisonTimer().registerCallback(globalTimerCallback);
  }


  protected Runnable createTimerCallback(){
    return new Runnable()
    {
      @Override
      public void run () {
        refill();
      }
    };
  }

  @Override
  public void onNotify () {
    //nothing to update
  }
}
