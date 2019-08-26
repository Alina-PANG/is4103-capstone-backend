package capstone.is4103capstone.module01.controller;

import capstone.is4103capstone.module01.model.DemoRole;
import capstone.is4103capstone.module01.model.DemoUser;
import capstone.is4103capstone.module01.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/demouser")
public class DemoUserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String getUserInfo(@RequestParam(value="username") String username) {
        //Try access http://localhost:8080/demouser/userinfo?username=demo, can see the json result
        System.out.println("Looking for username:"+username);
        DemoUser thisUser = userService.findByUsername(username);
        JSONObject returnObj = new JSONObject();
        if (thisUser == null){
            returnObj.put("result",false);
        }else{
            returnObj.put("userid",thisUser.getId());
        }

        return returnObj.toString();
    }

    @RequestMapping(value = "/allroles",method = RequestMethod.GET)
    public String showRoles(){
        //http://localhost:8080/demouser/allroles
        List<DemoRole> roles = userService.getAllRoles();

        JSONObject obj = new JSONObject();
        if (roles == null){
            obj.put("result",false);
        }else{
            obj.put("size",roles.size());
            if (roles.size() > 0){
                obj.put("first",roles.get(0).getName());
            }
        }

        return obj.toString();
    }
}
