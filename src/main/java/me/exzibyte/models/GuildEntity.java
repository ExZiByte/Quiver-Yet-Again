package me.exzibyte.models;

import com.google.common.base.Functions;
import me.exzibyte.Quiver;
import net.dv8tion.jda.api.entities.Guild;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

@BsonDiscriminator
public class GuildEntity {

    private final long id;
    private final String name;
    private final long ownerId;
    private final Map<String, MemberEntity> members;
    private final GuildConfig config;

    @BsonCreator
    public GuildEntity(@BsonId long id,
                       @BsonProperty("name") String name,
                       @BsonProperty("ownerId") long ownerId,
                       @BsonProperty("members") Map<String, MemberEntity> members,
                       @BsonProperty("config") GuildConfig config) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.members = members;
        this.config = config;
    }

    public GuildEntity(Guild guild) {
        this(guild.getIdLong(), guild.getName(), guild.getOwnerIdLong(), guild.getMembers().stream()
                        .filter(member -> !member.getUser().isBot())
                        .map(MemberEntity::new)
                        .collect(Collectors.toMap(entity -> String.valueOf(entity.getId()), Functions.identity())),
                GuildConfig.defaultSettings());
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

    @Deprecated(forRemoval = true)
    public Guild guild() {
        return Quiver.getManager().getGuildById(getId());
    }

    @Override
    public String toString() {
        return "GuildEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", members=" + members +
                ", config=" + config +
                '}';
    }
}
