package capstone.is4103capstone.general;

import capstone.is4103capstone.admin.service.PermissionControllerService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.enums.OperationTypeEnum;

public class AuthenticationTools {
    public static boolean authenticateUser(String username){
        return true;
    }

    public static void configurePermissionMap(DBEntityTemplate entity){
        entity.getPermissionMap().put(OperationTypeEnum.GRANT, PermissionControllerService.GLOBAL_SID_EVERYONE);
    }

    //for now;
    public static String getUsernameFromToken(String token){
        return token;
    }
}
