package com.mapper.user;

import com.mapper.base.BaseDao;
import com.model.sys.Role;
import com.model.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseDao<User>{
    /**
     * 获取用户对应所有角色
     * @param id
     * @return
     */
    List<Role> getRoles(int id);


    /**
     *删除用户和角色关系
     * @param userId
     */
    public void deleteUR(Integer userId);

    /**
     * 保存用户和角色关系
     * @param userId
     * @param str
     */
    public void insertUR(@Param("userId")int userId, @Param("roleId") String str);

    /**
     * 获取用户对应的所有角色id
     * @param userId
     * @return
     */
    List<String> getUserAllRole(@Param("userId")int userId);

    //获取所有角色id
    List<String> getAllRoleId();
    //通过角色id获取该角色授权用户
    List<User> getAssignUserDataByRoleId(Role role);
    //通过角色id获取该角色授权用户数量
    Long getAssignUserCount(Role role);
    //取消指定用户的角色授权
    void deleteAssignUser(@Param("userId")int userId,@Param("roleId") int roleId);

    List<User> getNoAssignUserDataByRoleId(Role role);

    Long getNoAssignUserCount(Role role);
}
