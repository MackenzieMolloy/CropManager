package net.mackenziemolloy.cropmanager.listeners;

import com.google.common.base.Enums;
import net.mackenziemolloy.cropmanager.CropManager;
import net.mackenziemolloy.cropmanager.utils.sirblobman.MessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockDrop implements Listener {

    public CropManager cropManager;

    public BlockDrop(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent e) {
        Bukkit.broadcastMessage("Drop");
        if(Enums.getIfPresent(CropState.class, e.getBlock().getState().toString()).isPresent()) {
            for (Item item : e.getItems()) {
                ItemStack itemStack = item.getItemStack();
                ConfigurationSection cropSection = cropManager.getConfiguration().getConfigurationSection("Crops." + itemStack.getType().name().toUpperCase() + ".Organic");
                if (cropSection == null) continue;

                ItemMeta itemMeta = itemStack.getItemMeta();
                if (cropSection.get("Name") != null)
                    itemMeta.setDisplayName(MessageUtility.color(cropSection.getString("Name")));
                if (cropSection.get("Lore") != null)
                    itemMeta.setLore(MessageUtility.colorList(cropSection.getStringList("Lore")));

                itemStack.setItemMeta(itemMeta);
                item.setItemStack(itemStack);
            }
        }
    }
}
