package me.varmetek.plugin.prisonminerefill.blockplace;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface ChunkPlacer
{

  void placeAll(Chunk chunk, Collection<ChunkBlock> changes);

  void place(Chunk chunk, ChunkBlock change);

  void sendBlockChanges(Player player, Chunk chunk, Collection<ChunkBlock> changes);
}
