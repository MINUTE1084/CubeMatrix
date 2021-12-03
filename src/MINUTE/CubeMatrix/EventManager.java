package MINUTE.CubeMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventManager implements Listener{
	static Random random = new Random();
	GUIManager GUImanager = new GUIManager();
	@EventHandler
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		LivingEntity damager;
		LivingEntity player;

		if (event.getEntity() instanceof LivingEntity)
			player = (LivingEntity) event.getEntity();
		else return;

		if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			damager = (LivingEntity) arrow.getShooter();
		} else if (event.getDamager() instanceof Snowball) {
			Snowball snow = (Snowball) event.getDamager();
			damager = (LivingEntity) snow.getShooter();
		} else if (event.getDamager() instanceof SmallFireball) {
			Snowball snow = (Snowball) event.getDamager();
			damager = (LivingEntity) snow.getShooter();
		} else if (event.getDamager() instanceof Fireball) {
			Fireball fireball = (Fireball) event.getDamager();
			damager = (LivingEntity) fireball.getShooter();
		} else if (event.getDamager() instanceof FishHook) {
			FishHook fishhook = (FishHook) event.getDamager();
			damager = (LivingEntity) fishhook.getShooter();
		} else if (event.getDamager() instanceof LivingEntity) {
			damager = (LivingEntity) event.getDamager();
		} else return;

		float armor = 0, ignoreArmor = 0, damage = (float) event.getDamage(), plusDamage = 1, critDamage = 1.25f, critPerc = 0.05f;
		int noTickDelay = player.getMaximumNoDamageTicks();
		List<ItemStack> damagerItems = new ArrayList<ItemStack>();

		if (damager.getEquipment().getHelmet() != null)
			damagerItems.add(damager.getEquipment().getHelmet());

		if (damager.getEquipment().getLeggings() != null)
			damagerItems.add(damager.getEquipment().getLeggings());

		if (damager.getEquipment().getBoots() != null)
			damagerItems.add(damager.getEquipment().getBoots());

		if (damager.getEquipment().getChestplate() != null)
			damagerItems.add(damager.getEquipment().getChestplate());

		if (damager.getEquipment().getItemInOffHand().getType() != Material.AIR)
			damagerItems.add(damager.getEquipment().getItemInOffHand());

		if (damager.getEquipment().getItemInMainHand().getType() != Material.AIR)
			damagerItems.add(damager.getEquipment().getItemInMainHand());

		for (ItemStack damagerItem : damagerItems) {
			if (damagerItem.getItemMeta() != null && damagerItem.getItemMeta().getLore() != null) {
				for (String Lore : damagerItem.getItemMeta().getLore()) {
					String temp = "";
					Lore = ChatColor.stripColor(Lore);
					for (int i = 0; i < Lore.length(); i++) {
						if (48 <= Lore.charAt(i) && Lore.charAt(i) <= 57)
							temp += Lore.charAt(i);
					}
					if (Lore.contains("데미지")) {
						if (Lore.contains("크리티컬")) {
							critDamage += (Float.parseFloat(temp) / 100);
						} else {
							if (Lore.contains("%")) {
								plusDamage += (Float.parseFloat(temp) / 100);
							} else {
								damage += Float.parseFloat(temp);
							}
						}
					}
					if (Lore.contains("크리티컬 확률")) {
						critPerc += (Integer.parseInt(temp) / 100);
					} else if (Lore.contains("방어력") && Lore.contains("무시")) {
						ignoreArmor = ignoreArmor + (Float.parseFloat(temp) / 100) - (ignoreArmor * (Float.parseFloat(temp) / 100));
					} else if (Lore.contains("구속")) {
						int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
						if (BuffRandom < BuffAcc) {
							damager.sendMessage("\2476[\247eCubeMatrix\2476] \2477구속 " + BuffLv + "단계를 걸었습니다.");
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80 * BuffLv, BuffLv - 1));
						}
					} else if (Lore.contains("혼란")) {
						int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
						if (BuffRandom < BuffAcc) {
							damager.sendMessage("\2476[\247eCubeMatrix\2476] \2475혼란 " + BuffLv + "단계를 걸었습니다.");
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100 + (80 * BuffLv), BuffLv - 1));
						}
					} else if (Lore.contains("독")) {
						int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
						if (BuffRandom < BuffAcc) {
							damager.sendMessage("\2476[\247eCubeMatrix\2476] \2472독 " + BuffLv + "단계를 걸었습니다.");
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80 * BuffLv, BuffLv - 1));
						}
					} else if (Lore.contains("실명")) {
						int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
						if (BuffRandom < BuffAcc) {
							damager.sendMessage("\2476[\247eCubeMatrix\2476] \2478실명 " + BuffLv + "단계를 걸었습니다.");
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80 * BuffLv, BuffLv - 1));
						}
					}
				}
			}
		}
		List<ItemStack> playerItems = new ArrayList<ItemStack>();

		if (player.getEquipment().getHelmet() != null)
			playerItems.add(player.getEquipment().getHelmet());

		if (player.getEquipment().getLeggings() != null)
			playerItems.add(player.getEquipment().getLeggings());

		if (player.getEquipment().getBoots() != null)
			playerItems.add(player.getEquipment().getBoots());

		if (player.getEquipment().getChestplate() != null)
			playerItems.add(player.getEquipment().getChestplate());

		if (player.getEquipment().getItemInOffHand().getType() != Material.AIR)
			playerItems.add(player.getEquipment().getItemInOffHand());

		if (player.getEquipment().getItemInMainHand().getType() != Material.AIR)
			playerItems.add(player.getEquipment().getItemInMainHand());

		for (ItemStack playerItem : playerItems) {
			if (playerItem.getItemMeta() != null && playerItem.getItemMeta().hasLore()) {
				for (String Lore : playerItem.getItemMeta().getLore()) {
					String temp = "";
					Lore = ChatColor.stripColor(Lore);
					for (int i = 0; i < Lore.length(); i++) {
						if (48 <= Lore.charAt(i) && Lore.charAt(i) <= 57)
							temp += Lore.charAt(i);
					}

					if (Lore.contains("방어력") && !Lore.contains("무시")) {
						armor += (Float.parseFloat(temp) / 100);
					} else if (Lore.contains("무적")) {
						noTickDelay += Integer.parseInt(temp);
					}
				}
			}
		}
		armor += MinecraftUtility.getPlayerArmorPoint(player);


		float middleDamage = (damage * plusDamage);
		if (random.nextInt(1000) < critPerc * 1000) {
			if (damager != null)
				damager.sendMessage("\2476[\247eCubeMatrix\2476] \2476Critical!");
			middleDamage *= critDamage;
		}

		float finaldamageAcc = 1 - armor + (ignoreArmor * armor);
		float finalDamage = middleDamage * finaldamageAcc;
		if (finalDamage < 0) finalDamage = 0;

		float finalHealth = (float) (player.getHealth() - finalDamage);
		if (finalHealth <= 0) {
			player.setHealth(1);
			event.setDamage(1000);
		} else {
			event.setDamage(0);
			player.setHealth(finalHealth);
		}
		player.setMaximumNoDamageTicks(noTickDelay);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
    {
		InventoryView inventory = null;
		if (event.getPlayer() instanceof Player) {
			if (event.getView() != null) {
				inventory = event.getView();
				if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " + ChatColor.RED + "레드 큐브") ||
						inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "블랙 큐브") ||
						inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "수상한 큐브") ) {
					if (inventory.getItem(4) != null && !GUIManager.isReroll) {
						event.getPlayer().getInventory().addItem(inventory.getItem(4));
					}
				}
				else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.RED + "Result") ||
						inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "Result")) {
					if (inventory.getItem(4) != null) {
						event.getPlayer().getInventory().addItem(inventory.getItem(4));
					}
				}
				else if (inventory.getTitle().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "Result")) {
					if (inventory.getItem(4) != null) {
						if (GUIManager.selAfter)
							event.getPlayer().getInventory().addItem(inventory.getItem(5));
						else
							event.getPlayer().getInventory().addItem(inventory.getItem(3));
					}
				}
			}
			MinecraftUtility.healthBonus((Player) event.getPlayer());
		}
    }
	@EventHandler
    public void onPlayerLogin(PlayerJoinEvent event)
    {
		if (event.getPlayer() instanceof Player)
			MinecraftUtility.healthBonus((Player) event.getPlayer());
    }

	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
		if (event.getPlayer() instanceof Player)
			MinecraftUtility.healthBonus((Player) event.getPlayer());
    }
	@EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getEquipment().getItemInMainHand().equals(MinecraftUtility.redCube) || player.getEquipment().getItemInMainHand().equals(MinecraftUtility.blackCube) || player.getEquipment().getItemInMainHand().equals(MinecraftUtility.unknownCube)) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            	if (player.getEquipment().getItemInMainHand().equals(MinecraftUtility.redCube)) {
            		event.setCancelled(true);
            		Inventory cubeRed = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " + ChatColor.RED + "레드 큐브");
            		cubeRed.setItem(0, GUIManager.items.get(0));
            		cubeRed.setItem(1, GUIManager.items.get(2));
            		cubeRed.setItem(2, GUIManager.items.get(2));
            		cubeRed.setItem(3, GUIManager.items.get(2));
            		cubeRed.setItem(4, new ItemStack(Material.AIR));
            		cubeRed.setItem(5, GUIManager.items.get(2));
            		cubeRed.setItem(6, GUIManager.items.get(2));
            		cubeRed.setItem(7, GUIManager.items.get(2));
            		cubeRed.setItem(8, GUIManager.items.get(1));
            		
            		player.openInventory(cubeRed);
            		
            	}
            	else if (player.getEquipment().getItemInMainHand().equals(MinecraftUtility.blackCube)) {
            		event.setCancelled(true);
            		Inventory cubeBlack = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "블랙 큐브");
            		cubeBlack.setItem(0, GUIManager.items.get(0));
            		cubeBlack.setItem(1, GUIManager.items.get(3));
            		cubeBlack.setItem(2, GUIManager.items.get(3));
            		cubeBlack.setItem(3, GUIManager.items.get(3));
            		cubeBlack.setItem(4, new ItemStack(Material.AIR));
            		cubeBlack.setItem(5, GUIManager.items.get(3));
            		cubeBlack.setItem(6, GUIManager.items.get(3));
            		cubeBlack.setItem(7, GUIManager.items.get(3));
            		cubeBlack.setItem(8, GUIManager.items.get(1));
            		
            		player.openInventory(cubeBlack);
            	}
            	else if (player.getEquipment().getItemInMainHand().equals(MinecraftUtility.unknownCube)) {
            		event.setCancelled(true);

            		Inventory cubeUnknown = Bukkit.createInventory(player, 9, "\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "수상한 큐브");
            		cubeUnknown.setItem(0, GUIManager.items.get(0));
            		cubeUnknown.setItem(1, GUIManager.items.get(4));
            		cubeUnknown.setItem(2, GUIManager.items.get(4));
            		cubeUnknown.setItem(3, GUIManager.items.get(4));
            		cubeUnknown.setItem(4, new ItemStack(Material.AIR));
            		cubeUnknown.setItem(5, GUIManager.items.get(4));
            		cubeUnknown.setItem(6, GUIManager.items.get(4));
            		cubeUnknown.setItem(7, GUIManager.items.get(4));
            		cubeUnknown.setItem(8, GUIManager.items.get(1));
            		
            		
            		player.openInventory(cubeUnknown);
            	}
            }
        }
    }
}
