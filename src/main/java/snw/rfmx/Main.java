package snw.rfmx;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import snw.rfm.RunForMoney;
import snw.rfm.api.GameController;

import java.util.Collection;
import java.util.stream.Collectors;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        new CommandAPICommand("reverse")
                .withPermission(CommandPermission.OP)
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        controller.setCoinPerSecond(controller.getCoinPerSecond());
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();

        new CommandAPICommand("clearcoin")
                .withPermission(CommandPermission.OP)
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        controller.clearCoin();
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();

        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.ONE_PLAYER))
                .withArguments(new MultiLiteralArgument("forceout", "exit"))
                .executes((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        Bukkit.broadcastMessage(ChatColor.RED + ((Player) args[0])
                                .getName() + (((String) args[1]).equalsIgnoreCase("forceout") ? " 被强制淘汰。" : " 已弃权。"));
                        controller.forceOut((Player) args[0]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                }).register();

        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.ONE_PLAYER))
                .executes((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        Bukkit.broadcastMessage(ChatColor.RED + ((Player) args[0])
                                .getName() + "被强制淘汰。");
                        controller.forceOut((Player) args[0]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                }).register();
                
        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.MANY_PLAYERS))
                .executes((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        @SuppressWarnings("unchecked")
                        Collection<Player> players = (Collection<Player>) args[0];
                        Bukkit.broadcastMessage(ChatColor.RED + players.stream().map(HumanEntity::getName).collect(Collectors.joining(", "))
                                + "被强制淘汰。");
                        controller.forceOut((Player) args[0]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                }).register();

        new CommandAPICommand("hunternomove")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("time"))
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        controller.setHunterNoMoveTime((int) args[0]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();

        new CommandAPICommand("setcoinpersecond")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("coin"))
                .executes(((sender, args) -> {
                    int coin = (int) args[0];
                    if (coin == 0) {
                        CommandAPI.fail("提供的值无效。");
                    } else {
                        GameController controller = RunForMoney.getInstance().getGameController();
                        if (controller != null) {
                            controller.setHunterNoMoveTime((int) args[0]);
                            sender.sendMessage(ChatColor.GREEN + "操作成功。");
                        } else {
                            sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                        }
                    }
                })).register();

        new CommandAPICommand("addmoney")
                .withPermission(CommandPermission.OP)
                .withArguments(new PlayerArgument("player"))
                .withArguments(new DoubleArgument("coin"))
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        RunForMoney.getInstance().getGameController().addMoney((Player) args[0], (double) args[1]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();

        new CommandAPICommand("rrt")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("time", 1))
                .withArguments(new BooleanArgument("addCoin"))
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        RunForMoney.getInstance().getGameController().removeRemainingTime((int) args[0], (boolean) args[1]);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();

        new CommandAPICommand("rrt")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("time", 1))
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        RunForMoney.getInstance().getGameController().removeRemainingTime((int) args[0], true);
                        sender.sendMessage(ChatColor.GREEN + "操作成功。");
                    } else {
                        sender.sendMessage(ChatColor.RED + "操作失败。游戏并未运行。");
                    }
                })).register();
    }

    @Override
    public void onDisable() {
        CommandAPI.unregister("forceout");
        CommandAPI.unregister("hunternomove");
        CommandAPI.unregister("setcoinpersecond");
        CommandAPI.unregister("addmoney");
        CommandAPI.unregister("rrt");
    }
}
