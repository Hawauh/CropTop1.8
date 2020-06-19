package me.harley.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import me.harley.cropstop.CropsTop;

public class ChatManager {

	private CropsTop main = CropsTop.getPlugin(CropsTop.class);

	public void registerColorCodes() {
		//FileConfiguration config = main.getConfig();

	}

	public String getTranslatedString(String path) {
		return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(path));
	}
	public List<String> getTranslatedList(String path) {
		List<String> translatedListed = new ArrayList<>();
		for(String lines : main.getConfig().getStringList(path)) {
			translatedListed.add(ChatColor.translateAlternateColorCodes('&', lines));
		}
		return translatedListed;

	}

}
