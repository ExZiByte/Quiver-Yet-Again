package me.exzibyte.entity;

import net.dv8tion.jda.api.entities.Member;

public class MemberEntity {

    private final long id;
    private final String name;

    public static MemberEntity fromMember(Member member) {
        return new MemberEntity(member.getIdLong(), member.getUser().getAsTag());
    }

    public MemberEntity(/*@BsonProperty("id") */long id, /*@BsonProperty("name") */String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
