package com.itheima.ax.realm;

import com.itheima.ax.dao.FunctionDao;
import com.itheima.ax.dao.IUserDao;
import com.itheima.ax.pojo.Function;
import com.itheima.ax.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BosRealm extends AuthorizingRealm {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private FunctionDao functionDao;

    /**
     * 认证
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken mytoken= (UsernamePasswordToken) token;
        String username = mytoken.getUsername();
        //根据用户名查询数据库中的密码
        User user=userDao.findUserByUsername(username);
        if (user==null){
            //用户名不存在
            return null;
        }
        //如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        获取当前登录用户对象
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        根据当前登录用户查询数据库,获取实际对应的权限
        List<Function> list=null;
        if (user.getUsername().equals("admin")){
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
//            超级管理员内置用户,查询所有权限数据
            list = functionDao.findAllByCriteria(detachedCriteria);
        }else {
            list = functionDao.findFunctionListByUserId(user.getId());
        }
        for (Function function:list) {
            info.addStringPermission(function.getCode());
        }
//        info.addStringPermission("staff-list");
        //TODO 后期需要修改为根据当前用户查询数据库,获取实际对应的权限
        return info;
    }

}
