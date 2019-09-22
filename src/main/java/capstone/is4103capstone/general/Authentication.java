package capstone.is4103capstone.general;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.enums.PermissionTypeEnum;

public class Authentication {
    public static boolean authenticateUser(String username){
        return true;
    }

    public static void configurePermissionMap(DBEntityTemplate entity){
        entity.getPermissionMap().put(PermissionTypeEnum.GRANT,"all_user");
    }
}
