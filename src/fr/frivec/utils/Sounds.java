package fr.frivec.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds{
	
    private Player player;
    
    public Sounds(Player p){
        player = p;
    }

	public void playSound(Sound s){
        player.playSound(player.getLocation(), s, 4, 4);
    }

}
