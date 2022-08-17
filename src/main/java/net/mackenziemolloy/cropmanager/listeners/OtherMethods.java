package net.mackenziemolloy.cropmanager.listeners;

import net.mackenziemolloy.cropmanager.CropManager;
import net.mackenziemolloy.cropmanager.utils.sirblobman.MessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OtherMethods implements Listener {

    public CropManager cropManager;

    public OtherMethods(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @EventHandler
    public void onOther(BlockFromToEvent e) { //BlockFromToEvent

        List<ItemStack> dropList = new ArrayList<>(e.getToBlock().getDrops());
        //e.getToBlock().setType(Material.AIR);

        for (ItemStack itemStack : e.getToBlock().getDrops()) {
            e.getToBlock().getDrops().remove(itemStack);

            ConfigurationSection cropSection = cropManager.getConfiguration().getConfigurationSection("Crops." + itemStack.getType().name().toUpperCase() + ".Inorganic");
            if (cropSection == null) { dropAtLoc(e.getToBlock(), itemStack); continue; }

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (cropSection.get("Name") != null)
                itemMeta.setDisplayName(MessageUtility.color(cropSection.getString("Name")));
            else Bukkit.broadcastMessage("No name");
            if (cropSection.get("Lore") != null)
                itemMeta.setLore(MessageUtility.colorList(cropSection.getStringList("Lore")));
            else Bukkit.broadcastMessage("No lore");

            itemStack.setItemMeta(itemMeta);
            dropAtLoc(e.getToBlock(), itemStack);

            Bukkit.broadcastMessage("Updated");
        }

    }

    private void dropAtLoc(Block block, ItemStack itemStack) {
        block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
    }

}
