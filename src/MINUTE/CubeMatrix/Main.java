package MINUTE.CubeMatrix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("\2476[\247eCubeMatrix\2476] \2477v1.0.1 활성화 되었어요.");
		Bukkit.getConsoleSender().sendMessage("Made by MINUTE.");
        
		getCommand("cube").setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getServer().getPluginManager().registerEvents(new GUIManager(), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("\2476[\247eCubeMatrix\2476] \2477v1.0.1 비활성화 되었어요.");
		Bukkit.getConsoleSender().sendMessage("Made by MINUTE.");
	}
}
