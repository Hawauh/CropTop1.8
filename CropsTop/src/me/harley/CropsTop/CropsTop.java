package me.harley.CropsTop;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.harley.inventory.CropCatergoryInventory;
import me.harley.inventory.Top9Inventory;

public class CropsTop extends JavaPlugin {

	public void onEnable() {
		System.out.println("[CropsTop] Enabled!");
		saveDefaultConfig();
		reloadConfig();

		getCommand("cropstop").setExecutor(new CropsTopCmd());

		getServer().getPluginManager().registerEvents(new CropCatergoryInventory(), this);
		getServer().getPluginManager().registerEvents(new Top9Inventory(), this);
		getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getPluginManager().registerEvents(SetupData.inst(), this);

		setup(this);
		SetupData.inst().registerPlayers();

	}

	public void onDisable() {

	}

	FileConfiguration data;

	File dfile;

	public void setup(Plugin plugin) {
		dfile = new File(plugin.getDataFolder(), "data.yml");
		if (!dfile.exists())
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe("Could not create data.yml!");
			}
		data = YamlConfiguration.loadConfiguration(dfile);
	}

}
