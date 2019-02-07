package MINUTE.CubeMatrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemLore {
	private ItemRank itemRank_ = new ItemRank();
	
	public ItemStack setLore(Player p, ItemStack items) {
		if (items.getType() != Material.AIR) {
			p.sendMessage("\2476[\247eCubeMatrix\2476] \247a잠재옵션을 재설정합니다.");
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100L, 2L);
			ItemMeta meta = items.getItemMeta();
			boolean wearitem = MinecraftUtility.isWearing(p);
			Random random = new Random();
			int itemRank = itemRank_.getRank(items);
			if (itemRank > 0) {
				
			int LoreCount = 0;
			int[] LoreAcc = new int[] {0,0}; // HP, 상태이상, 데미지, 방무, 방어력, 크확, 크뎀
			switch (itemRank) {
			case 1:
				LoreAcc = new int[] {20,40,55,60,100,-1,-1,-1};
				break;
			case 2:
				LoreAcc = new int[] {20,40,60,70,90,100,-1,-1};
				break;
			case 3:
				LoreAcc = new int[] {15,35,60,70,85,95,-1,100};
				break;
			case 4:
				LoreAcc = new int[] {15,35,60,70,80,85,95,100};
				break;
			}
			switch (random.nextInt(6)) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				LoreCount = 2;
				break;
			case 5:
				LoreCount = 3;
				break;
			}
			if (meta.getLore().size() == 3)
				LoreCount = 3;
		
			List<String> AbilityList = new ArrayList<String>();
			
			boolean Panelty = false;
			boolean[] hasBuff = new boolean[] {false,false,false,false};
			for (int i = 0; i < LoreCount; i++) {
				String currentLore = "";
				int abilitySet = random.nextInt(100);
				int PaneltyLore = random.nextInt(8);
				
				if (LoreAcc[0] > abilitySet) { // HP
					if (wearitem) {
						switch (random.nextInt(2)) {
						case 0:
							if (itemRank < 3)
								currentLore = ChatColor.GRAY + "HP : +" + String.valueOf(((1 + random.nextInt(2))) * itemRank);
							break;
						case 1:
							if (!Panelty) {
								Panelty = true;
								currentLore = ChatColor.GRAY + "HP : +" + String.valueOf(15 * itemRank) + "%";
							}
							else {
								if (PaneltyLore != 0)
									currentLore = ChatColor.GRAY + "HP : +" + String.valueOf(15 * (itemRank-1)) + "%";
								else {
									currentLore = ChatColor.GRAY + "HP : +" + String.valueOf(15 * itemRank) + "%";
								}
							}
							break;
						}
					}
				}
					
				else if (LoreAcc[1] > abilitySet) { // 상태이상
					if (!wearitem) {
					switch (random.nextInt(4)) {
					case 0:
						if (!hasBuff[0]) {
							currentLore = ChatColor.GRAY + "공격 시 " + String.valueOf(5 * itemRank) + "% 확률로 구속 " + String.valueOf(1 + random.nextInt(itemRank)) + "단계";
							hasBuff[0] = true;
						}
						break;
					case 1:
						if (!hasBuff[1]) {
							currentLore = ChatColor.GRAY + "공격 시 " + String.valueOf(5 * itemRank) + "% 확률로 혼란 " + String.valueOf(1 + random.nextInt(itemRank)) + "단계";
							hasBuff[1] = true;
						}
						break;
					case 2:
						if (!hasBuff[2]) {
							currentLore = ChatColor.GRAY + "공격 시 " + String.valueOf(5 * itemRank) + "% 확률로 독 " + String.valueOf(1 + random.nextInt(itemRank)) + "단계";
							hasBuff[2] = true;
						}
						break;
					case 3:
						if (!hasBuff[3]) {
							currentLore = ChatColor.GRAY + "공격 시 " + String.valueOf(5 * itemRank) + "% 확률로 실명 " + String.valueOf(1 + random.nextInt(itemRank)) + "단계";
							hasBuff[3] = true;
						}
						break;
					}
					}
				}
					
				else if (LoreAcc[2] > abilitySet) { // 데미지 증가
					switch (random.nextInt(2)) {
					case 0:
						if (!Panelty) {
							currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(itemRank);
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(itemRank-1);
							else {
								currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(itemRank);
							}
						}
						break;
					case 1:
						if (!Panelty) {
							Panelty = true;
							currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(15 * itemRank) + "%";
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(15 * (itemRank-1)) + "%";
							else {
								currentLore = ChatColor.GRAY + "데미지 : +" + String.valueOf(15 * itemRank) + "%";
							}
						}
						break;
					}
				}
					
				else if (LoreAcc[3] > abilitySet) { // 방어율 무시
					if (itemRank == 4) {
						currentLore = ChatColor.GRAY + "공격 시 적의 방어력 " + String.valueOf(30 + (5 * random.nextInt(3))) + "% 무시";
					}
					else {
						if (!Panelty) {
							Panelty = true;
							currentLore = ChatColor.GRAY + "공격 시 적의 방어력 " + String.valueOf(15 * ((itemRank + 1) / 2)) + "% 무시";
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "공격 시 적의 방어력 " + String.valueOf(15 * ((itemRank) / 2)) + "% 무시";
							else {
								currentLore = ChatColor.GRAY + "공격 시 적의 방어력 " + String.valueOf(15 * ((itemRank + 1) / 2)) + "% 무시";
							}
						}
					}

				}
					
				else if (LoreAcc[4] > abilitySet) { // 방어력
					if (wearitem) {
						if (!Panelty) {
							Panelty = true;
							currentLore = ChatColor.GRAY + "방어력 : +" + String.valueOf(10 * itemRank) + "%";
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "방어력 : +" + String.valueOf(10 * (itemRank-1)) + "%";
							else {
								currentLore = ChatColor.GRAY + "방어력 : +" + String.valueOf(10 * itemRank) + "%";
							}
						}
					}
				}
					
				else if (LoreAcc[5] > abilitySet) { // 크리티컬 확률
					currentLore = ChatColor.GRAY + "크리티컬 확률 : +" + String.valueOf(4 * itemRank) + "%";

				}
					
				else if (LoreAcc[6] > abilitySet) { // 크리티컬 데미지
					if (itemRank == 4) {
						if (!Panelty) {
							Panelty = true;
							currentLore = ChatColor.GRAY + "크리티컬 데미지 : +" + String.valueOf(10 * itemRank) + "%";
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "크리티컬 데미지 : +" + String.valueOf(10 * (itemRank-1)) + "%";
							else {
								currentLore = ChatColor.GRAY + "크리티컬 데미지 : +" + String.valueOf(10 * itemRank) + "%";
							}
						}
					}
				}
				
				else if (LoreAcc[7] > abilitySet) { // 피격 후 무적시간
					if (itemRank >= 3) {
						if (!Panelty) {
							Panelty = true;
							currentLore = ChatColor.GRAY + "피격 후 무적시간 : +" + String.valueOf(itemRank - 2);
						}
						else {
							if (PaneltyLore != 0)
								currentLore = ChatColor.GRAY + "피격 후 무적시간 : +" + String.valueOf(itemRank - 3);
							else {
								currentLore = ChatColor.GRAY + "피격 후 무적시간 : +" + String.valueOf(itemRank - 2);
							}
						}
					}
				}
				
				if (currentLore.contains(" 0%") || currentLore.equalsIgnoreCase("") || currentLore.contains("+0")) {
					i--;
					continue;
				}
				
				else {
					AbilityList.add(currentLore);
				}
			}
			AbilityList.sort(new MiniComparator());
			for (String strs : AbilityList) {
				p.sendMessage(strs);
			}
			meta.setLore(AbilityList);
			items.setItemMeta(meta);
			return items;
			}
			else {
				p.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 없는 아이템입니다.");
				return null;
			}
		}
		return null;
	}
}

class MiniComparator implements Comparator<String>{
	@Override
	public int compare(String a, String b) {
		String a_ = "";
		String b_ = "";
		
        for(int i = 0 ; i < a.length(); i ++)
        {    
            if(48 <= a.charAt(i) && a.charAt(i) <= 57)
                a_ += a.charAt(i);
        }
        
        for(int i = 0 ; i < b.length(); i ++)
        {    
            if(48 <= b.charAt(i) && b.charAt(i) <= 57)
                b_ += b.charAt(i);
        }
        
        int aValue = Integer.parseInt(a_);
        int bValue = Integer.parseInt(b_);

		if (aValue > bValue) {
			return -1;
		}
		else if (aValue < bValue) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
