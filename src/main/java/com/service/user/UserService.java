package com.service.user;

import com.mapper.sys.MenuMapper;
import com.mapper.sys.RoleMapper;
import com.model.sys.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.user.UserMapper;
import com.model.user.User;
import com.service.base.CrudService;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserService extends CrudService<UserMapper, User>{

    @Resource
    public UserMapper userMapper;
    @Resource
    public RoleMapper roleMapper;
    @Resource
    public MenuMapper menuMapper;

    @Transactional
    public void saveRole(int userId, String roleIds) {

        String[] strs = roleIds.split(",");
        //删除已有关联关系
        userMapper.deleteUR(userId);


        //保存新的用户和角色的关联关系
        for (int i = 0; i < strs.length; i++) {
            userMapper.insertUR(userId, strs[i]);

        }
    }

    public List<String> getUserAllRole(int userId) {
        return userMapper.getUserAllRole(userId);
    }

    //更新管理员权限信息
    public void updateAdmin() {
        //更新所属角色为所有角色
        List<String> roleIds= userMapper.getAllRoleId();
        userMapper.deleteUR(1);
        for (String roleId : roleIds) {
            userMapper.insertUR(1,roleId);
        }

        //更新所属菜单页面为所有页面
        List<String> menuIds= menuMapper.getAllMenuId();
        roleMapper.deleteRM(2);
        for (String menuId : menuIds) {
            roleMapper.insertRM("2", menuId);
        }
    }



    //通过角色id获取该角色授权用户
    public List<User> getAssignUserDataByRoleId(Role role) {
        return userMapper.getAssignUserDataByRoleId(role);
    }
    //通过角色id获取该角色授权用户数量
    public Long getAssignUserCount(Role role) {
        return userMapper.getAssignUserCount(role);
    }

    //取消指定用户的角色授权
    public void deleteAssignUser(int userId, int roleId) {
        userMapper.deleteAssignUser(userId, roleId);
    }

    public List<User> getNoAssignUserDataByRoleId(Role role) {
        return userMapper.getNoAssignUserDataByRoleId(role);
    }

    public Long getNoAssignUserCount(Role role) {
        return userMapper.getNoAssignUserCount(role);
    }
}