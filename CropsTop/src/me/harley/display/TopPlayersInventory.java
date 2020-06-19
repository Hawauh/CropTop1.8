package me.harley.display;

import java.util.ArrayList;
import java.util.List;
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

import me.harley.manager.CropTopPlayer;
import me.harley.manager.ManageData;
import me.harley.utils.ChatManager;
import me.harley.utils.SkullCreator;

public class TopPlayersInventory implements InventoryHolder, Listener {

	private Inventory inv;
	private ChatManager chatManager = new ChatManager();
	private ManageData data = ManageData.inst();

	private final String GUI_TITLE = chatManager.getTranslatedString("top-players-inventory.title");
	private final String PLACEMENT_ITEM_NAME = chatManager.getTranslatedString("top-players-inventory.item-name");
	private final List<String> PLACEMENT_ITEM_LORE = chatManager.getTranslatedList("top-players-inventory.item-lore");

	public void createInventory(Player p, String crop) {
		Inventory inv = Bukkit.createInventory(this, 9, GUI_TITLE.replace("{crop}", crop));
		
		for (int i = 0; i < data.getTop().size(); i++) {
			inv.setItem(i, getPlacementItem(data.getTop().get(i).getUuid().toString(),
				crop, (i + 1)));
		}

		this.inv = inv;
		p.openInventory(inv);
	}

	public Inventory getInventory() {
		return inv;
	}

	private ItemStack getPlacementItem(String uuid, String crop, int place) {
		ItemStack item = new SkullCreator().itemFromUuid(UUID.fromString(uuid));
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		String nameFromUuid = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
		CropTopPlayer cropTopPlayer = new CropTopPlayer().fromUuid(UUID.fromString(uuid));
		int brokenCount = cropTopPlayer.getStats().get(crop);
		meta.setDisplayName(PLACEMENT_ITEM_NAME.replace("{player}", nameFromUuid).replace("{place}", place + "")
				.replace("{broken}", brokenCount + ""));
		List<String> lore = new ArrayList<>();
		for (String loreLines : PLACEMENT_ITEM_LORE) {
			lore.add(loreLines.replace("{broken}", brokenCount + "").replace("{place}", place + "").replace("{player}",
					nameFromUuid));

		}
		meta.setLore(lore);
		meta.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
		item.setItemMeta(meta);
		return item;
	}

	@EventHandler
	private void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof TopPlayersInventory) {
			e.setCancelled(true);
		}
	}

}
