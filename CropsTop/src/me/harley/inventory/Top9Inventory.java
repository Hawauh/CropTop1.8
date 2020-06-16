package me.harley.inventory;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.harley.CropsTop.CropTopPlayer;
import me.harley.CropsTop.CropsTop;
import me.harley.CropsTop.SetupData;
import me.harley.utils.SkullCreator;

public class Top9Inventory extends SetupData implements InventoryHolder, Listener {

	private CropsTop cropstop = CropsTop.getPlugin(CropsTop.class);
	private Inventory inv;

	private final String no_stat_exists = cropstop.getConfig().getString("no-stats-exist").replace("&", "§");
	private final String topItemName = cropstop.getConfig().getString("gui-top-player-inventory-item-name").replace("&",
			"§");

	public void createInventory(Player p, String crop) {
		Inventory inv = Bukkit.createInventory(this, 9,
				cropstop.getConfig().getString("gui-name-" + crop).replace("&", "§"));

		if (getBroken(p.getUniqueId().toString(), crop) == 0) {
			p.sendMessage(no_stat_exists.replace("{crop}", crop));
			return;
		}
		for (int i = 0; i < getTop().size(); i++) {
			inv.setItem(i, getTopItem(getTop().get(i).getUuid().toString(),
					getAmountOfPlayer(crop, getTop().get(i).getUuid()), (i + 1)));
		}
		this.inv = inv;
		p.openInventory(inv);
	}

	public Inventory getInventory() {
		return inv;
	}

	private ItemStack getTopItem(String uuid, int i, int place) {
		ItemStack item;
		item = new SkullCreator().itemFromUuid(UUID.fromString(uuid));
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(topItemName.replace("{player}", Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName())
				.replace("{place}", place + ""));
		meta.setLore(Arrays.asList("§cAmount Broken: §f§n" + i));
		meta.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
		item.setItemMeta(meta);
		return item;
	}

	private int getAmountOfPlayer(String crop, UUID uuid) {
		CropTopPlayer p = CropTopPlayer.getCropTopPlayer(uuid);
		return p.getStats().get(crop.replace("_block", "").toUpperCase());
	}

	@EventHandler
	private void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof Top9Inventory) {
			e.setCancelled(true);
		}
	}

}
