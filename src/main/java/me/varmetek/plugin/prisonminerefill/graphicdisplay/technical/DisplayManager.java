package me.varmetek.plugin.prisonminerefill.graphicdisplay.technical;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class DisplayManager implements Listener
{

  @EventHandler
  public void onClick(InventoryClickEvent event){
    if(event.getClickedInventory() == null) return;

    Inventory top = event.getView().getTopInventory();
    if(top.getHolder() instanceof DisplaySession){

      DisplaySession session = (DisplaySession) top.getHolder();
      DisplayPage current = session.getCurrentPage();
      current.handleClick(event);
    }
  }
}
