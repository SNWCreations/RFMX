package snw.rfmx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import snw.rfm.RunForMoney;
import snw.rfm.api.GameController;

public final class SetCoinPerSecondCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "操作失败。未提供参数。");
            return false;
        }
        GameController controller = RunForMoney.getInstance().getGameController();
        if (controller == null) {
            sender.sendMessage(ChatColor.RED + "操作失败。游戏未在运行。");
        } else {
            try {
                controller.setCoinPerSecond(Integer.parseInt(args[0]));
                sender.sendMessage(ChatColor.RED + "操作成功。");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "操作失败。提供的值不是数字。");
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "操作失败。提供的值不能为 0 。");
            }
        }
        return true;
    }
}
