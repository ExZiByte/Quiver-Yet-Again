package me.exzibyte.permissions.groups;

import java.util.Set;

public class Group {

    private final String name;
    private final Set<String> permissionNodes;

    public Group(String name, Set<String> permissionNodes) {
        this.name = name.toLowerCase();
        this.permissionNodes = permissionNodes;
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(String permission) {
        // keep in mind that case matters (case-sensitive permissions)
        return permissionNodes.contains(permission);
    }

    public void addPermission(String permission) {
        permission = permission.toLowerCase();
        permissionNodes.add(permission);
        // TODO insert into database, yep, much better solution imo
    }

    /*
    Member {
       groups{
          'admin"
          "mod"
          "member"
        }
    } // yesssirrrrrrrrrrrr yeehawww
     */
}
