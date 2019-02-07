package MINUTE.CubeMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventManager implements Listener{
	static Random random = new Random();
	GUIManager GUImanager = new GUIManager();
	@EventHandler
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		Player damager;
		Player player;
		
		if ( event.getEntity() instanceof Player ) {
			player = (Player)event.getEntity();
			
			if (event.getDamager() instanceof Player)
				damager = (Player) event.getDamager();
			else if (event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if (arrow.getShooter() instanceof Player)
					damager = (Player) arrow.getShooter();
				else return;
			}
			else if (event.getDamager() instanceof Snowball) {
				Snowball snow=(Snowball)event.getDamager();
				if(snow.getShooter() instanceof Player)
					damager = (Player) snow.getShooter();
				else return;
			}
			else if (event.getDamager() instanceof SmallFireball) {
				Snowball snow=(Snowball)event.getDamager();
				if(snow.getShooter() instanceof Player)
					damager = (Player) snow.getShooter();
				else return;
			}
			else if (event.getDamager() instanceof Fireball) {
				Fireball fireball=(Fireball)event.getDamager();
				if(fireball.getShooter() instanceof Player)
					damager = (Player) fireball.getShooter();
				else return;
			}
			else if (event.getDamager() instanceof SmallFireball) {
				SmallFireball smallfireball = (SmallFireball)event.getDamager();
				if(smallfireball.getShooter() instanceof Player)
					damager = (Player) smallfireball.getShooter();
				else return;
			}
			else if (event.getDamager() instanceof FishHook) {
				FishHook fishhook = (FishHook)event.getDamager();
				if(fishhook.getShooter() instanceof Player)
					damager = (Player) fishhook.getShooter();
				else return;
			}
			else return;
		}
		else return;
		
		float armor = 0, ignoreArmor = 0, damage = (float)event.getDamage(), plusDamage = 1, critDamage = 1.25f, critPerc = 0.05f;	
		int noTickDelay = player.getMaximumNoDamageTicks();
		List<ItemStack> damagerItems = new ArrayList<ItemStack>();
		
		if (damager.getInventory().getHelmet() != null)
			damagerItems.add(damager.getInventory().getHelmet());
		
		if (damager.getInventory().getLeggings() != null)
			damagerItems.add(damager.getInventory().getLeggings());
		
		if (damager.getInventory().getBoots() != null)
			damagerItems.add(damager.getInventory().getBoots());
		
		if (damager.getInventory().getChestplate() != null)
			damagerItems.add(damager.getInventory().getChestplate());
		
		if (damager.getInventory().getItemInOffHand().getType() != Material.AIR)
			damagerItems.add(damager.getInventory().getItemInOffHand());
		
		if (damager.getInventory().getItemInMainHand().getType() != Material.AIR)
			damagerItems.add(damager.getInventory().getItemInMainHand());
		
		for (ItemStack damagerItem : damagerItems) {
			if (damagerItem.getItemMeta().getLore() != null) {
			for (String Lore : damagerItem.getItemMeta().getLore()) {
				String temp = "";
				Lore = ChatColor.stripColor(Lore);
				for (int i = 0 ; i < Lore.length(); i ++) {   
		            if(48 <= Lore.charAt(i) && Lore.charAt(i) <= 57)
		            	temp += Lore.charAt(i);
				}
				if (Lore.contains("데미지")) {
					if (Lore.contains("크리티컬")) {
						critDamage += (Float.parseFloat(temp) / 100);
					}
					else {
						if (Lore.contains("%")) {
							plusDamage += (Float.parseFloat(temp) / 100);
						}
						else {
							damage += Float.parseFloat(temp);
						}
					}
				}
				if (Lore.contains("크리티컬 확률")) {
					critPerc += (Integer.parseInt(temp) / 100);
				}
				
				else if (Lore.contains("방어력") && Lore.contains("무시") ) {
					ignoreArmor = ignoreArmor + (Float.parseFloat(temp) / 100) - (ignoreArmor * (Float.parseFloat(temp) / 100));
				}
				

				else if (Lore.contains("구속")) {
					int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
					if (BuffRandom < BuffAcc) {
						damager.sendMessage("\2476[\247eCubeMatrix\2476] \2477구속 "+ BuffLv +"단계를 걸었습니다.");
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80 * BuffLv, BuffLv-1));
					}
				}
				
				else if (Lore.contains("혼란")) {
					int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
					if (BuffRandom < BuffAcc) {
						damager.sendMessage("\2476[\247eCubeMatrix\2476] \2475혼란 "+ BuffLv +"단계를 걸었습니다.");
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100 + (80 * BuffLv), BuffLv-1));	
					}
				}
				
				else if (Lore.contains("독")) {
					int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
					if (BuffRandom < BuffAcc) {
						damager.sendMessage("\2476[\247eCubeMatrix\2476] \2472독 "+ BuffLv +"단계를 걸었습니다.");
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80 * BuffLv, BuffLv-1));
					}
				}
				
				else if (Lore.contains("실명")) {
					int BuffAcc = Integer.parseInt(temp) / 10, BuffLv = Integer.parseInt(temp) % 50, BuffRandom = random.nextInt(100);
					if (BuffRandom < BuffAcc) {
						damager.sendMessage("\2476[\247eCubeMatrix\2476] \2478실명 "+ BuffLv +"단계를 걸었습니다.");
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80 * BuffLv, BuffLv-1));
					}
				}
			}
			}
		}
		List<ItemStack> playerItems = new ArrayList<ItemStack>();
		
		if (player.getInventory().getHelmet() != null)
			playerItems.add(player.getInventory().getHelmet());
		
		if (player.getInventory().getLeggings() != null)
			playerItems.add(player.getInventory().getLeggings());
		
		if (player.getInventory().getBoots() != null)
			playerItems.add(player.getInventory().getBoots());
		
		if (player.getInventory().getChestplate() != null)
			playerItems.add(player.getInventory().getChestplate());
		
		if (player.getInventory().getItemInOffHand().getType() != Material.AIR)
			playerItems.add(player.getInventory().getItemInOffHand());
		
		if (player.getInventory().getItemInMainHand().getType() != Material.AIR)
			playerItems.add(player.getInventory().getItemInMainHand());
		
		for (ItemStack playerItem : playerItems) {
			if (playerItem.getItemMeta().hasLore()) {
			for (String Lore : playerItem.getItemMeta().getLore()) {
				String temp = "";
				Lore = ChatColor.stripColor(Lore);
				for (int i = 0 ; i < Lore.length(); i ++) {    
		            if(48 <= Lore.charAt(i) && Lore.charAt(i) <= 57)
		            	temp += Lore.charAt(i);
				}
				
				if (Lore.contains("방어력") && !Lore.contains("무시") ) {
					armor += (Float.parseFloat(temp) / 100);
				}
				else if (Lore.contains("무적")) {
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
		}
		else {
			event.setDamage(0);
			player.setHealth(finalHealth);
		}
		player.setMaximumNoDamageTicks(noTickDelay);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
    {
		Inventory inventory = null;
		if (event.getPlayer() instanceof Player) {
			if (event.getInventory() != null) {
				inventory = event.getInventory();
				if (inventory.getName().equals("\2476[\247eCubeMatrix\2476] " + ChatColor.RED + "레드 큐브") ||
						inventory.getName().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "블랙 큐브") ||
						inventory.getName().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "수상한 큐브") ) {
					if (inventory.getItem(4) != null && !GUIManager.isReroll) {
						event.getPlayer().getInventory().addItem(inventory.getItem(4));
					}
				}
				else if (inventory.getName().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.RED + "Result") ||
						inventory.getName().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.GRAY + "Result")) {
					if (inventory.getItem(4) != null) {
						event.getPlayer().getInventory().addItem(inventory.getItem(4));
					}
				}
				else if (inventory.getName().equals("\2476[\247eCubeMatrix\2476] " +  ChatColor.DARK_GRAY + "Result")) {
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
        if(player.getInventory().getItemInMainHand().equals(MinecraftUtility.redCube) || player.getInventory().getItemInMainHand().equals(MinecraftUtility.blackCube) || player.getInventory().getItemInMainHand().equals(MinecraftUtility.unknownCube)) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            	if (player.getInventory().getItemInMainHand().equals(MinecraftUtility.redCube)) {
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
            	else if (player.getInventory().getItemInMainHand().equals(MinecraftUtility.blackCube)) {
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
            	else if (player.getInventory().getItemInMainHand().equals(MinecraftUtility.unknownCube)) {
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
