package me.harley.manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.harley.cropstop.CropsTop;
import me.harley.display.CropCategoryInventory;

public class CropsTopCmd implements CommandExecutor {

	private CropsTop cropstop = CropsTop.getPlugin(CropsTop.class);
	private ManageData data = ManageData.inst();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("cropstop")) {
				if (args.length == 0)
					new CropCategoryInventory().createInventory(p);
				else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						if (p.hasPermission("cropstop.reload")) {
							cropstop.saveConfig();
							data.saveData();
							p.sendMessage(cropstop.getConfig().getString("reloaded").replace("&", "§"));
							return true;
						}
						p.sendMessage(cropstop.getConfig().getString("no-perms").replace("&", "§"));
					}
				}
			}
		}
		return true;
	}

}
