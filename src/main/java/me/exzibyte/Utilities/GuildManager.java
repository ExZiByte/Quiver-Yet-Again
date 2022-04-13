package me.exzibyte.Utilities;

import me.exzibyte.Quiver;
import me.exzibyte.models.GuildEntity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Class to load data for guilds from the database
 */
public class GuildManager extends ListenerAdapter {

    private final Quiver quiver;
    private final Map<Long, GuildEntity> cachedGuilds;

    public GuildManager(Quiver quiver) {
        this.quiver = quiver;
        this.cachedGuilds = new HashMap<>();
    }

    @NotNull
    public Collection<GuildEntity> allGuilds() {
        return cachedGuilds.values();
    }

    @Nullable
    public GuildEntity getGuild(Guild guild) {
        return getGuild(guild.getIdLong());
    }

    @Nullable
    public GuildEntity getGuild(long id) {
        return cachedGuilds.get(id);
    }

    /**
     * This event is fired when a guild is added to JDA's internal cache.
     * We use this to also cache our custom guild entity at runtime.
     * <p>
     * If the guild is not in the database already, a default document will
     * be created for it and inserted into the cache.
     */
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        var guild = event.getGuild();

        var collection = quiver.getDatabase().getCollection("guilds", GuildEntity.class);
        var entity = collection.find(eq("_id", guild.getIdLong())).first();

        if (entity == null) {
            entity = new GuildEntity(guild);
            collection.insertOne(entity);
        }

        cachedGuilds.put(guild.getIdLong(), entity);
        quiver.getCommandRegistry().registerAllCommandsInGuild(guild);
    }
}
