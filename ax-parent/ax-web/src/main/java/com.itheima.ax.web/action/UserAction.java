package com.itheima.ax.web.action;

import com.itheima.ax.pojo.User;
import com.itheima.ax.service.IUserService;
import com.itheima.ax.utils.AxUtils;
import com.itheima.ax.utils.MD5Utils;
import com.itheima.ax.web.base.BaseAction;
import com.itheima.crm.service.ICustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.io.IOException;

/**
 * 用户Action   继承了BaseAction
 */
@Controller
@Scope("prototype") //原型注解，扫描时不创建对象
public class UserAction extends BaseAction<User> {
    @Autowired
    private ICustomerService iCustomerService;

    //属性驱动
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Autowired
    private IUserService userService;

    /**
     * 登录,使用shiro框架提供的方式进行验证
     */
    public String login() {
//        List<Customer> list = iCustomerService.findAll();
//        for (Customer c : list) {
//            System.out.println(c);
//        }
        //1.获取验证码
        String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
//        判断验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && validatecode.equals(checkcode)) {
//            使用shiro框架提供的方式进行验证
            Subject subject = SecurityUtils.getSubject();//获得当前登录用户对像,现在状态是未认证
//            用户名密码令牌
            AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), MD5Utils.md5(getModel().getPassword()));
            try {
                subject.login(token);//subject 调用 securityManager 中的realm
                User user = (User) subject.getPrincipal();//验证时从数据库中拿到的user
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);//最后再放入session中
            } catch (Exception e) {
                e.printStackTrace();
                return LOGIN;
            }
            return HOME;
        } else {
//        验证码错误
            this.addActionError("验证码输入有误");
            return LOGIN;
        }

    }

    /**
     * 登录
     */
    public String login_bac() {
//        List<Customer> list = iCustomerService.findAll();
//        for (Customer c : list) {
//            System.out.println(c);
//        }
        //1.获取验证码
        String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
//        判断验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && validatecode.equals(checkcode)) {
//           验证码正确
//           登录
            User user = userService.login(this.getModel()); //this.getModel 调用方法获取模型对象 User ，该方法在BaseAction中有实现
            if (user != null && StringUtils.isNotBlank(user.getId())) {
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
                return HOME;
            } else {
                this.addActionError("用户名或密码有误");
                return LOGIN;
            }
        } else {
//        验证码错误
            this.addActionError("验证码输入有误");
            return LOGIN;
        }

    }

    /**
     * 退出
     */
    public String logout() {
        ServletActionContext.getRequest().getSession().invalidate(); //手动销毁session
        return LOGIN;
    }


    /**
     * 修改密码
     */
    public String editPassword() throws IOException {
        User user = AxUtils.getUser();
        String i = "1";
        try {
            userService.editPassword(user.getId(), getModel().getPassword());
        } catch (Exception e) {
            i = "0";
            e.printStackTrace();
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
//        向前台写入结果 为1则成功 ,为0则失败
        ServletActionContext.getResponse().getWriter().print(i);
        return NONE;
    }

    /**
     * 添加用户
     */

//   角色id数组
    private String[] roleIds;

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String add() {
        userService.save(getModel(),roleIds);
        return LIST;
    }

    /**
     * 分页查询
     */
    public String pageQuery() {
        userService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"noticebills","roles"});
        return NONE;
    }

}
