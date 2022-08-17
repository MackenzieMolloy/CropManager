package net.mackenziemolloy.cropmanager;

import net.mackenziemolloy.cropmanager.listeners.BlockBreak;
import net.mackenziemolloy.cropmanager.listeners.BlockDrop;
import net.mackenziemolloy.cropmanager.listeners.OtherMethods;
import net.mackenziemolloy.cropmanager.listeners.PlayerCropHarvest;
import net.mackenziemolloy.cropmanager.utils.CommentedConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CropManager extends JavaPlugin {

    CommentedConfiguration config;
    static CropManager instance;

    @Override
    public void onEnable() {
        config = new CommentedConfiguration();
        generateFiles();

        getServer().getPluginManager().registerEvents(new PlayerCropHarvest(this), this);
        getServer().getPluginManager().registerEvents(new BlockDrop(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
        getServer().getPluginManager().registerEvents(new OtherMethods(this), this);
    }

    public void generateFiles() {
        saveDefaultConfig();
        File pluginFolder = getDataFolder();
        File configFile = new File(pluginFolder, "config.yml");

        try {
            this.config.load(configFile);

            InputStream jarConfig = getResource("config.yml");
            this.config.syncWithConfig(configFile, jarConfig, "BossBars");
        } catch(IOException | InvalidConfigurationException ex) {
            Logger logger = getLogger();
            logger.log(Level.SEVERE, "Failed to load the 'config.yml' file due to an error:", ex);
        }
    }

    public CommentedConfiguration getConfiguration() {
        return config;
    }
    public static CropManager getInstance() { return instance; }

}
