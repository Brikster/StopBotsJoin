package ru.MrBrikster.StopBotsJoin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("[StopBotsJoin] ��� ������������ ������������ ��������� /" + cmd.getName().toLowerCase() + " reload");
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			PluginManager.reloadConfig();
			Main.getPlugin().reloadConfig();
			sender.sendMessage("[StopBotsJoin] ������������ �������������!");
			return true;
		}
		sender.sendMessage("[StopBotsJoin] ��� ������������ ������������ ��������� /sbj reload");
		return true;
	}

}
