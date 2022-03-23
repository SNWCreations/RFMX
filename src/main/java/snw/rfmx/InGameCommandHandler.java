package snw.rfmx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import snw.rfm.RunForMoney;
import snw.rfm.api.GameController;
import snw.rfm.game.TeamHolder;

public class InGameCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        GameController controller = RunForMoney.getInstance().getGameController();
        if (controller == null) {
            sender.sendMessage(ChatColor.RED + "操作失败。游戏未在运行。");
        } else {
            if (label.equalsIgnoreCase("hunternomove") || label.equalsIgnoreCase("setcoinpersecond")) {
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "参数不足。");
                    return false;
                }
                try {
                    if (label.equalsIgnoreCase("hunternomove")){
                        controller.setHunterNoMoveTime(Integer.parseInt(args[0]));
                    } else {
                        controller.setCoinPerSecond(Integer.parseInt(args[0]));
                    }
                    sender.sendMessage(ChatColor.RED + "操作成功。");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的值不是数字。");
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的数字无效。");
                }
            } else if (label.equalsIgnoreCase("addmoney")) {
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "参数不足。");
                    return false;
                }
                try {
                    controller.addMoney(args[0], Double.parseDouble(args[1]));
                    sender.sendMessage(ChatColor.RED + "操作成功。");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的值不是数字。");
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的数字无效。");
                }
            } else if (label.equalsIgnoreCase("clearcoin")) {
                controller.clearCoin();
            } else if (label.equalsIgnoreCase("rrt")) {
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "参数不足。");
                    return false;
                }
                try {
                    controller.removeRemainingTime(Integer.parseInt(args[0]), Boolean.parseBoolean(args[1]));
                    sender.sendMessage(ChatColor.RED + "操作成功。");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的值不是数字。");
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "操作失败。提供的数字无效。");
                }
            } else if (label.equalsIgnoreCase("forceout")) {
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "参数不足。");
                    return false;
                }
                Player p = Bukkit.getPlayerExact(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.RED + "操作失败。玩家不在线。");
                } else {
                    if (!TeamHolder.getInstance().isRunner(p)) {
                        sender.sendMessage(ChatColor.RED + "操作失败。玩家不是逃走队员。");
                    } else {
                        controller.forceOut(p);
                        sender.sendMessage(ChatColor.RED + "操作成功。");
                    }
                }
            }
        }
        return true;
    }
}
