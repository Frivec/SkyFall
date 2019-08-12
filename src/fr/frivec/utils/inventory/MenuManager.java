package fr.frivec.utils.inventory;

import fr.frivec.utils.inventory.abstracts.VirtualMenuManager;

public class MenuManager extends VirtualMenuManager {
	
	public MenuManager() {}
	
	@Override
	public void registerVirtualMenus() {
		registerVirtualMenu(1, new KitMenu());
	}

}
