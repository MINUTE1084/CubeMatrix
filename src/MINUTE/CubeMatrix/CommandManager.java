package MINUTE.CubeMatrix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager  implements CommandExecutor {
	public final Main main;
	private ItemRank itemRank = new ItemRank();
	
	public CommandManager(Main main_) {
		main = main_;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("cube")) {
			if (sender instanceof Player) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("set")) {	
						Player player = (Player)sender;
						itemRank.setRank(player, args);
					}
					if (args[0].equalsIgnoreCase("give")) {
						if (args.length > 1) {
							Player player = (Player)sender;
							int CubeAmount = 1;
							if (args.length > 2)
								CubeAmount = Integer.parseInt(args[2]);
							MinecraftUtility.giveCube(player, Integer.parseInt(args[1]), CubeAmount);
						}
						else {
							sender.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브 타입을 입력해주세요. (1 : 수상한 큐브 / 2 : 레드 큐브 / 3 : 블랙 큐브)");
						}
					}
					if (args[0].equalsIgnoreCase("clear")) {	
						Player player = (Player)sender;
						itemRank.clearRank(player);
					}
				}
				else {
					sender.sendMessage("\2476[\247eCubeMatrix\2476]");
					sender.sendMessage("\2476/cube \247f: \247a명령어를 확인합니다.");
					sender.sendMessage("\2476/cube \247bset \247b(아이템 이름) \247f: \247a왼손에 들고있는 아이템에 잠재능력을 부여합니다.");
					sender.sendMessage("\247a이름 미입력 시 기본 영문 이름으로 적용됩니다.");
					sender.sendMessage("\2476/cube \247bgive \247b(큐브 타입) \247f: \247a큐브를 지급받습니다.");
					sender.sendMessage("\247a큐브 타입\247f - \24771. 수상한 큐브 \247c 2. 레드 큐브 \2478 3. 블랙 큐브");
					sender.sendMessage("\2476/cube \247bclear \247f: \247a왼손에 들고있는 아이템에 잠재능력 및 등급을 초기화 합니다.");
				}
			}
		}
		return true;
	}
}
