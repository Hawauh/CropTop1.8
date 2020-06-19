package me.harley.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.harley.cropstop.CropsTop;

public class ManageData implements Listener {

	private CropsTop main = CropsTop.getPlugin(CropsTop.class);
	private static ManageData inst = new ManageData();

	public static ManageData inst() {
		return inst;
	}

	public FileConfiguration getData() {
		return main.data;
	}

	public void saveData() {
		try {
			main.data.save(main.dfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().log(Level.SEVERE, "Error while saving data!");
		}
	}

	private void uploadPlayers() {
		for (CropTopPlayer players : CropTopPlayer.getPlayers()) {
			players.saveToFile();
		}
	}

	@EventHandler
	public void onSetupPlayer(PlayerJoinEvent e) {
		setupPlayer(e.getPlayer());
	}


	private void setupPlayer(Player p) {
		String uuidStr = p.getUniqueId().toString();
		if (!(getData().contains("stats." + uuidStr))) {
			new CropTopPlayer(p.getUniqueId(), new HashMap<>(), 0, 0, 0, 0, 0, 0, 0);
		} else {
			new CropTopPlayer(UUID.fromString(uuidStr), new HashMap<String, Integer>(),
					getBroken(uuidStr, "sugar_cane_block"), getBroken(uuidStr, "cactus"), getBroken(uuidStr, "wheat"),
					getBroken(uuidStr, "pumpkin"), getBroken(uuidStr, "melon"), getBroken(uuidStr, "carrot"),
					getBroken(uuidStr, "potato"));
		}
	}

	public int getBroken(String offlinePlayer, String crop) {
		return getData().getInt("stats." + offlinePlayer + ".crops." + crop);
	}

	public List<CropTopPlayer> getTop() {
		List<CropTopPlayer> ar = new ArrayList<CropTopPlayer>(CropTopPlayer.getPlayers());

		Collections.sort(ar, new Sort());
		Collections.reverse(ar);

		return ar;
	}

	public void startAutoSave() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				uploadPlayers();
			}
		}, 0, 20 * 60 * 5);
	}

}
