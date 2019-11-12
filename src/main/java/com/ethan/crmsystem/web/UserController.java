package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.UserService;
import com.ethan.crmsystem.web.model.UserInfoModel;
import com.ethan.crmsystem.web.model.UserModel;
import com.ethan.crmsystem.web.model.UserTableModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : UserController.java,2019/11/6 23:58 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private UserService userService;

    private String jwtSecret;

    private String jwtPrefix;

    @PostMapping("/addUser")
    public String addUser(@Validated UserModel userModel) {
        return userService.addUser(userModel);
    }

    @PostMapping("/query")
    public List<UserTableModel> query(@RequestParam String name, Integer roleId, Integer page,
                                      Integer pageSize) {
        return userService.query(name,roleId, page, pageSize);
    }

    @PostMapping("/count")
    public Integer count(@RequestParam String name, String unitId, Integer roleId) {
        return userService.count(name, unitId, roleId);
    }

    @PostMapping("/editUser")
    public String editUser(@Validated UserModel userModel) {
        return userService.editUser(userModel);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/resetPassword/{id}")
    public String resetPassword(@PathVariable String id) {
        return userService.resetPassword(id);
    }

    @GetMapping("/getProfile/{loginName}")
    public UserTableModel getProfile(@PathVariable String loginName) {
        return userService.getProfile(loginName);
    }

    /**
     * @param token token
     * @return 用户信息
     */
    @GetMapping("/info")
    public UserInfoModel info(@RequestParam String token) {
        token = token.substring(jwtPrefix.length());
        Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        return userService.buildUserInfo(userId);
    }

    @GetMapping("/id/{id}")
    public UserModel loadById(@PathVariable String id) {
        return userService.loadUserById(id);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@Validated UserModel userModel) {
        userService.updateProfile(userModel);
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam String id, String password, String oldPassword) {
        return userService.updatePassword(id, password, oldPassword);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Value("${jwt.prefix}")
    public void setJwtPrefix(String jwtPrefix) {
        this.jwtPrefix = jwtPrefix;
    }
}