package me.exzibyte.permissions.groups;

import me.exzibyte.Quiver;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GroupCreate extends ListenerAdapter {

    private final Quiver quiver;

    //@Permission("commands.groups.create")
    public GroupCreate(Quiver quiver){
        this.quiver = quiver;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("groups") && !event.getSubcommandName().equals("create")) return;

    }

}
