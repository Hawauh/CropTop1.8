package me.harley.display;

import org.bukkit.entity.Player;

import me.harley.manager.CropTopPlayer;
import me.harley.manager.ManageData;

public class TopPlayersChat {

	private ManageData data = ManageData.inst();

	public void sendTopMessage(String crop, Player p) {
		p.sendMessage("§e§m*----------------------------*");
		p.sendMessage("");
		p.sendMessage("             §e§lCropsTop         ");
		p.sendMessage("             §7" + crop + "         ");
		p.sendMessage("");
		int place = 1;
		for (CropTopPlayer top : data.getTop()) {
			p.sendMessage(
					"  §e§l#" + place + " §7" + top.getPlayer().getName() + " §8§o(" + top.getStats().get(crop) + ")");
			place++;
		}
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("§e§m*----------------------------*");

	}

}
