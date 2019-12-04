package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.common.StringGenerator;
import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.mapper.UserModelMapper;
import com.ethan.crmsystem.infra.repository.UserRepository;
import com.ethan.crmsystem.web.model.UserInfoModel;
import com.ethan.crmsystem.web.model.UserModel;
import com.ethan.crmsystem.web.model.UserTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UserService.java, 2018/8/7 14:57 $
 */
@Service
public class UserService {

    private UserRepository userRepository;

    private UserModelMapper userModelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    /**
     * 增加用户
     *
     * @param userModel 前端用户表单
     * @return 是否成功
     */
    public String addUser(UserModel userModel) {
        Optional<User> userOptional = userRepository.findByLoginName(userModel.getLoginName());
        if (userOptional.isPresent()) {
            return ResponseConstants.EXISTS;
        }

        User user = new User();
        user.setLoginName(userModel.getLoginName());
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setMobile(userModel.getMobile());
        user.setEmail(userModel.getEmail());
        user.setType(userModel.getType());
        String password = StringGenerator.genRandom(8);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return password;
    }

    /**
     * 增加用户
     *
     * @param userModel 前端用户表单
     * @return 是否成功
     */
    public String editUser(UserModel userModel) {
        Optional<User> userOptional = userRepository.findById(userModel.getId());
        if (!userOptional.isPresent()) {
            return ResponseConstants.FAILURE;
        }

        User user = userOptional.get();
        user.setSubDepartments(userModel.getSubDepartments());
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setMobile(userModel.getMobile());
        user.setEmail(userModel.getEmail());
        user.setType(userModel.getType());

        userRepository.save(user);

        return ResponseConstants.SUCCESS;
    }

    /**
     * 查询用户
     *
     * @param name     用户名或姓名
     * @param roleId   角色
     * @param page     页数
     * @param pageSize 单页元素数量
     * @return 用户列表
     */
    public List<UserTableModel> query(String name, Integer roleId, Integer page,
                                      Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        return userModelMapper.findTable(name,roleId, pageSize, offset);
    }

    /**
     * 查询总元素数量
     *
     * @param name   用户名或姓名
     * @param unitId 单位
     * @param roleId 角色
     * @return 数量
     */
    public Integer count(String name, String unitId, Integer roleId) {
        return userModelMapper.count(name, unitId, roleId);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public String resetPassword(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseConstants.FAILURE;
        }

        String password = StringGenerator.genRandom(8);
        User user = userOptional.get();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return password;
    }

    /**
     * 从token获取用户名，并组合成页面需要的用户信息
     *
     * @param userId userId
     */
    public UserInfoModel buildUserInfo(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return null;
        }

        return userModelMapper.loadUserInfo(userId);
    }

    public UserTableModel getProfile(String loginName) {
        return userModelMapper.findByLoginName(loginName);
    }

    /**
     * 修改个人信息
     *
     * @param userModel 用户信息表单
     */
    public void updateProfile(UserModel userModel) {
        Optional<User> userOptional = userRepository.findById(userModel.getId());
        if (!userOptional.isPresent()) {
            return;
        }

        User user = userOptional.get();
        user.setFullName(userModel.getFullName());
        user.setSubDepartments(userModel.getSubDepartments());
        user.setEmail(userModel.getEmail());
        user.setMobile(userModel.getMobile());

        userRepository.save(user);
    }

    /**
     * 修改用户密码
     *
     * @param id          uuid
     * @param password    新密码
     * @param oldPassword 原密码
     */
    public String updatePassword(String id, String password, String oldPassword) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseConstants.FAILURE;
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseConstants.INCORRECT;
        }

        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return ResponseConstants.SUCCESS;
    }

    public UserModel loadUserById(String id) {
        Optional<User> wrapper = userRepository.findById(id);
        if (!wrapper.isPresent()) {
            return null;
        }

        User user = wrapper.get();

        UserModel result = new UserModel();
        result.setId(user.getId());
        result.setLoginName(user.getLoginName());
        result.setFullName(user.getFullName());
        result.setType(user.getType());
        result.setRoleId(user.getRoleId());
        result.setMobile(user.getMobile());
        result.setEmail(user.getEmail());

        return result;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserModelMapper(UserModelMapper userModelMapper) {
        this.userModelMapper = userModelMapper;
    }
}
