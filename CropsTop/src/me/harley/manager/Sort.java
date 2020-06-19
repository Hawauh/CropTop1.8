package me.harley.manager;

import java.util.Comparator;

class Sort implements Comparator<CropTopPlayer> {
	public int compare(CropTopPlayer a, CropTopPlayer b) {
		return a.getStats().get("SUGAR_CANE") - b.getStats().get("SUGAR_CANE");
	}
}
