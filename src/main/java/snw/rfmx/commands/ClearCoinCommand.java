package snw.rfmx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import snw.rfm.RunForMoney;
import snw.rfm.api.GameController;

public class ClearCoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        GameController controller = RunForMoney.getInstance().getGameController();
        if (controller == null) {
            sender.sendMessage(ChatColor.RED + "操作失败。游戏未在运行。");
        } else {
            controller.clearCoin();
            sender.sendMessage(ChatColor.GREEN + "操作成功。");
        }
        return true;
    }
}
