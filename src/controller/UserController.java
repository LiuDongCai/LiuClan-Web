package controller;

import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller注解用于指示该类是一个控制器，可以同时处理多个请求动作
 */
@Controller
@RequestMapping("/liu")
public class UserController {

    @RequestMapping(value="/user")
    // @ResponseBody会将集合数据转换json格式返回客户端
    @ResponseBody
    public Object getJson() {
        List<User> list = new ArrayList<>();
        list.add(new User("beyond","beyond","黄华华"));
        list.add(new User("hello","hello","谁谁谁"));
        return "{\n" +
                "    \"status\": \"1\",\n" +
                "    \"data\": {\n" +
                "        \"rc_uid\": \"37\",\n" +
                "        \"rc_name\": \"劉女士\",\n" +
                "        \"rc_portrait\": \"https://upload.debug.591.com.tw/user/2017/09/07/150477792627036604.png\"\n" +
                "    }\n" +
                "}";
    }

    // 静态List<User>集合，此处代替数据库用来保存注册的用户信息
    private static List<User> userList;

    // UserController类的构造器，初始化List<User>集合
    public UserController() {
        super();
        userList = new ArrayList<User>();
    }

    // 该方法映射的请求为http://localhost:8080/liu/register，该方法支持GET请求
    @GetMapping(value="/register")
    public String registerForm() {
        System.out.println("register GET方法被调用...");
        // 跳转到注册页面
        return "registerForm";
    }

    // 该方法映射的请求为http://localhost:8080/RequestMappingTest/user/register，该方法支持POST请求
    @PostMapping(value="/register")
    // 将请求中的loginname参数的值赋给loginname变量,password和username同样处理
    public String register(
            @RequestParam("loginname") String loginname,
            @RequestParam("password") String password,
            @RequestParam("username") String username,
            @RequestParam("phone") String phone) {
        System.out.println("register POST方法被调用...");
        // 创建User对象
        User user = new User();
        user.setLoginname(loginname);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhone(phone);
        // 模拟数据库存储User信息
        userList.add(user);
        // 跳转到登录页面
        return "loginForm";
    }

    // 该方法映射的请求为http://localhost:8080/RequestMappingTest/user/login
    @RequestMapping("/login")
    public String login(
            // 将请求中的loginname参数的值赋给loginname变量,password同样处理
            @RequestParam("loginname") String loginname,
            @RequestParam("password") String password,
            Model model) {
        System.out.println("登录名:"+loginname + " 密码:" + password);
        // 到集合中查找用户是否存在，此处用来模拟数据库验证
        for(User user : userList){
            if(user.getLoginname().equals(loginname)
                    && user.getPassword().equals(password)){
                model.addAttribute("user",user);
                return "welcome";
            }
        }
        return "loginForm";
    }


}
