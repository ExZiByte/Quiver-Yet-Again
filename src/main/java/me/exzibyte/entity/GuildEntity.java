package me.exzibyte.entity;

import com.google.common.base.Functions;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Map;
import java.util.stream.Collectors;

public class GuildEntity {

    private final long id;
    private final String name;
    private final long ownerId;
    private final Map<String, MemberEntity> members;
    private final GuildConfig config;

    public static GuildEntity defaultFromGuild(Guild guild) {
        var members = guild.getMembers().stream()
                .map(MemberEntity::fromMember)
                .collect(Collectors.toMap(entity -> String.valueOf(entity.getId()), Functions.identity()));

        return new GuildEntity(guild.getIdLong(), guild.getName(), guild.getOwnerIdLong(), members, GuildConfig.defaultSettings());
    }

    public GuildEntity(long id, String name, long ownerId, Map<String, MemberEntity> members, GuildConfig config) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.members = members;
        this.config = config;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public Map<String, MemberEntity> getMembers() {
        return members;
    }

    public GuildConfig getConfig() {
        return config;
    }
}
