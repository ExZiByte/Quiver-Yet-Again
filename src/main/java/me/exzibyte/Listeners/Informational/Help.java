package me.exzibyte.Listeners.Informational;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();

    public Help(Quiver quiver){
        this.quiver = quiver;
    }


    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(!event.getName().equals("help")) return;
        EmbedBuilder eb = new EmbedBuilder();

        if(event.isFromGuild()){
            if(event.getOptions().size() == 0){
                eb.setDescription("Not yet fleshed out just getting the base function functioning");
                eb.setColor(0x000);
            }
        }
    }
}
