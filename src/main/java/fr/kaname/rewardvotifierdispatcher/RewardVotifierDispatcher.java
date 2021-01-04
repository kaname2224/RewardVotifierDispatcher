package fr.kaname.rewardvotifierdispatcher;

import fr.kaname.rewardvotifierdispatcher.Listener.PluginListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardVotifierDispatcher extends JavaPlugin {

    public void onEnable() {
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "vote:votifierevent", new PluginListener(this));
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginListener(this));
    }
}
