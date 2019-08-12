package fr.frivec.utils.inventory.abstracts;

import java.util.HashMap;
import java.util.Map;

public abstract class VirtualMenuManager {
	
	protected Map<Integer, VirtualMenu> virtualsMenus;
	
	public VirtualMenuManager() {
		this.virtualsMenus = new HashMap<>();
		registerVirtualMenus();
	}
	
	public abstract void registerVirtualMenus();
	
	public VirtualMenu getVirtualMenu(int id) {
		return this.virtualsMenus.get(id);
	}
	
	public void registerVirtualMenu(int id, VirtualMenu virtualMenu) {
		this.virtualsMenus.put(id, virtualMenu);
	}

}
