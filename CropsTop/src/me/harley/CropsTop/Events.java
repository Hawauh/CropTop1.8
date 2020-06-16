package me.harley.CropsTop;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Events implements Listener {

	@EventHandler
	public void onCropBreak(BlockBreakEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		CropTopPlayer p = CropTopPlayer.getCropTopPlayer(uuid);
		Block block = e.getBlock();
		switch (block.getType()) {
		case CROPS:
			if (block.getData() == 7)
				p.getStats().put("WHEAT", p.getStats().get("WHEAT") + 1);
			break;
		case CACTUS:
			p.getStats().put("CACTUS", p.getStats().get("CACTUS") + getStack(block));
			break;
		case SUGAR_CANE_BLOCK:
			p.getStats().put("SUGAR_CANE", p.getStats().get("SUGAR_CANE") + getStack(block));
			break;
		case PUMPKIN:
			p.getStats().put("PUMPKIN", p.getStats().get("PUMPKIN") + 1);
			break;
		case MELON_BLOCK:
			p.getStats().put("MELON", p.getStats().get("MELON") + 1);
			break;
		case CARROT:
			if (block.getData() == 7)
				p.getStats().put("CARROT", p.getStats().get("CARROT") + 1);
			break;
		case POTATO:
			if (block.getData() == 7)
				p.getStats().put("POTATO", p.getStats().get("POTATO") + 1);
			break;
		default:
			break;
		}
	}

	public int getStack(Block b) {
		int amount = 0;
		if (b.getType() == Material.SUGAR_CANE || b.getType() == Material.CACTUS) {
			for (int i = 0; i < b.getWorld().getHighestBlockYAt(b.getLocation()); i++) {
				Block block = b.getRelative(BlockFace.UP, i);
				if (block.getType() == b.getType()) {
					amount++;
				}
			}
		}
		return amount;
	}
}
