package listeners;

import statics.PermissionChecker;
import net.dv8tion.jda.core.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RoleUpdatePermissionListener extends ListenerAdapter {
    public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent event) {
        PermissionChecker.checkPermission(event.getGuild());
    }
}
