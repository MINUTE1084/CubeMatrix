package MINUTE.CubeMatrix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MinecraftUtility {
	public static ItemStack redCube =  new ItemStack(Material.RED_SHULKER_BOX, 1);
	static {
		ItemMeta redCube_Data = redCube.getItemMeta();
		redCube_Data.setDisplayName(ChatColor.WHITE + "\2476[\247eCubeMatrix\2476] " + ChatColor.RED + "레드 큐브");
		List<String> redCube_Lore = new ArrayList<String>();
		redCube_Lore.add(ChatColor.GRAY + "우 클릭 시 큐브 UI를 엽니다.");
		redCube_Data.setLore(redCube_Lore);
		redCube.setItemMeta(redCube_Data);
	}
	
	public static ItemStack unknownCube =  new ItemStack(Material.GRAY_SHULKER_BOX, 1);
	static {
		ItemMeta unknownCube_Data = unknownCube.getItemMeta();
		unknownCube_Data.setDisplayName(ChatColor.WHITE + "\2476[\247eCubeMatrix\2476] " + ChatColor.GRAY + "수상한 큐브");
		List<String> unknownCube_Lore = new ArrayList<String>();
		unknownCube_Lore.add(ChatColor.GRAY + "우 클릭 시 큐브 UI를 엽니다.");
		unknownCube_Data.setLore(unknownCube_Lore);
		unknownCube.setItemMeta(unknownCube_Data);
	}
	
	public static ItemStack blackCube = new ItemStack(Material.BLACK_SHULKER_BOX, 1);
	static {
		ItemMeta blackCube_Data = blackCube.getItemMeta();
		blackCube_Data.setDisplayName(ChatColor.WHITE + "\2476[\247eCubeMatrix\2476] " + ChatColor.DARK_GRAY + "블랙 큐브");
		List<String> blackCube_Lore = new ArrayList<String>();
		blackCube_Lore.add(ChatColor.GRAY + "우 클릭 시 큐브 UI를 엽니다.");
		blackCube_Data.setLore(blackCube_Lore);
		blackCube.setItemMeta(blackCube_Data);
	}
	
	public static float getPlayerArmorPoint(LivingEntity p) {
		int armorPoint = 0;
		float finalarmor = 0;
		List<ItemStack> Items = new ArrayList<ItemStack>();
		
		if (p.getEquipment().getHelmet() != null)
			Items.add(p.getEquipment().getHelmet());
		
		if (p.getEquipment().getLeggings() != null)
			Items.add(p.getEquipment().getLeggings());
		
		if (p.getEquipment().getBoots() != null)
			Items.add(p.getEquipment().getBoots());
		
		if (p.getEquipment().getChestplate() != null)
			Items.add(p.getEquipment().getChestplate());
		
		for (ItemStack Item_ : Items) {
			if (Item_ != null) {
				switch (Item_.getType()) {
					case DIAMOND_BOOTS:
						armorPoint += 3;
						break;
					case DIAMOND_CHESTPLATE:
						armorPoint += 8;
						break;
					case DIAMOND_HELMET:
						armorPoint += 3;
						break;
					case DIAMOND_LEGGINGS:
						armorPoint += 6;
						break;
					case NETHERITE_BOOTS:
						armorPoint += 3;
						break;
					case NETHERITE_CHESTPLATE:
						armorPoint += 8;
						break;
					case NETHERITE_HELMET:
						armorPoint += 3;
						break;
					case NETHERITE_LEGGINGS:
						armorPoint += 6;
						break;
					case GOLDEN_BOOTS:
						armorPoint += 1;
						break;
					case GOLDEN_CHESTPLATE:
						armorPoint += 2;
						break;
					case GOLDEN_HELMET:
						armorPoint += 2;
						break;
					case GOLDEN_LEGGINGS:
						armorPoint += 3;
						break;
					case IRON_BOOTS:
						armorPoint += 2;
						break;
					case IRON_CHESTPLATE:
						armorPoint += 6;
						break;
					case IRON_HELMET:
						armorPoint += 2;
						break;
					case IRON_LEGGINGS:
						armorPoint += 5;
						break;
					case LEATHER_BOOTS:
						armorPoint += 1;
						break;
					case LEATHER_CHESTPLATE:
						armorPoint += 3;
						break;
					case LEATHER_HELMET:
						armorPoint += 1;
						break;
					case LEATHER_LEGGINGS:
						armorPoint += 2;
						break;
					case CHAINMAIL_BOOTS:
						armorPoint += 1;
						break;
					case CHAINMAIL_CHESTPLATE:
						armorPoint += 5;
						break;
					case CHAINMAIL_HELMET:
						armorPoint += 2;
						break;
					case CHAINMAIL_LEGGINGS:
						armorPoint += 4;
						break;
					default:
						break;
				}
				
				if (Item_.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 0) 
					finalarmor += (Item_.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * 0.04);
			}
		}
		
		finalarmor += ((float)armorPoint/25);
		
		if (finalarmor > 0.8) finalarmor = 0.8f;
		return finalarmor;
	}

	public static boolean isWearing(LivingEntity p) {
		if (p.getEquipment().getItemInOffHand().getType() != Material.AIR) {
			switch (p.getEquipment().getItemInOffHand().getType()) {
				case DIAMOND_BOOTS:
				case DIAMOND_CHESTPLATE:
				case DIAMOND_HELMET:
				case DIAMOND_LEGGINGS:
				case NETHERITE_BOOTS:
				case NETHERITE_CHESTPLATE:
				case NETHERITE_HELMET:
				case NETHERITE_LEGGINGS:
				case GOLDEN_BOOTS:
				case GOLDEN_CHESTPLATE:
				case GOLDEN_HELMET:
				case GOLDEN_LEGGINGS:
				case IRON_BOOTS:
				case IRON_CHESTPLATE:
				case IRON_HELMET:
				case IRON_LEGGINGS:
				case LEATHER_BOOTS:
				case LEATHER_CHESTPLATE:
				case LEATHER_HELMET:
				case LEATHER_LEGGINGS:
				case CHAINMAIL_BOOTS:
				case CHAINMAIL_CHESTPLATE:
				case CHAINMAIL_HELMET:
				case CHAINMAIL_LEGGINGS:
				case PLAYER_HEAD:
				case ZOMBIE_HEAD:
				case CREEPER_HEAD:
				case DRAGON_HEAD:
				case SKELETON_SKULL:
				case WITHER_SKELETON_SKULL:
				case ELYTRA:
					return true;
				default:
					return false;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void healthBonus(LivingEntity p) {
		if(p.getEquipment().equals(p.getEquipment())){
			List<ItemStack> playerItems = new ArrayList<ItemStack>();
			
			if (p.getEquipment().getHelmet() != null)
				playerItems.add(p.getEquipment().getHelmet());
			
			if (p.getEquipment().getLeggings() != null)
				playerItems.add(p.getEquipment().getLeggings());
			
			if (p.getEquipment().getBoots() != null)
				playerItems.add(p.getEquipment().getBoots());
			
			if (p.getEquipment().getChestplate() != null)
				playerItems.add(p.getEquipment().getChestplate());
			
			float addHP = 0, addHPPercent = 1;
			for (ItemStack playerItem : playerItems) {
				if (playerItem.getItemMeta().hasLore()) {
				for (String Lore : playerItem.getItemMeta().getLore()) {
					String temp = "";
					for (int i = 4 ; i < Lore.length(); i ++) {   
			            if(48 <= Lore.charAt(i) && Lore.charAt(i) <= 57)
			            	temp += Lore.charAt(i);
					}
					
					if (Lore.contains("HP")) {
						if (Lore.contains("%")) {
							addHPPercent += (Float.parseFloat(temp) / 100);
						}
						else {
							addHP += Float.parseFloat(temp);
						}
					}
				}
				}
			}
			
			float finalHP = (float) ((20 + addHP) * addHPPercent);
			p.setMaxHealth(finalHP);
		}
	}

	public static void giveCube(Player p, int cubeType, int cubeAmount) {
		if (cubeAmount <= 0) cubeAmount = 1;
		if (cubeAmount > 36) cubeAmount = 36;
		
		for (int i = 0; i < cubeAmount; i++) {
			switch (cubeType) {
			case 1:
				p.getInventory().addItem(unknownCube);
				break;
			case 2:
				p.getInventory().addItem(redCube);
				break;
			case 3:
				p.getInventory().addItem(blackCube);
				break;
			}
		}
	}
}
