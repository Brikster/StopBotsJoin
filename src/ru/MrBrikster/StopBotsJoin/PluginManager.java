package ru.MrBrikster.StopBotsJoin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.MrBrikster.StopBotsJoin.connecters.BungeeConnecter;
import ru.MrBrikster.StopBotsJoin.connecters.LilyPadConnecter;

public class PluginManager{
	
	private static File file;
	private static FileConfiguration config;
	private static IPLogger logger;
	
	
	
	/* Другое */
	public static void sendServer(final Player player, String server) {
		switch(Settings.getSettings().getProxyMode()) {
		case LILYPAD:
			LilyPadConnecter.sendServer(player, server);
			break;
		case BUNGEE:
			BungeeConnecter.sendServer(player, server);
			break;
		}
	}
	
	public static void kick(Player player, boolean isBot) {
		if (isBot) {
			player.kickPlayer("§cБот-защита считает, что вы бот.");
		} else {
			player.kickPlayer("§cВы не прошли Анти-Бот проверку.");
		}
	}
	
	
	
	/* Запуск задач */
	public static void runTimeTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
			@Override
			public void run() {
				Settings.getSettings().getWorld().setTime(0);
			}
		}, 600L, 0);
	}
	
	public static void runMessageTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(Settings.getSettings().getMessage());
				}
			}
		}, 0L, 60L);
	}

	
	
	/* Управление AntiBotPlayer'ами */
	public static AntiBotPlayer getPlayer(Player p) {
		String name = p.getName();
		if (PluginManager.getConfig().getConfigurationSection(name) == null) {
			PluginManager.getConfig().set(name + ".checked", false);
			PluginManager.getConfig().set(name + ".bot", false);
			PluginManager.getConfig().set(name + ".errors", 0);
			saveConfig();
		}
		AntiBotPlayer abp = new AntiBotPlayer(name, 
				PluginManager.getConfig().getInt(name + ".errors"), 
				PluginManager.getConfig().getBoolean(name + ".bot"),
				PluginManager.getConfig().getBoolean(name + ".checked"));
		return abp;
	}
	

	/* Конфигурация */
	public static void reloadConfig() {	
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void saveConfig() {
		try {
			getConfig().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileConfiguration getConfig() {
		if (file == null) {
			file = new File(Main.getPlugin().getDataFolder(), "players.yml");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (config == null) {
			config = YamlConfiguration.loadConfiguration(file);
		}
		return config;
	}

	public static void loadLogger() {
		logger = new IPLogger();
	}
	
	public static IPLogger getLogger() {
		return logger;
	}
	
}
