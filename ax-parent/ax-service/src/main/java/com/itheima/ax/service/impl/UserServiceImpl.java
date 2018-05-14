package com.itheima.ax.service.impl;

import com.itheima.ax.dao.IUserDao;
import com.itheima.ax.pojo.Role;
import com.itheima.ax.pojo.User;
import com.itheima.ax.service.IUserService;
import com.itheima.ax.utils.MD5Utils;
import com.itheima.ax.utils.pageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource //注解注入
    private IUserDao userDao;

//    登录
    public User login(User user) {

        return userDao.findUserByUsernameAndPassword(user.getUsername(), MD5Utils.md5(user.getPassword()));

    }

//    修改密码
    @Override
    public void editPassword(String id, String password) {
        password=MD5Utils.md5(password);
        userDao.update("user.editPassword",password,id);
    }

    @Override
    public void save(User model, String[] roleIds) {
        String password = model.getPassword();
        password= MD5Utils.md5(password);
        model.setPassword(password);

        userDao.save(model);
        if (roleIds!=null && roleIds.length>0){
            for (String id : roleIds) {
//               手动创建托管状态
                Role role = new Role(id);
//                用户对象关联角色对象
                model.getRoles().add(role);
            }
        }


    }

    @Override
    public void pageQuery(pageBean pageBean) {
        userDao.pageQuery(pageBean);
    }

}
