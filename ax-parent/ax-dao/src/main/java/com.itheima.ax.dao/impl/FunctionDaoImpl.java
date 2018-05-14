package com.itheima.ax.dao.impl;

import com.itheima.ax.dao.FunctionDao;
import com.itheima.ax.pojo.Function;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {
    /**
     * easyui-combotree 重写了findAll
     * @return
     */
    public List<Function> findAll() {
        String hql = " from Function where parentFunction is null";
        return (List<Function>) this.getHibernateTemplate().find(hql);
    }

    /**
     * 根据用户id,查询权限
     */
    @Override
    public List<Function> findFunctionListByUserId(String id) {
        String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id = ? ";
        return (List<Function>) this.getHibernateTemplate().find(hql,id);
    }

    /**
     * 查询所有菜单
     */
    @Override
    public List<Function> findAllMenu() {
        String hql="from Function f where f.generatemenu ='1' order by f.zindex Desc";
        return (List<Function>) this.getHibernateTemplate().find(hql);
    }

    /**
     * 根据Id查询菜单
     * @param id
     */
    @Override
    public List<Function> findMenuById(String id) {
        String hql = "select distinct f from Function f left outer join f.roles r left outer join" +
                " r.users u where u.id = ? and f.generatemenu ='1' order by f.zindex Desc";
        return (List<Function>) this.getHibernateTemplate().find(hql,id);
    }
}
