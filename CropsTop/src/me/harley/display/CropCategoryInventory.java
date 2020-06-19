package me.harley.display;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.harley.cropstop.CropsTop;
import me.harley.manager.CropTopPlayer;
import me.harley.utils.ChatManager;

public class CropCategoryInventory implements InventoryHolder, Listener {

	private CropsTop main = CropsTop.getPlugin(CropsTop.class);
	private Inventory inv;
	private ChatManager chatManager = new ChatManager();
	private final String GUI_NAME = chatManager.getTranslatedString("main-gui.title");
	private final List<String> GUI_LORE = chatManager.getTranslatedList("main-gui.item-lore");
	private final boolean USING_GUI = main.getConfig().getBoolean("use-gui");

	public void createInventory(Player p) {
		final Inventory inv;
		inv = Bukkit.createInventory(this, 9, GUI_NAME);
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1));
		inv.setItem(1, getFoodItem(p, "Sugar Cane", "SUGAR_CANE"));
		inv.setItem(2, getFoodItem(p, "Cactus", "CACTUS"));
		inv.setItem(3, getFoodItem(p, "Wheat", "WHEAT"));
		inv.setItem(4, getFoodItem(p, "Pumpkin", "PUMPKIN"));
		inv.setItem(5, getFoodItem(p, "Melon", "MELON"));
		inv.setItem(6, getFoodItem(p, "Carrot", "CARROT_ITEM"));
		inv.setItem(7, getFoodItem(p, "Potato", "POTATO_ITEM"));
		this.inv = inv;
		p.openInventory(inv);
	}

	public Inventory getInventory() {
		return inv;
	}

	private ItemStack getFoodItem(Player p, String cropName, String cropMaterial) {
		CropTopPlayer cropTopPlayer = new CropTopPlayer().fromUuid(p.getUniqueId());
		ItemStack item = new ItemStack(Material.valueOf(cropMaterial));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(cropName);
		List<String> lore = new ArrayList<String>();
		for (String lores : GUI_LORE) {
			lore.add(lores.replace("{crop}", cropName).replace("{broken}",
					cropTopPlayer.getStats().get(cropMaterial.replace("_ITEM", "")) + ""));
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	@EventHandler
	public void onCropSelect(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof CropCategoryInventory) {
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != p.getInventory()) {
				e.setCancelled(true);
				if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
					String crop = e.getCurrentItem().getType().toString().replace("_ITEM", "");
					if (USING_GUI) {
						new TopPlayersInventory().createInventory(p, crop);
					} else {
						new TopPlayersChat().sendTopMessage(crop, p);
					}
				}
			}
		}
	}

}
