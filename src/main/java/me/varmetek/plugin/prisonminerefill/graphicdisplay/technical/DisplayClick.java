package me.varmetek.plugin.prisonminerefill.graphicdisplay.technical;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface DisplayClick extends DisplayIcon
{

   void  onClick(InventoryClickEvent event);
}
