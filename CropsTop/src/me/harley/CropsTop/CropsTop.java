package me.harley.cropstop;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.harley.display.CropCategoryInventory;
import me.harley.display.TopPlayersInventory;
import me.harley.manager.CropsTopCmd;
import me.harley.manager.ManageData;

public class CropsTop extends JavaPlugin {

	public FileConfiguration data;
	public File dfile;

	ManageData manageData;

	public void onEnable() {
		//Bukkit.getLogger().log(Level.INFO, "=======================================");
		saveDefaultConfig();
		reloadConfig();

		getCommand("cropstop").setExecutor(new CropsTopCmd());

		getServer().getPluginManager().registerEvents(new CropCategoryInventory(), this);
		getServer().getPluginManager().registerEvents(new TopPlayersInventory(), this);
		getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getPluginManager().registerEvents(new ManageData(), this);

		ManageData manageData = ManageData.inst();

		setup();
		manageData.startAutoSave();
		//Bukkit.getLogger().log(Level.INFO, "=======================================");
	}

	private void setup() {
		dfile = new File(this.getDataFolder(), "data.yml");
		if (!dfile.exists())
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe("Error while creating data.yml!");
			}
		data = YamlConfiguration.loadConfiguration(dfile);
	}

}
