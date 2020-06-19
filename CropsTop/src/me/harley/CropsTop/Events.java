package me.harley.cropstop;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.harley.manager.CropTopPlayer;

public class Events implements Listener {

	@EventHandler
	public void onCropBreak(BlockBreakEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		CropTopPlayer p = new CropTopPlayer().fromUuid(uuid);
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
			p.getStats().put("SUGAR_CANE", p.getStats().get("SUGAR_CANE") + getStack(block) + 1);
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

	private int getStack(Block block) {
		final Block top = block.getRelative(BlockFace.UP);
		if (top.getType() == block.getType())
			return getStack(top) + 1;
		return 0;
	}
}
