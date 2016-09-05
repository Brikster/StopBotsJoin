package ru.MrBrikster.StopBotsJoin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventListener implements Listener{
	
	public static List<String> notBots = new ArrayList<>();


	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(final PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(e.getPlayer());
			e.getPlayer().hidePlayer(p);
		}
		e.setJoinMessage(null);
		
		AntiBotPlayer p = PluginManager.getPlayer(e.getPlayer());
		if (p.isBot()) {
			PluginManager.kick(Bukkit.getPlayer(p.getPlayer()), true);
			p = null;
			return;
		}
		if (p.isChecked()) {
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

				@Override
				public void run() {
					PluginManager.sendServer(e.getPlayer(), Settings.getSettings().getServer()); 
				}
				
			}, 1L);
			p = null;
			return;
		}
		p.runCheck();
		p = null;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.getTo().getPitch() != e.getFrom().getPitch() || e.getFrom().getYaw() != e.getTo().getYaw()) notBots.add(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if (!e.getPlayer().getName().matches("^[a-zA-Z0-9_]*")) {
			e.disallow(Result.KICK_OTHER, "§cВаш ник содержит запрещённые символы.");
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setDamage(0);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void weatherChange(WeatherChangeEvent event) {
		if(event.toWeatherState()) {
			World world = Settings.getSettings().getWorld();
			if(event.getWorld() == world) {
				event.setCancelled(true);
				world.setStorm(false);
				world.setThundering(false);
				world.setWeatherDuration(0);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.PHYSICAL)) {
			if (e.getClickedBlock().getType().equals(Material.STONE_PLATE) |
					e.getClickedBlock().getType().equals(Material.GOLD_PLATE) |
					e.getClickedBlock().getType().equals(Material.IRON_PLATE) |
					e.getClickedBlock().getType().equals(Material.WOOD_PLATE)) {
				PluginManager.getPlayer(e.getPlayer()).setIsBot(false);
				PluginManager.getPlayer(e.getPlayer()).setIsChecked(true);
				PluginManager.sendServer(e.getPlayer(), Settings.getSettings().getServer());
			}
		}
	}
	
}
