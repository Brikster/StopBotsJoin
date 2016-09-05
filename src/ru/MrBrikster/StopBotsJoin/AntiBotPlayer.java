package ru.MrBrikster.StopBotsJoin;

import org.bukkit.Bukkit;

public class AntiBotPlayer {
	
	private boolean isBot;
	private int errors;
	private String player;
	private boolean isChecked;

	public AntiBotPlayer(String player, int errors, boolean isBot, boolean isChecked) {
		this.player = player;
		this.errors = errors;
		this.isBot = isBot;	
		this.isChecked = isChecked;
	}
	
	public boolean isBot() {
		return isBot;
	}
	
	public void setIsBot(boolean isBot) {
		this.isBot = isBot;
		PluginManager.getConfig().set(player + ".bot", isBot);
		PluginManager.saveConfig();
	}
	
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
		PluginManager.getConfig().set(player + ".checked", isChecked);
		PluginManager.saveConfig();
		PluginManager.getLogger().logIP(Bukkit.getPlayer(player));
	}
	
	public int getErrors() {
		return errors;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void runCheck() {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				int maxerrors = Settings.getSettings().getMaxErrors();
				if (maxerrors == -1) return;
				
				if (Bukkit.getPlayer(player) == null) return;
				errors++;
				PluginManager.getConfig().set(player + ".errors", errors);
				PluginManager.saveConfig();
				if (Settings.getSettings().isKickEnabled()) {
					PluginManager.kick(Bukkit.getPlayer(player), false);
				}
				if (errors >= maxerrors || EventListener.notBots.contains(player)) {
					setIsBot(true);
					setIsChecked(true);
				}
			}
		}, 600L);
	}
	
}
