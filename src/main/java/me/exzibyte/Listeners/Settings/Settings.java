package me.exzibyte.Listeners.Settings;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Settings extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(!event.getName().equals("settings")) return;
        if(!event.isFromGuild()) return;

        if(event.getSubcommandName().equals("update")){
            if (!event.getOptionsByName("locale").isEmpty()) {
                System.out.println("Locale Update");
            }
            if (!event.getOptionsByName("log-channel").isEmpty()) {
                System.out.println("Log Channel Updated");
            }
            if (!event.getOptionsByName("announcement-channel").isEmpty()) {
                System.out.println("Announcement Channel Updated");
            }
            if (!event.getOptionsByName("join-log-channel").isEmpty()) {
                System.out.println("Join Log Updated");
            }
            if (!event.getOptionsByName("mute-role").isEmpty()) {
                System.out.println("Mute Role Updated");
            }
        }
        if(event.getSubcommandName().equals("reset")){
            if (!event.getOptionsByName("locale").isEmpty()) {
                System.out.println("Locale Reset");
            }
            if (!event.getOptionsByName("log-channel").isEmpty()) {
                System.out.println("Log Channel Reset");
            }
            if (!event.getOptionsByName("announcement-channel").isEmpty()) {
                System.out.println("Announcement Channel Reset");
            }
            if (!event.getOptionsByName("join-log-channel").isEmpty()) {
                System.out.println("Join Log Reset");
            }
            if (!event.getOptionsByName("mute-role").isEmpty()) {
                System.out.println("Mute Role Reset");
            }
        }
        
    }

}
