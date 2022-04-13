package me.exzibyte.Listeners.Miscellaneous;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomShit extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        event.reply(event.getCommandId().toString()).queue();
    }

}
