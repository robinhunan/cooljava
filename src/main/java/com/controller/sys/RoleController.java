package com.controller.sys;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.mapper.sys.RoleMapper;
import com.model.base.AjaxResult;
import com.model.user.User;
import com.service.user.UserService;
import com.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.model.sys.Role;
import com.service.sys.RoleService;
import com.shiro.ShiroRealm;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    public RoleMapper roleMapper;

    /**
	 * 角色管理跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		return "views/sys/roleList";
	}
	
	/**
	 * 角色数据
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/data")
	public Map<String, Object> data(HttpServletRequest request, Model model, Role role) throws UnsupportedEncodingException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> map = new HashMap<String, Object>();
        //区分是否管理员登录,显示不同角色列表
        List<Role> list = new ArrayList<>();
        Long count = 0L;
        if (SecurityUtils.getSubject().hasRole("admin")) {
            list = roleService.getListByPage(role);
            count = roleService.getCount(role);
        } else {
            list = roleService.getListByPage(role);
            list.removeIf(roleTemp -> roleTemp.getId() == 1);
            count = roleService.getCount(role);
            --count;
        }
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", list);

        return map;
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Role role, HttpServletRequest request, Model model) {
		role = roleService.get(role);
		model.addAttribute("role", role);
		return "views/sys/roleForm";
	}
	
	/**
	 * 保存角色信息
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		 Role role = new  Role();
		 role.setName(request.getParameter("name"));
		 String id = request.getParameter("id");
        String rolekey = request.getParameter("rolekey");
        String description = request.getParameter("description");
		 if(id != null && id != ""){
			 role.setId(Integer.parseInt(id));
		 }
        role.setRolekey(rolekey);
        role.setDescription(description);
		try {
			roleService.save(role);
            userService.updateAdmin();
            ShiroUtils.doClearCache();
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
    public AjaxResult delete(HttpServletRequest request, Model model, Role role) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode('1');
        Integer count=	roleMapper.getUserRoleCount(role.getId());
        if (count != null && count > 1) {
            ajaxResult.setCode("2");
            ajaxResult.setMsg("当前角色使用中,请取消使用后再进行删除");
        } else {
            try {
                roleService.delete(role.getId());
                roleMapper.deleteRM(role.getId());
                userService.updateAdmin();
                ShiroUtils.doClearCache();
                ajaxResult.setCode("0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ajaxResult;
	}
	
	/**
	 * 禁用/解禁角色
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/setUse")
	public String setUse(HttpServletRequest request, Model model, Role role) {
		String result = "1";
		role = roleService.get(role);
		try {
			if(role.getUseable().equals("0")){
				role.setUseable("1");
			}else{
				role.setUseable("0");
			}
			roleService.save(role);
            ShiroUtils.doClearCache();
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 权限设置跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/setPermission")
	public String setPermission(Role role, HttpServletRequest request, Model model) {
		role = roleService.get(role);
		model.addAttribute("role", role);
		return "views/sys/permission";
	}
	
	/**
	 * 保存权限设置
	 * @param request
	 * @param model
	 * @param roleId
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePermission")
	public String savePermission(HttpServletRequest request, Model model, String roleId, String ids) {
		String result = "1";
		try {
			roleService.savePermission(roleId, ids);
			//保存权限后刷新权限
            ShiroUtils.doClearCache();
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
