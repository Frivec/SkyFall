package fr.frivec.commands;

import fr.frivec.SkyFall;
import fr.frivec.commands.dev.DevCommand;

public class CommandsManager {
	
	private SkyFall main;
	
	public CommandsManager(SkyFall main) {
		this.main = main;
		registerCommands();
	}
	
	void registerCommands() {
		main.getCommand("dev").setExecutor(new DevCommand());
	}
	
	public SkyFall getMain() {
		return main;
	}

}
