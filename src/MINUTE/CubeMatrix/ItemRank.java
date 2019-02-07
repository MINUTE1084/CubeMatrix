package MINUTE.CubeMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRank {
	Random random = new Random();
	public void setRank(Player p, String[] s) {
		if (p.getInventory().getItemInOffHand().getType() != Material.AIR) {
		ItemStack item = p.getInventory().getItemInOffHand();
		ItemMeta meta = item.getItemMeta();
		if (getRank(item) != 0) {
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 이미 부여되어 있습니다.");
			return;
		}
		List<String> AbilityList = new ArrayList<String>();
		AbilityList.add("\2477큐브를 사용하여 잠재옵션을 설정할 수 있습니다.");
		
		String str = new String();
		if (s.length > 1) {	
			for (int i = 1; i < s.length; i++) {
				s[i] = s[i].replaceAll("&", "\247");
				str += s[i];
				if (i+1 != s.length)
					str += " ";
			}
		}
		else {
			str = item.getItemMeta().hasDisplayName() ? ChatColor.stripColor(item.getItemMeta().getDisplayName()) : ChatColor.stripColor(item.getType().name().replace("_", " ").toLowerCase());
		}
		
		int randoms = random.nextInt(10000);
		if (randoms < 50) {
			meta.setDisplayName(ChatColor.GREEN + str);
			meta.setUnbreakable(true);
			meta.setLore(AbilityList);
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247a레전더리 등급이 감지되었습니다.");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		else if (randoms < 200){
			meta.setDisplayName(ChatColor.GOLD + str);
			meta.setUnbreakable(true);
			meta.setLore(AbilityList);
			p.sendMessage("\2476[\247eCubeMatrix\2476] \2476유니크 등급이 감지되었습니다.");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		else if (randoms < 476){
			meta.setDisplayName(ChatColor.DARK_PURPLE + str);
			meta.setUnbreakable(true);
			meta.setLore(AbilityList);
			p.sendMessage("\2476[\247eCubeMatrix\2476] \2475에픽 등급이 감지되었습니다.");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		else if (randoms < 7500){
			meta.setDisplayName(ChatColor.AQUA + str);
			meta.setUnbreakable(true);
			meta.setLore(AbilityList);
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247b레어 등급이 감지되었습니다.");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		else {
			meta.setDisplayName(ChatColor.RESET + str);
			p.sendMessage("\2476[\247eCubeMatrix\2476] \2477잠재옵션 부여에 실패하였습니다.");
			
		}

		item.setItemMeta(meta);
		p.getInventory().setItemInOffHand(item);	
		}
		else {
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템을 지정된 슬롯에 들고있지 않습니다.");
		}
	}
	
	public int getRank(ItemStack items) {
		if (items.getType() != Material.AIR) {
		ItemMeta meta = items.getItemMeta();
		if (meta.hasDisplayName()) {
			String str = meta.getDisplayName().substring(0, 2);
			if (str.contains("" + ChatColor.AQUA)) {
				return 1;
			}
			else if (str.contains("" + ChatColor.DARK_PURPLE)) {
				return 2;
			}
			else if (str.contains("" + ChatColor.GOLD)) {
				return 3;
			}
			else if (str.contains("" + ChatColor.GREEN)) {
				return 4;
			}
			else {
				return 0;
			}
		}
		}
		return 0;
	}
	
	public ItemStack increaseRank(Player p, ItemStack items, int cubeType) {
		if (items.getType() != Material.AIR) {
		ItemMeta meta = items.getItemMeta();
		int randoms = 0;
		int[] RankAcc = new int[] {0,0}; // HP, 상태이상, 데미지, 방무, 방어력, 크확, 크뎀
		switch (cubeType) {
		case 1: // 수상
			RankAcc = new int[] {35,-1,-1};
			break;
		case 2: // 레드
			RankAcc = new int[] {60,20,6};
			break;
		case 3: // 블랙
			RankAcc = new int[] {150,35,14};
			break;
		}
		randoms = random.nextInt(1000);
		switch(getRank(items)) {
		case 1:
			if (randoms < RankAcc[0]) {
				String str = ChatColor.DARK_PURPLE + ChatColor.stripColor(meta.getDisplayName());
				p.sendMessage("\2476[\247eCubeMatrix\2476] \2475에픽 등급이 감지되었습니다.");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100L, 1L);
				meta.setDisplayName(str);
			}
			break;
		case 2:
			if (randoms < RankAcc[1]) {
				String str = ChatColor.GOLD + ChatColor.stripColor(meta.getDisplayName());
				p.sendMessage("\2476[\247eCubeMatrix\2476] \2476유니크 등급이 감지되었습니다.");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100L, 1L);
				meta.setDisplayName(str);
			}
			break;
		case 3:
			if (randoms < RankAcc[2]) {
				String str = ChatColor.GREEN + ChatColor.stripColor(meta.getDisplayName());
				p.sendMessage("\2476[\247eCubeMatrix\2476] \247a레전더리 등급이 감지되었습니다.");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100L, 1L);
				meta.setDisplayName(str);
			}
			break;	
		}
		if (!meta.isUnbreakable())
			meta.setUnbreakable(true);
		items.setItemMeta(meta);
		return items;
		}
		else {
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템을 지정된 슬롯에 들고있지 않습니다.");
		}
		return null;
	}
	
	public void clearRank(Player p) {
		if (p.getInventory().getItemInOffHand().getType() != Material.AIR) {
				ItemStack item = p.getInventory().getItemInOffHand();
				ItemMeta meta = item.getItemMeta();
				if (getRank(item) == 0) {
					p.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 없는 아이템입니다.");
					return;
				}
				List<String> Lore = new ArrayList<String>();
				Lore.clear();
				
				String str = new String();
				str = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", " ").toLowerCase();
				
				meta.setDisplayName(ChatColor.stripColor(str));
				
				meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
				meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				
				meta.setLore(Lore);
				item.setItemMeta(meta);
				
				p.getInventory().setItemInOffHand(item);
				p.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션을 초기화했습니다.");
			}
			else {
				p.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템을 지정된 슬롯에 들고있지 않습니다.");
			}
	}
}
