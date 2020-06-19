package me.harley.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CropTopPlayer {

	private ManageData data = ManageData.inst();
	private static List<CropTopPlayer> players = new ArrayList<CropTopPlayer>();
	public static List<CropTopPlayer> getPlayers() {
		return players;
	}

	private UUID uuid;
	private HashMap<String, Integer> stats;

	public CropTopPlayer(UUID uuid, HashMap<String, Integer> stats, int sugarcaneCount, int cactusCount, int wheatCount, int pumpkinCount,
			int melonCount, int carrotCount, int potatoCount) {
		setUuid(uuid);
		stats.put("SUGAR_CANE", sugarcaneCount);
		stats.put("CACTUS", cactusCount);
		stats.put("WHEAT", wheatCount);
		stats.put("PUMPKIN", pumpkinCount);
		stats.put("MELON", melonCount);
		stats.put("CARROT", carrotCount);
		stats.put("POTATO", potatoCount);
		setStats(stats);
		getPlayers().add(this);
		this.saveToFile();
	}

	public CropTopPlayer() {
	}

	public void saveToFile() {
		data.getData().set("stats." + this.getUuid() + ".crops.sugar_cane_block", this.getStats().get("SUGAR_CANE"));
		data.getData().set("stats." + this.getUuid() + ".crops.cactus", this.getStats().get("CACTUS"));
		data.getData().set("stats." + this.getUuid() + ".crops.wheat", this.getStats().get("WHEAT"));
		data.getData().set("stats." + this.getUuid() + ".crops.pumpkin", this.getStats().get("PUMPKIN"));
		data.getData().set("stats." + this.getUuid() + ".crops.melon", this.getStats().get("MELON"));
		data.getData().set("stats." + this.getUuid() + ".crops.carrot", this.getStats().get("CARROT"));
		data.getData().set("stats." + this.getUuid() + ".crops.potato", this.getStats().get("POTATO"));
		data.saveData();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public HashMap<String, Integer> getStats() {
		return stats;
	}

	public void setStats(HashMap<String, Integer> stats) {
		this.stats = stats;
	}

	public CropTopPlayer fromUuid(UUID uuid) {
		for (CropTopPlayer players : getPlayers()) {
			if (players.getUuid().equals(uuid)) {
				return players;
			}
		}
		return null;
	}

	public Player getPlayer(CropTopPlayer cropTopPlayer) {
		return Bukkit.getPlayer(cropTopPlayer.getUuid());

	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.getUuid());

	}

	public void unregister() {
	}

}
