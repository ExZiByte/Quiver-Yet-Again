package me.exzibyte.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Object representation of all the config values for a specific guild.
 */
public class GuildConfig {

    // private final Map<String, List<String>> groups;
    private String locale;
    private Long logChannel;
    private Long joinLog;
    private Long muteRole;
    private Long announcementChannel;
    private boolean blacklisted;
    private boolean premium;

    @NotNull
    public static GuildConfig defaultSettings() {
        return new GuildConfig( "en_US", null, null, null, null, false, false);
    }

    /*
     * BSON public constructor, values will be automatically filled at runtime.
     */
    public GuildConfig() {
    }

    // this is gonna get weird with default values, idk we can try
    public GuildConfig(String locale, Long logChannel, Long joinLog, Long muteRole, Long announcementChannel, boolean blacklisted, boolean premium) {
        this.locale = locale;
        this.logChannel = logChannel;
        this.joinLog = joinLog;
        this.muteRole = muteRole;
        this.announcementChannel = announcementChannel;
        this.blacklisted = blacklisted;
        this.premium = premium;
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
