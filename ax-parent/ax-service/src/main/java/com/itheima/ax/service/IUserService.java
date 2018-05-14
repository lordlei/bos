package com.itheima.ax.service;

import com.itheima.ax.pojo.User;
import com.itheima.ax.utils.pageBean;

public interface IUserService  {

    public User login (User user);


    void editPassword(String id, String password);

    void save(User model, String[] roleIds);

    void pageQuery(pageBean pageBean);
}
