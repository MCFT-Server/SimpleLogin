package SimpleLogin;

import SimpleLogin.manage.Manage;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase{
	private EventListener listener;
	private DataBase dataBase;
	private Manage manage;
	
	public void onEnable() {
		listener = new EventListener(this);
		dataBase = new DataBase(this);
		manage = new Manage(this);
		
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	public void onDisable() {
		dataBase.save(true);
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			return listener.onCommand(sender, command, label, args);
		} catch (SecurityException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public DataBase getDataBase() {
		return dataBase;
	}
	public Manage getManage() {
		return manage;
	}
}