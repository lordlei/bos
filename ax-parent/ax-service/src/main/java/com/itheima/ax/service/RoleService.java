package com.itheima.ax.service;

import com.itheima.ax.pojo.Role;
import com.itheima.ax.utils.pageBean;

import java.util.List;

public interface RoleService {

    void save(Role model, String functionIds);

    void pageQuery(pageBean pageBean);

    List<Role> findAll();

}
