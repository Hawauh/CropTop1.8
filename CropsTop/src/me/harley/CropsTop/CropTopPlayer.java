package me.harley.CropsTop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CropTopPlayer {

	static List<CropTopPlayer> players = new ArrayList<CropTopPlayer>();
	SetupData data = SetupData.inst();

	private UUID uuid;
	private HashMap<String, Integer> stats;

	public CropTopPlayer(UUID uuid, HashMap<String, Integer> stats, int sugarcaneCount, int cactusCount, int wheatCount, int pumpkinCount,
			int melonCount, int carrotCount, int potatoCount) {
		this.setUuid(uuid);
		this.setStats(stats);
		stats.put("SUGAR_CANE", sugarcaneCount);
		stats.put("CACTUS", cactusCount);
		stats.put("WHEAT", wheatCount);
		stats.put("PUMPKIN", pumpkinCount);
		stats.put("MELON", melonCount);
		stats.put("CARROT", carrotCount);
		stats.put("POTATO", potatoCount);
		this.saveToFile();
		players.add(this);
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

	public static CropTopPlayer getCropTopPlayer(UUID uuid) {
		for (CropTopPlayer players : players) {
			if (players.getUuid().equals(uuid)) {
				return players;
			}
		}
		return null;
	}

	public static Player getPlayer(CropTopPlayer cropTopPlayer) {
		return Bukkit.getPlayer(cropTopPlayer.getUuid());

	}

}
