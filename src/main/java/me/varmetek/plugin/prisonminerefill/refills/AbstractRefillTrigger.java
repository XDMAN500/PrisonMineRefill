package me.varmetek.plugin.prisonminerefill.refills;

public abstract class AbstractRefillTrigger implements RefillTrigger
{

  protected RefillHandler refillHandler;

  public AbstractRefillTrigger(RefillHandler handler){
    this.refillHandler = handler;
  }

  @Override
  public void refill () {
    refillHandler.refillMine();
  }


  @Override
  public RefillHandler getHandler(){
    return refillHandler;

  }


  @Override
  public abstract void onNotify () ;
}
