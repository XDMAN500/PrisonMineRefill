package me.varmetek.plugin.prisonminerefill.hooks;

public final  class HookManager
{
  private static HookManager instance;


  protected PluginHookFAWE hookFAWE;
  protected PluginHookWorldEdit hookWorldEdit;
  protected PluginHookWorldGuard hookWorldGuard;

  private HookManager(){
    instance = this;
    hookFAWE = new PluginHookFAWE();
    hookWorldEdit = new PluginHookWorldEdit();
    hookWorldGuard = new PluginHookWorldGuard();
  }


  public static HookManager getInstance(){
    return instance == null ? new HookManager() : instance;
  }


  public PluginHookFAWE getHookFAWE(){
    return hookFAWE;
  }


  public PluginHookWorldEdit getHookWorldEdit(){
    return  hookWorldEdit;
  }


  public PluginHookWorldGuard getHookWorldGuard(){
    return  hookWorldGuard;
  }
}
