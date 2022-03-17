package me.exzibyte.Listeners.MIscellaneous;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        event.getJDA().getGuildById(488137783127572491L)
                .upsertCommand("ban", "Ban a member from the server")
                .addOption(OptionType.MENTIONABLE,"member", "The member you wish to ban", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for banning the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the ban", false, false).queue();
        event.getJDA().getGuildById(488137783127572491L)
                .upsertCommand("kick", "Kick a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to kick", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for kicking the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();
    }

}
