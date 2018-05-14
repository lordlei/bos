package com.itheima.ax.service.impl;

import com.itheima.ax.dao.FunctionDao;
import com.itheima.ax.pojo.Function;
import com.itheima.ax.pojo.User;
import com.itheima.ax.service.FunctionService;
import com.itheima.ax.utils.AxUtils;
import com.itheima.ax.utils.pageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionDao functionDao;


    public List<Function> findAll() {
        return functionDao.findAll();
    }

    @Override
    public void save(Function model) {
        functionDao.save(model);
    }

    @Override
    public void pageQuery(pageBean pageBean) {
        functionDao.pageQuery(pageBean);
    }

    @Override
    public List<Function> findMenu() {
        User user = AxUtils.getUser();
        List<Function> list=null;
        if(user.getUsername().equals("admin")){
//            如果是超级管理员,查询所有菜单
           list = functionDao.findAllMenu();
        }else{
//            如果是普通用户,根据Id查询
           list = functionDao.findMenuById(user.getId());
        }
        return list;
    }
}
