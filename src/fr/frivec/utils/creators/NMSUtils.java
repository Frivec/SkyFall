package fr.frivec.utils.creators;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class NMSUtils implements Listener {
	
	public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks){
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
        PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        sendPacket(player, p);
        sendPacket(player, p2);
        sendTime(player, ticks);
	}

    private static void sendTime(Player player, int ticks){
        PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
    }

    public static void sendActionBar(Player player, String message){
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        sendPacket(player, ppoc);
    }
    
    public static void sendTab(Player p, String head, String foot){
    
    	IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', head) + "\"}");
    	IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', foot) + "\"}");
    	PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
    	
    	try { 
    		Field headerField = packet.getClass().getDeclaredField("a");
    		headerField.setAccessible(true);
    		headerField.set(packet, header);
    		headerField.setAccessible(!headerField.isAccessible());
    		Field footerField = packet.getClass().getDeclaredField("b");footerField.setAccessible(true);
    		footerField.set(packet, footer);
    		footerField.setAccessible(!footerField.isAccessible());
    	} catch (SecurityException e) {
    		e.printStackTrace();
    	} catch (NoSuchFieldException e) {
    		e.printStackTrace();
    	} catch (IllegalArgumentException e) {
    		e.printStackTrace();
    	} catch (IllegalAccessException e) {
    		e.printStackTrace(); }
    	sendPacket(p, packet); 
    }
    
    public static void spawnParticles(Location location, Player player, EnumParticle enumParticle) {
    	
    	PacketPlayOutWorldParticles packetParticles = new PacketPlayOutWorldParticles(enumParticle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 1, 0);
    	
    	sendPacket(player, packetParticles);
    	
    }
    
    public static void sendPacket(Player p, Packet<?> packet) { 
    	((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet); 
    }

}
