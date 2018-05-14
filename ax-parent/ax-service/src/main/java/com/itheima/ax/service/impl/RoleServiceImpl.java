package com.itheima.ax.service.impl;

import com.itheima.ax.dao.RoleDao;
import com.itheima.ax.pojo.Function;
import com.itheima.ax.pojo.Role;
import com.itheima.ax.service.RoleService;
import com.itheima.ax.utils.pageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(Role model, String functionIds) {
        roleDao.save(model);
        if(StringUtils.isNotBlank(functionIds)){
            String[] split = functionIds.split(",");
            for (String id: split) {
//               手动创建一个权限对象,对象状态为托管对象
                Function function = new Function(id);
//                角色关联权限,托管变为持久态
                model.getFunctions().add(function);
            }
        }
    }

    @Override
    public void pageQuery(pageBean pageBean) {
        roleDao.pageQuery(pageBean);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
