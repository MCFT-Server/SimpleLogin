package SimpleLogin;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class DataBase {
	public Main plugin;
	public Config messages, config;
	public LinkedHashMap<String, Object> accountDB;
	public final int m_version = 2;
	
	DataBase(Main plugin) {
		this.plugin = plugin;
		
		plugin.getDataFolder().mkdirs();
		initMessage();
		initDB();
		registerCommands();
	}
	public long getClientId(Player player) {
		/*
		Class<? extends Player> reflect =  player.getClass();
		Field var;
		long clientId = 0;
		try {
			var = reflect.getDeclaredField("randomClientId");
			var.setAccessible(true);
			try {
				clientId = var.getLong(player);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return clientId;
		*/
		return player.getClientId();
	}
	public void initMessage() {
		plugin.saveResource("messages.yml");
		messages = new Config(this.plugin.getDataFolder() + "/messages.yml", Config.YAML);
		updateMessage();
	}
	public void updateMessage() {
		if (messages.get("m_version", 1) < m_version) {
			this.plugin.saveResource("messages.yml", true);
			messages = new Config(this.plugin.getDataFolder() + "/messages.yml", Config.YAML);
		}
	}
	public void initDB() {
		accountDB = (LinkedHashMap<String, Object>) (new Config(this.plugin.getDataFolder() + "/accountDB.json", Config.JSON)).getAll();
		config = new Config(
				plugin.getDataFolder() + "/config.yml",
				Config.YAML,
				new LinkedHashMap<String, Object>(){
					{
						put("allow-subaccount", false);
					}
				});
	}
	public void save() {
		Config accountDB = new Config(plugin.getDataFolder()+ "/accountDB.json", Config.JSON);
		accountDB.setAll(this.accountDB);
		accountDB.save();
		config.save();
	}
	public void registerCommands() {
		registerCommand(get("command-login"), get("command-login-description"), get("command-login-usage"), "simplelogin.commands.login");
		registerCommand(get("command-register"), get("command-register-description"), get("command-register-usage"), "simplelogin.commands.register");
		registerCommand(get("command-manage"), get("command-manage-description"), get("command-manage-usage"), "simplelogin.commands.manage");
	}
	public void registerCommand(String name, String description, String usage, String permission) {
		SimpleCommandMap commandMap = this.plugin.getServer().getCommandMap();
		PluginCommand<Main> command = new PluginCommand<Main>(name, plugin);
		command.setDescription(description);
		command.setUsage(usage);
		command.setPermission(permission);
		commandMap.register(name, command);
	}
	public String get(String key) {
		return this.messages.get(this.messages.get("default-language", "kor") + "-" + key, "default-value");
	}
	public void alert(CommandSender player, String message) {
		player.sendMessage(TextFormat.RED + get("default-prefix") + " " + message);
	}
	public void message(CommandSender player, String message) {
		player.sendMessage(TextFormat.BLUE + get("default-prefix") + " " + message);
	}
	public String getLastLoginIp(String player) {
		return ((Map<String, String>) accountDB.get(player.toLowerCase())).get("ip");
	}
	public String getLastLoginIp(Player player) {
		return getLastLoginIp(player.getName());
	}
}
