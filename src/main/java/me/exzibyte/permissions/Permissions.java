package me.exzibyte.permissions;

import com.google.common.collect.HashMultimap;
import me.exzibyte.permissions.groups.Group;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Permissions {

    private final Map<String, Group> groups;
    private final HashMultimap<Long, Group> memberGroups;

    public Permissions(Document document) {
        this.groups = parseGroups(document);
        this.memberGroups = parseBenis(document); // tODO change to member doc xd
    }

    @Nullable
    public Group getGroup(String name) {
        return groups.get(name.toLowerCase());
    }

    public void createGroup(Group group) {
        groups.put(group.getName(), group);
        // TODO insert into database
    }

    @NotNull
    private Map<String, Group> parseGroups(Document document) {
        var groups = new HashMap<String, Group>();

        for (var entries : document.entrySet()) {
            var name = entries.getKey();
            var permissions = (List<String>) entries.getValue();
            groups.put(name, new Group(name, new HashSet<>(permissions)));
        }

        return groups;
    }

    @NotNull
    private HashMultimap<Long, Group> parseBenis(Document document) {
        var members = HashMultimap.<Long, Group>create();

        for (var member : document.values()) {
            var memberDoc = (Document) member;
            var id = memberDoc.getLong("memberID");
            var groups = memberDoc.getList("groups", String.class);

            for (var name : groups) {
                var group = getGroup(name);

                if (group == null) {
                    continue;
                }

                members.put(id, group);
            }
        }

        return members;
    }

}
