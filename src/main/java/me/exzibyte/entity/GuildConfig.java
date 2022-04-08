package me.exzibyte.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Object representation of all the config values for a specific guild.
 */
public class GuildConfig {

    // private final Map<String, List<String>> groups;
    private final String prefix;
    private final String locale;
    private final Long logChannel;
    private final Long joinLog;
    private final Long muteRole;
    private final Long announcementChannel;
    private final boolean blacklisted;
    private final boolean premium;

    @NotNull
    public static GuildConfig defaultSettings() {
        return new GuildConfig("q!", "en_US", null, null, null, null, false, false);
    }

    // this is gonna get weird with default values, idk we can try
    public GuildConfig(String prefix, String locale, Long logChannel, Long joinLog, Long muteRole, Long announcementChannel, boolean blacklisted, boolean premium) {
        this.prefix = prefix;
        this.locale = locale;
        this.logChannel = logChannel;
        this.joinLog = joinLog;
        this.muteRole = muteRole;
        this.announcementChannel = announcementChannel;
        this.blacklisted = blacklisted;
        this.premium = premium;
    }

    public String getPrefix() {
        return prefix;
    }

    @Nullable
    public Long getLogChannel() {
        return logChannel;
    }

    @Nullable
    public Long getJoinLog() {
        return joinLog;
    }

    @Nullable
    public Long getMuteRole() {
        return muteRole;
    }

    @Nullable
    public Long getAnnouncementChannel() {
        return announcementChannel;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public boolean isPremium() {
        return premium;
    }
}
