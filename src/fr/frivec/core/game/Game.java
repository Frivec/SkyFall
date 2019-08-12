package fr.frivec.core.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.frivec.core.maps.SfMap;

public class Game {
	
	private SfMap map;
	private List<Player> players;
	
	public Game(SfMap map) {
		this.map = map;
		this.players = new ArrayList<>();
	}
	
	public SfMap getMap() {
		return map;
	}
	
	public void setMap(SfMap map) {
		this.map = map;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
