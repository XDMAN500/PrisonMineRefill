package me.varmetek.plugin.prisonminerefill.graphicdisplay.technical;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface DisplayPage
{
  void addDisplayItem(DisplayIcon icon);

  void setDisplayItem(int index,DisplayIcon icon);

  DisplayIcon getDisplayItem(int index);

  int getNumOfIcons();

  void removeDisplayItem(int index);

  void handleClick(InventoryClickEvent event);

  void updateDisplay();

  DisplaySession getSession();
}
