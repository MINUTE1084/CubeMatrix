package MINUTE.CubeMatrix;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class GUIManager implements Listener{
	ItemStack Accept = new Wool(DyeColor.LIME).toItemStack(1);
	ItemStack Denied = new Wool(DyeColor.RED).toItemStack(1);
	ItemStack End = new Wool(DyeColor.BLACK).toItemStack(1);
	ItemStack Retey = new Wool(DyeColor.LIGHT_BLUE).toItemStack(1);
	ItemStack Notice = new Wool(DyeColor.BLACK).toItemStack(1);
	ItemLore Itemlore = new ItemLore();
	ItemRank Itemrank = new ItemRank();
	static boolean isReroll = false, selAfter = true;
	static List<ItemStack> items = new ArrayList<ItemStack>(); {
		ItemStack glass_1 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemStack glass_2 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemStack glass_3 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemStack BEDROCKs = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		
		ItemMeta AccMeta = Accept.getItemMeta();
		AccMeta.setDisplayName(ChatColor.GREEN + "잠재옵션 부여");
		List<String> AccLore = new ArrayList<String>();
		AccLore.add(ChatColor.GRAY + "잠재옵션을 부여합니다.");
		AccMeta.setLore(AccLore);
		Accept.setItemMeta(AccMeta);
		
		ItemMeta DenMeta = Accept.getItemMeta();
		DenMeta.setDisplayName(ChatColor.RED + "취소");
		List<String> DenLore = new ArrayList<String>();
		DenLore.add(ChatColor.GRAY + "창을 닫고 잠재옵션 부여를 취소합니다.");
		DenMeta.setLore(DenLore);
		Denied.setItemMeta(DenMeta);
		
		items.add(Denied);
		items.add(Accept);

		glass_1.setDurability((short)14);
		ItemMeta glass_1Meta = glass_1.getItemMeta();
		glass_1Meta.setDisplayName(ChatColor.RED + "Red Cube");
		glass_1.setItemMeta(glass_1Meta);
		items.add(glass_1);

		glass_2.setDurability((short)15);
		ItemMeta glass_2Meta = glass_2.getItemMeta();
		glass_2Meta.setDisplayName(ChatColor.DARK_GRAY + "Black Cube");
		glass_2.setItemMeta(glass_2Meta);
		items.add(glass_2);

		glass_3.setDurability((short)7);
		ItemMeta glass_3Meta = glass_3.getItemMeta();
		glass_3Meta.setDisplayName(ChatColor.GRAY + "Unknown Cube");
		glass_3.setItemMeta(glass_3Meta);
		items.add(glass_3);
		
		ItemMeta EndMeta = End.getItemMeta();
		EndMeta.setDisplayName(ChatColor.AQUA + "종료");
		List<String> EndLore = new ArrayList<String>();
		EndLore.add(ChatColor.GRAY + "잠재옵션 부여를 종료하고 아이템을 지급받습니다.");
		EndMeta.setLore(EndLore);
		End.setItemMeta(EndMeta);
		
		ItemMeta ReMeta = Retey.getItemMeta();
		ReMeta.setDisplayName(ChatColor.AQUA + "잠재옵션 재부여");
		List<String> ReLore = new ArrayList<String>();
		ReLore.add(ChatColor.GRAY + "잠재옵션을 다시 부여합니다.");
		ReMeta.setLore(ReLore);
		Retey.setItemMeta(ReMeta);
		
		items.add(End);
		items.add(Retey);
		
		ItemMeta NotMeta = Notice.getItemMeta();
		NotMeta.setDisplayName(ChatColor.AQUA + "! 알림 !");
		List<String> NotLore = new ArrayList<String>();
		NotLore.add(ChatColor.GRAY + "원하는 아이템을 선택하면, 잠재옵션 부여를 종료하고 아이템을 지급받습니다.");
		NotLore.add(ChatColor.GRAY + "창을 강제로 닫을 시, 큐브 사용 후의 아이템이 인벤토리에 지급됩니다.");
		NotMeta.setLore(NotLore);
		Notice.setItemMeta(NotMeta);

		BEDROCKs.setDurability((short)15);
		ItemMeta BEDROCKsMETA = BEDROCKs.getItemMeta();
		BEDROCKsMETA.setDisplayName(ChatColor.GRAY + "← Before / After →");
		BEDROCKs.setItemMeta(BEDROCKsMETA);
		
		items.add(Notice);
		items.add(BEDROCKs);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
    {
		Player player = null;
		ItemStack clicked = null;
		InventoryView inventory = null;
		
		if (event.getWhoClicked() instanceof Player)
			player = (Player) event.getWhoClicked();
		
		if (event.getCurrentItem() != null)
			clicked = event.getCurrentItem();
		
		if (event.getView() != null)
			inventory = event.getView();
		
		if (player != null && clicked != null  && inventory != null ) {
			if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " + ChatColor.RED + "레드 큐브")) {
				if (clicked.getType().toString().contains("WOOL") || clicked.getType().toString().contains("STAINED_GLASS_PANE")) {
					event.setCancelled(true);
					if (clicked.equals(items.get(0))) {
						isReroll = false;
						player.closeInventory();
					}
					if (clicked.equals(items.get(1))) {
						if (inventory.getItem(4) != null){
							if (Itemrank.getRank(inventory.getItem(4)) == 0) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 없는 아이템입니다.");
								return;
							}
							if (!player.getInventory().contains(MinecraftUtility.redCube)) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
								return;
							}
							else 
								player.getInventory().removeItem(MinecraftUtility.redCube);
							isReroll = true;
							Inventory cubeRed_Result = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " +  ChatColor.RED + "Result");
							cubeRed_Result.setItem(0, items.get(5));
							cubeRed_Result.setItem(1, items.get(2));
							cubeRed_Result.setItem(2, items.get(2));
							cubeRed_Result.setItem(3, items.get(2));
							cubeRed_Result.setItem(4, new ItemStack(Material.AIR));
							cubeRed_Result.setItem(5, items.get(2));
							cubeRed_Result.setItem(6, items.get(2));
							cubeRed_Result.setItem(7, items.get(2));
							cubeRed_Result.setItem(8, items.get(6));
							
							player.openInventory(cubeRed_Result);
							cubeRed_Result.setItem(4, Itemlore.setLore(player, inventory.getItem(4)));
							isReroll = false;
						}
						else {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템이 없습니다.");
							return;
						}
					}
				}
			}
			else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.RED + "Result")) {
					event.setCancelled(true);
					isReroll = false;
				if (clicked.equals(items.get(5))) {
					player.closeInventory();
				}
				if (clicked.equals(items.get(6))) {
					if (inventory.getItem(4).getType() != Material.AIR){
						if (!player.getInventory().contains(MinecraftUtility.redCube)) {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
							return;
						}
						else 
							player.getInventory().removeItem(MinecraftUtility.redCube);
						ItemStack temp = Itemrank.increaseRank(player, inventory.getItem(4), 2);	
						inventory.setItem(4, Itemlore.setLore(player, temp));
					}
				}
			}
			else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "수상한 큐브")) {
				if (clicked.getType().toString().contains("WOOL") || clicked.getType().toString().contains("STAINED_GLASS_PANE")) {
					event.setCancelled(true);
					if (clicked.equals(items.get(0))) {
						isReroll = false;
						player.closeInventory();
					}
					if (clicked.equals(items.get(1))) {
						if (inventory.getItem(4) != null){
							if (Itemrank.getRank(inventory.getItem(4)) > 2) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c수상한 큐브는 에픽 이하 등급만 사용할 수 있습니다.");
								return;
							}
							else if (Itemrank.getRank(inventory.getItem(4)) == 0) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 없는 아이템입니다.");
								return;
							}
							if (!player.getInventory().contains(MinecraftUtility.unknownCube)) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
								return;
							}
							else 
								player.getInventory().removeItem(MinecraftUtility.unknownCube);
							isReroll = true;
		            		Inventory cubeUnknown_Result = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "Result");
		            		cubeUnknown_Result.setItem(0, GUIManager.items.get(5));
		            		cubeUnknown_Result.setItem(1, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(2, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(3, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(4, new ItemStack(Material.AIR));
		            		cubeUnknown_Result.setItem(5, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(6, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(7, GUIManager.items.get(4));
		            		cubeUnknown_Result.setItem(8, GUIManager.items.get(6));
		            		
							player.openInventory(cubeUnknown_Result);
							cubeUnknown_Result.setItem(4, Itemlore.setLore(player, inventory.getItem(4)));
							isReroll = false;
						}
						else {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템이 없습니다.");
							return;
						}
					}
				}
			}
			else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "Result")) {
					event.setCancelled(true);
					isReroll = false;
				if (clicked.equals(items.get(5))) {
					player.closeInventory();
				}
				if (clicked.equals(items.get(6))) {
					if (inventory.getItem(4).getType() != Material.AIR){
						if (!player.getInventory().contains(MinecraftUtility.unknownCube)) {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
							return;
						}
						else 
							player.getInventory().removeItem(MinecraftUtility.unknownCube);
						ItemStack temp = Itemrank.increaseRank(player, inventory.getItem(4), 1);
						inventory.setItem(4, Itemlore.setLore(player, temp));
					}
				}
			}
			else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "블랙 큐브")) {
				if (clicked.getType().toString().contains("WOOL") || clicked.getType().toString().contains("STAINED_GLASS_PANE")) {
					event.setCancelled(true);
					if (clicked.equals(items.get(0))) {
						isReroll = false;
						player.closeInventory();
					}
					if (clicked.equals(items.get(1))) {
						if (inventory.getItem(4) != null){
							if (Itemrank.getRank(inventory.getItem(4)) == 0) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c잠재옵션이 없는 아이템입니다.");
								return;
							}
							if (!player.getInventory().contains(MinecraftUtility.blackCube)) {
								player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
								return;
							}
							else 
								player.getInventory().removeItem(MinecraftUtility.blackCube);
							isReroll = true;
							selAfter = true;
							
							Inventory cubeBlack_Result = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "Result");
							cubeBlack_Result.setItem(0, items.get(7));
							cubeBlack_Result.setItem(1, items.get(3));
							cubeBlack_Result.setItem(2, items.get(3));
							cubeBlack_Result.setItem(3, new ItemStack(Material.AIR));
							cubeBlack_Result.setItem(4, items.get(8));
							cubeBlack_Result.setItem(5, new ItemStack(Material.AIR));
							cubeBlack_Result.setItem(6, items.get(3));
							cubeBlack_Result.setItem(7, items.get(3));
							cubeBlack_Result.setItem(8, items.get(6));
							
							player.openInventory(cubeBlack_Result);
							cubeBlack_Result.setItem(3, inventory.getItem(4));
							cubeBlack_Result.setItem(5, Itemlore.setLore(player, inventory.getItem(4)));
							isReroll = false;
						}
						else {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c아이템이 없습니다.");
							return;
						}
					}
				}
			}
			else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "Result")) {
					event.setCancelled(true);
					isReroll = false;
				if (clicked.equals(inventory.getItem(3))) {
					selAfter = false;
					player.closeInventory();
				}
				else if (clicked.equals(inventory.getItem(5))) {
					selAfter = true;
					player.closeInventory();
				}
				else if (clicked.equals(items.get(6))) {
					if (inventory.getItem(5).getType() != Material.AIR){
						if (!player.getInventory().contains(MinecraftUtility.blackCube)) {
							player.sendMessage("\2476[\247eCubeMatrix\2476] \247c큐브가 부족합니다.");
							return;
						}
						else 
							player.getInventory().removeItem(MinecraftUtility.blackCube);
						ItemStack temp = Itemrank.increaseRank(player, inventory.getItem(5), 3);	
						inventory.setItem(5, Itemlore.setLore(player, temp));
					}
				}
			}
		}
    }
}
