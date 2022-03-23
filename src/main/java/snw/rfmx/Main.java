package snw.rfmx;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        InGameCommandHandler igch = new InGameCommandHandler();

        registerCommand("clearcoin", igch);
        registerCommand("hunternomove", igch);
        registerCommand("setcoinpersecond", igch);
        registerCommand("rrt", igch);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommand(@NotNull String cmdName, @NotNull CommandExecutor executor) {
        PluginCommand cmd = getCommand(cmdName);
        if (cmd == null) {
            getLogger().severe("命令 " + cmdName + " 注册失败。插件无法加载。");
            throw new NullPointerException();
        } else {
            cmd.setExecutor(executor);
        }
    }
}
