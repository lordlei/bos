package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Role;
import com.itheima.ax.service.RoleService;
import com.itheima.ax.web.base.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
    private String functionIds;
    @Autowired
    private RoleService roleService;

    /**
     * 添加角色,并关联权限
     */
    public String add(){
        roleService.save(getModel(),functionIds);
        return LIST;
    }

    /**
     * 分页查询
     */
    public String pageQuery(){
        roleService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"functions","users"});
        return NONE;
    }

    /**
     * 查询所有角色
     */
    public String listAjax(){
        List<Role> list=roleService.findAll();
        this.java2Json(list,new String[]{"functions","users"});
        return NONE;
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }
}
