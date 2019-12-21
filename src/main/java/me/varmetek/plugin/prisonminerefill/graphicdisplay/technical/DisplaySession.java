package me.varmetek.plugin.prisonminerefill.graphicdisplay.technical;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DisplaySession implements InventoryHolder
{

  protected Inventory inv;
  protected DisplayPage currentPage;

  public DisplaySession(String invName, int rows){

    inv = Bukkit.createInventory(this,9*rows,invName);
  }


  @Override
  public Inventory getInventory () {
    return inv;
  }


  public DisplayPage getCurrentPage(){
    return currentPage;
  }

  public void setCurrentPage(DisplayPage page){
    if(!this.equals(page.getSession())){
      throw new IllegalArgumentException("Set current page to one with foreign session");
    }


    page.updateDisplay();
  }

  public void showDisplay(Player player){
    player.openInventory(inv);
  }
}
