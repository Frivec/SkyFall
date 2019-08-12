package fr.frivec.core.runnables;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.frivec.SkyFall;
import fr.frivec.core.game.States;
import fr.frivec.utils.Sounds;
import fr.frivec.utils.creators.NMSUtils;

public class NoDamageRunnable extends BukkitRunnable {
	
	private static int timer = 30;
	public static boolean start = false;
	
	public NoDamageRunnable() {}
	
	@Override
	public void run() {
		
		if(timer == 0) {
			
			for(Player all : SkyFall.getInstance().getGame().getPlayers()) {
				NMSUtils.sendActionBar(all, "§6Vous êtes maintenant sensibles aux dégats !");
				new Sounds(all).playSound(Sound.ENTITY_ZOMBIE_AMBIENT);
			}
			
			States.setState(States.GAME);
			this.cancel();
			
		}
		
		timer--;
		
	}

}
