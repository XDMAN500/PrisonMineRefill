package me.varmetek.plugin.prisonminerefill.graphicdisplay.technical;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class DisplayPageSingle implements DisplayPage
{

  protected Map<Integer,DisplayIcon> elements;
  protected DisplaySession session;
  protected boolean cancelAllClicks;

  public DisplayPageSingle(DisplaySession session, boolean cancelAllClicks){
    this.session = session;
    elements = new HashMap<>(session.getInventory().getSize());
    this.cancelAllClicks = cancelAllClicks;
  }

  @Override
  public void addDisplayItem (DisplayIcon icon) {
      for(int i = 0 ; i < session.getInventory().getSize() ; i++ ){
        if(!elements.containsKey(i)){
          elements.put(i ,icon);
        }

      }
  }

  @Override
  public void setDisplayItem (int index, DisplayIcon icon) {
      elements.put(index, icon);
  }

  @Override
  public DisplayIcon getDisplayItem (int index) {
    return elements.get(index);
  }

  @Override
  public int getNumOfIcons () {
    return elements.size();
  }

  @Override
  public void removeDisplayItem (int index) {
        elements.remove(index);
  }

  @Override
  public void updateDisplay(){

    for(Map.Entry<Integer,DisplayIcon> ss: elements.entrySet()){
      session.getInventory().setItem(ss.getKey(),ss.getValue().getItem());
    }
  }

  @Override
  public DisplaySession getSession(){

    return session;
  }


  public void handleClick (InventoryClickEvent event) {
    Inventory clicked = event.getClickedInventory();
    if(clicked == null) return;

    DisplayIcon icon = elements.get(event.getRawSlot());

    if(icon == null){
      if( cancelAllClicks) {
        event.setCancelled(true);
      }
    }else{
      event.setCancelled(true);
      if(icon instanceof DisplayClick){
        ((DisplayClick) icon).onClick(event);
      }
    }

  }
}
