package ru.MrBrikster.StopBotsJoin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static Plugin plugin;

	public void onEnable() {
		/* В следующих версиях
		if (!SecrurityManager.isActivated()) {
			Bukkit.getConsoleSender().sendMessage("[StopBotsJoin] Плагин не активирован. Введите верный ключ.");
			return;
		}
		*/
		plugin = this;
		saveDefaultConfig();
		loadConfig();
		PluginManager.runTimeTask();
		PluginManager.runMessageTask();
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getCommand("sbj").setExecutor(new CommandManager());
		getCommand("stopbotsjoin").setExecutor(new CommandManager());
		PluginManager.loadLogger();
	}
	
	private void loadConfig() {
		if(!getConfig().getString("version").equals("2.2")) {
			File file = new File(getDataFolder(), "config.yml");
			file.renameTo(new File(getDataFolder(), "config.yml." + System.currentTimeMillis()));
			saveDefaultConfig();
		}
		
		// Загрузка настроек
		Settings.loadSettings(
				Bukkit.getWorld(getPlugin().getConfig().getString("world")),
				getPlugin().getConfig().getInt("maxerrors"), 
				getPlugin().getConfig().getBoolean("enablekick"), 
				getPlugin().getConfig().getString("message"), 
				ProxyMode.valueOf(getPlugin().getConfig().getString("proxy")), 
				getPlugin().getConfig().getString("server"));
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

}
