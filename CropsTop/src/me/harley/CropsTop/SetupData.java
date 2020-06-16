package me.harley.CropsTop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SetupData implements Listener {

	CropsTop main = CropsTop.getPlugin(CropsTop.class);
	private static SetupData inst = new SetupData();

	public static SetupData inst() {
		return inst;
	}

	public FileConfiguration getData() {
		return main.data;
	}

	public void saveData() {
		try {
			main.data.save(main.dfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("Could not save data.yml!");
		}
	}

	public void uploadPlayers() {
		for (CropTopPlayer players : CropTopPlayer.players) {
			players.saveToFile();
		}
	}

	// create a new object for each player upon onEnable
	public void registerPlayers() {
		for (String players : getData().getConfigurationSection("stats").getKeys(false)) {
			new CropTopPlayer(UUID.fromString(players), new HashMap<String, Integer>(),
					getBroken(players, "sugar_cane_block"), getBroken(players, "cactus"), getBroken(players, "wheat"),
					getBroken(players, "pumpkin"), getBroken(players, "melon"), getBroken(players, "carrot"),
					getBroken(players, "potato"));
		}
	}

	@EventHandler
	private void onSetupPlayer(PlayerJoinEvent e) {
		setupPlayer(e.getPlayer());
	}

	public void setupPlayer(Player p) {
		String uuid = p.getUniqueId().toString();
		if (!(getData().contains("stats." + uuid))) {
			new CropTopPlayer(p.getUniqueId(), new HashMap<String, Integer>(), 0, 0, 0, 0, 0, 0, 0);
		}
	}

	public int getBroken(String offlinePlayer, String crop) {
		return getData().getInt("stats." + offlinePlayer + ".crops." + crop);
	}

	public List<CropTopPlayer> getTop() {
		List<CropTopPlayer> ar = new ArrayList<CropTopPlayer>(CropTopPlayer.players);

		Collections.sort(ar, new Sort());
		Collections.reverse(ar);

		return ar;
	}

}
