package fr.frivec.core.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import fr.frivec.utils.creators.TeamTagsManager;

public enum Teams {
	
	WAITPLAYERS("2Players", "§f", ""),
	PLAYERS("1Players", "§4✘ §7", " §4✘"),
	SPEC("2Specs", "§8[§7Spec§8] ", "");
	
	private String name; 
	private String prefix;
	private String suffix;
	
	public static Map<Teams, List<Player>> teamsMap = new HashMap<>();
	
	private Teams(String name, String prefix, String suffix) { 
		this.name = name;
		this.prefix = prefix;
		this.suffix = suffix;
	} 
	
	static {
		
		for(Teams teams : values())
			teamsMap.put(teams, new ArrayList<>());
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public static Teams getPlayerTeam(Player player) {
		
		Teams teams = null;
		
		for(Entry<Teams, List<Player>> entrys : teamsMap.entrySet())
			if(entrys.getValue().contains(player))
				teams = entrys.getKey();
		
		return teams;
	}
	
	public static void setPlayerTeam(Player player, Teams teams) {
		teamsMap.get(teams).add(player);
		TeamTagsManager.setNameTag(player, teams.getName(), teams.getPrefix(), teams.getSuffix());
	}
	
	public static void removePlayerTeam(Player player) {
		
		for(Entry<Teams, List<Player>> entrys : teamsMap.entrySet())
			if(entrys.getValue().contains(player))
				entrys.getValue().remove(player);
		
		TeamTagsManager.setNameTag(player, "9", "§f", "");
		
	}

}
