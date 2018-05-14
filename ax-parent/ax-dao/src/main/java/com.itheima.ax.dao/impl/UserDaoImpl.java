package com.itheima.ax.dao.impl;

import com.itheima.ax.dao.IUserDao;
import com.itheima.ax.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao  {

//    根据用户和密码获取用户对象
    public User findUserByUsernameAndPassword(String username, String password) {
                        //类名
        String hql = "from User where username = ? and password = ?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username, password);
        return list.size() >0 ? list.get(0):null;

    }

    @Override
    public User findUserByUsername(String username) {
        //类名
        String hql = "from User where username = ?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
        return list.size() >0 ? list.get(0):null;

    }
}
