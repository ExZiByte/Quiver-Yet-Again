package me.exzibyte.models;

import net.dv8tion.jda.api.entities.Member;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator
public class MemberEntity {

    private final long id;
    private final String name;

    @BsonCreator
    public MemberEntity(@BsonId long id,
                        @BsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public MemberEntity(Member member) {
        this.id = member.getIdLong();
        this.name = member.getUser().getAsTag();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
