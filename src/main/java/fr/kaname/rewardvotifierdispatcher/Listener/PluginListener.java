package fr.kaname.rewardvotifierdispatcher.Listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.kaname.rewardvotifierdispatcher.RewardVotifierDispatcher;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginListener implements PluginMessageListener {
    private RewardVotifierDispatcher main;

    public PluginListener(RewardVotifierDispatcher main) {
        this.main = main;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equals("vote:votifierevent")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if (subChannel.equals("DispatchCommand")) {
                for(String command = in.readUTF(); !command.equals("StopDispatch"); command = in.readUTF()) {
                    this.main.getServer().getLogger().info(command);
                    boolean isCommand = player.getServer().dispatchCommand(this.main.getServer().getConsoleSender(), command);
                    if (!isCommand) {
                        this.main.getServer().getLogger().warning("Unknows Command : " + command);
                    }
                }

                this.main.getServer().getLogger().info("Un message de vote est arrivé");
            }

        }

        if (channel.equals("BungeeCord")) {

            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();

            if (subChannel.equals("DispatchCommand")) {

                this.main.getServer().getLogger().info("Un message de commandes est arrivé");

                short len = in.readShort();
                byte[] msgbytes = new byte[len];

                in.readFully(msgbytes);

                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));

                try {
                    for(String command = msgin.readUTF(); !command.equals("StopDispatch"); command = msgin.readUTF()) {
                        this.main.getServer().getLogger().info(command);
                        boolean isCommand = player.getServer().dispatchCommand(this.main.getServer().getConsoleSender(), command);
                        if (!isCommand) {
                            this.main.getServer().getLogger().warning("Unknows Command : " + command);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
