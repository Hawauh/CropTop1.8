package me.harley.inventory;

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

import me.harley.CropsTop.CropsTop;
import me.harley.CropsTop.SetupData;

public class CropCatergoryInventory implements InventoryHolder, Listener {
	
	private CropsTop cropstop = CropsTop.getPlugin(CropsTop.class);
	private String guiName = cropstop.getConfig().getString("gui-name").replace("&", "§");
	private Inventory inv;
	
	public void createInventory(Player p) {
		final Inventory inv;
		inv = Bukkit.createInventory(this, 9, guiName);
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1));
		inv.setItem(1, getFoodItem(p, "Sugar_cane"));
		inv.setItem(2, getFoodItem(p, "Cactus"));
		inv.setItem(3, getFoodItem(p, "Wheat"));
		inv.setItem(4, getFoodItem(p, "Pumpkin"));
		inv.setItem(5, getFoodItem(p, "Melon"));
		inv.setItem(6, getFoodItem(p, "Carrot_ITEM"));
		inv.setItem(7, getFoodItem(p, "Potato_ITEM"));
		this.inv = inv;
		p.openInventory(inv);
	}

	public Inventory getInventory() {
		return inv;
	}

	public ItemStack getFoodItem(Player p, String m) {
		ItemStack item = new ItemStack(Material.valueOf(m.toUpperCase()));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(cropstop.getConfig().getString("gui-item-name").replace("&", "§").replace("{crop}", m)
				.replace("ITEM", "").replace("_", "").replace("BLOCK", ""));
		List<String> lore = new ArrayList<String>();
		for (String lores : cropstop.getConfig().getStringList("gui-item-lore")) {
			lore.add(lores.replace("&", "§")
					.replace("{broken}",
							new SetupData().getBroken(p.getUniqueId().toString(), m.toLowerCase().replace("_item", "").replace("cane", "cane_block"))
									+ "")
					.replace("{crop}", m).replace("ITEM", ""));
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	@EventHandler
	public void onCropSelect(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getHolder() instanceof CropCatergoryInventory) {
			e.setCancelled(true);
			Material m = e.getCurrentItem().getType();
			String cropString = m.toString().replace("CANE", "CANE_BLOCK").replace("_ITEM", "").toLowerCase();
			new Top9Inventory().createInventory(p, cropString);
		}
	}

}
