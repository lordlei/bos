package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Function;
import com.itheima.ax.service.FunctionService;
import com.itheima.ax.web.base.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype") //原型注解，扫描时不创建对象
public class FunctionAction extends BaseAction<Function> {
    @Autowired
    private FunctionService functionService;

    /**
     * 查询所有权限功能
     */
    public String listAjax(){
        List<Function> list=functionService.findAll();
        this.java2Json(list,new String[]{"parentFunction","roles"});
        return NONE;
    }

    /**
     * 添加权限
     */
    public String add(){
        if(getModel().getParentFunction()!=null&&getModel().getParentFunction().getId().equals("")){
            getModel().setParentFunction(null);
        }
       functionService.save(getModel());
        return LIST;
    }

    /**
     * 分页
     */
    public String pageQuery(){
//       当model中的元素名称与属性驱动元素名称一样时,系统会把值优先付给model中的元素
        String page1=getModel().getPage();
        pageBean.setCurrentPage(Integer.parseInt(page1));
        functionService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"parentFunction","roles","children"});
        return NONE;
    }

    /**
     * 根据当前登录人查询菜单,返回json
     */
    public String findMenu(){
        List<Function> list=functionService.findMenu();
        this.java2Json(list,new String[]{"parentFunction","roles","children"});
        return NONE;
    }




}
