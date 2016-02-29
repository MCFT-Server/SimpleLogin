package SimpleLogin.manage;

import java.util.HashMap;
import java.util.Map;

import SimpleLogin.DataBase;
import SimpleLogin.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

class Login {
	private Main plugin;
	private HashMap<CommandSender, Boolean> loginList;
	Login(Main plugin) {
		this.plugin = plugin;
		loginList = new HashMap<CommandSender, Boolean>();
	}
	public DataBase getDataBase() {
		return plugin.getDataBase();
	}
	public Manage getManage() {
		return plugin.getManage();
	}
	public void setLogin(CommandSender sender, boolean bool) {
		loginList.put(sender, bool);
		Map<String, String> v = (Map<String, String>) getDataBase().accountDB.get(sender.getName().toLowerCase());
		if (bool) {
			v.put("ip", ((Player)sender).getAddress());
			v.put("clientid", String.valueOf(getDataBase().getClientId((Player)sender)));
			getDataBase().message(sender, getDataBase().get("login-success"));
		}
	}
	public boolean isLogin(CommandSender player) {
		if (loginList.get(player) != null) {
			return loginList.get(player);
		} else {
			return false;
		}
	}
	public boolean checkPassword(CommandSender player, String password) {
		if(((Map<String, String>)getDataBase().accountDB.get(player.getName().toLowerCase())).get("password").equals(Encrypt.encryptData(password))) {
			return true;
		} else {
			return false;
		}
	}
	public boolean changePassword(String sender, String password) {
		if (!getManage().isRegister(sender)) {
			return false;
		}
		Map<String, String> v = (Map<String, String>) getDataBase().accountDB.get(sender.toLowerCase());
		v.put("password", Encrypt.encryptData(password));
		getDataBase().accountDB.put(sender.toLowerCase(), v);
		return true;
	}
}