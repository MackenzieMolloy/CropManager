package net.mackenziemolloy.cropmanager.listeners;

import net.mackenziemolloy.cropmanager.CropManager;
import net.mackenziemolloy.cropmanager.utils.sirblobman.MessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerCropHarvest implements Listener {

    public CropManager cropManager;

    public PlayerCropHarvest(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @EventHandler
    public void onPlayerHarvest(PlayerHarvestBlockEvent e) { // PlayerHarvestBlockEvent
        Bukkit.broadcastMessage("harvet");
        for(ItemStack itemStack: e.getItemsHarvested()) {
            ConfigurationSection cropSection = cropManager.getConfiguration().getConfigurationSection("Crops." + itemStack.getType().name().toUpperCase() + ".Organic");
            if(cropSection == null) continue;

            ItemMeta itemMeta = itemStack.getItemMeta();
            if(cropSection.get("Name") != null) itemMeta.setDisplayName(MessageUtility.color(cropSection.getString("Name")));
            if(cropSection.get("Lore") != null) itemMeta.setLore(MessageUtility.colorList(cropSection.getStringList("Lore")));

            itemStack.setItemMeta(itemMeta);
        }
    }
}
