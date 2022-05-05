package snw.rfmx;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import snw.rfm.RunForMoney;
import snw.rfm.api.GameController;
import snw.rfm.game.TeamHolder;

import java.io.File;
import java.util.*;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        new CommandAPICommand("reverse")
                .withPermission(CommandPermission.OP)
                .executes(((sender, args) -> {
                    GameController controller = RunForMoney.getInstance().getGameController();
                    if (controller != null) {
                        controller.setCoinPerSecond(-controller.getCoinPerSecond());
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
                .withArguments(new MultiLiteralArgument("forceout", "exit", "respawn"))
                .executes((sender, args) -> {
                    forceout(sender, (Player) args[0], (String) args[1]);
                }).register();

        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.MANY_PLAYERS))
                .withArguments(new MultiLiteralArgument("forceout", "exit", "respawn"))
                .executes((sender, args) -> {
                    //noinspection unchecked
                    for (Player p : (Collection<Player>) args[0]) {
                        forceout(sender, p, (String) args[1]);
                    }
                }).register();

        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.ONE_PLAYER))
                .executes((sender, args) -> {
                    forceout(sender, (Player) args[0], "forceout");
                }).register();
                
        new CommandAPICommand("forceout")
                .withPermission(CommandPermission.OP)
                .withArguments(new EntitySelectorArgument("player", EntitySelectorArgument.EntitySelector.MANY_PLAYERS))
                .executes((sender, args) -> {
                    //noinspection unchecked
                    for (Player p : (Collection<Player>) args[0]) {
                        forceout(sender, p, "forceout");
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
                        throw CommandAPI.fail("提供的值无效。");
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
        new CommandAPICommand("rfmrandom")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {
                    rfmrandom(sender, "runner");
                }).register();
        new CommandAPICommand("rfmrandom")
                .withPermission(CommandPermission.OP)
                .withArguments(
                        new MultiLiteralArgument("runner","out","both")
                )
                .executes((sender, args) -> {
                    rfmrandom(sender, (String) args[0]);
                }).register();
    }

    @Override
    public void onDisable() {
        CommandAPI.unregister("forceout");
        CommandAPI.unregister("hunternomove");
        CommandAPI.unregister("setcoinpersecond");
        CommandAPI.unregister("addmoney");
        CommandAPI.unregister("rrt");
    }

    private static void rfmrandom(CommandSender sender, String type) throws WrapperCommandSyntaxException {
        List<String> region = new ArrayList<>();
        switch (type) {
            case "runner" -> region.addAll(TeamHolder.getInstance().getRunners());
            case "out" -> region.addAll(TeamHolder.getInstance().getGiveUpPlayers());
            case "both" -> {
                region.addAll(TeamHolder.getInstance().getRunners());
                region.addAll(TeamHolder.getInstance().getGiveUpPlayers());
            }
            default -> throw CommandAPI.fail("Impossible situation!");
        }
        String result = region.get(new Random().nextInt(region.size() - 1));
        sender.sendMessage(result);
    }
    
    private static void forceout(CommandSender sender, Player target, String type) throws WrapperCommandSyntaxException {
        GameController controller = RunForMoney.getInstance().getGameController();
        if (controller != null) {
            String text = switch (type) {
                case "exit" -> " 已弃权。";
                case "respawn" -> " 复活成功。";
                default -> " 被强制淘汰。";
            };
            text = ((Objects.equals(type, "respawn") ? ChatColor.GREEN : ChatColor.RED) +
                    (YamlConfiguration.loadConfiguration(new File(RunForMoney.getInstance().getDataFolder(), "nickname.yml"))
                            .getString(target.getName(), target.getName())  // another way to access
                            // nickname support
                            + text));

            controller.forceOut(target);
            Bukkit.broadcastMessage(text);
            sender.sendMessage(ChatColor.GREEN + "操作成功。");
        } else {
            throw CommandAPI.fail("操作失败。游戏并未运行。");
        }
    }
}
