package me.harley.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCreator {

	@Deprecated
	public ItemStack itemFromName(String name) {
		ItemStack item = getPlayerSkullItem();

		return itemWithName(item, name);
	}
	@Deprecated
	public static ItemStack itemWithName(ItemStack item, String name) {
		
		return Bukkit.getUnsafe().modifyItemStack(item,
				"{SkullOwner:\"" + name + "\"}"
		);
	}

	public ItemStack itemFromUuid(UUID id) {
		ItemStack item = getPlayerSkullItem();

		return itemWithUuid(item, id);
	}

	public static ItemStack itemWithUuid(ItemStack item, UUID id) {

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(Bukkit.getOfflinePlayer(id).getName());
		item.setItemMeta(meta);

		return item;
	}

	private static boolean newerApi() {
		try {

			Material.valueOf("PLAYER_HEAD");
			return true;

		} catch (IllegalArgumentException e) { // If PLAYER_HEAD doesn't exist
			return false;
		}
	}

	private static ItemStack getPlayerSkullItem() {
		if (newerApi()) {
			return new ItemStack(Material.valueOf("PLAYER_HEAD"));
		} else {
			return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
		}
	}

}
