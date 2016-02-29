package SimpleLogin.manage;

import java.util.HashMap;

import SimpleLogin.DataBase;
import SimpleLogin.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

class Register {
	private Main plugin;
	Register(Main plugin) {
		this.plugin = plugin;
	}
	public DataBase getDataBase() {
		return plugin.getDataBase();
	}
	public boolean isRegister(String player) {
		if (getDataBase().accountDB.containsKey(player.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	public void setRegister(CommandSender player, String password) {
		HashMap<String, String> v1 = new HashMap<String, String>();
		v1.put("name", player.getName().toLowerCase());
		v1.put("password", Encrypt.encryptData(password));
		v1.put("ip", ((Player)player).getAddress());
		v1.put("clientid", String.valueOf(getDataBase().getClientId((Player)player)));
		getDataBase().accountDB.put(player.getName().toLowerCase(), v1);
		plugin.getManage().setLogin(player, true);
	}
	public boolean unRegister(String target) {
		if (isRegister(target.toLowerCase())) {
			getDataBase().accountDB.remove(target.toLowerCase());
			return true;
		}
		return false;
	}
}
