package com.itheima.ax.dao.base;

import com.itheima.ax.pojo.Role;
import com.itheima.ax.utils.pageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 *  持久层通用接口
 *
 * @author ax
 * @param  <T>
 * */
public interface IBaseDao<T> {


    /**
     * 保存
     * */
    public  void save(T entity);

    /**
     * 删除
     * */
    public  void delete(T entity);

    /**
     * 更新
     * */
    public void  update(T entity);

    /**
     * 根据 id  查询对象
     * */                     //序列化
    public T findById(Serializable id);

    /**
     * 查询所有
     * */
    public List<T> findAll();


    /**
     * 根据条件修改
     * @param queryName
     * @param objects
     */
    public void update(String queryName, Object... objects);

    public List<Role> pageQuery(pageBean pageBean);

    public void saveOrUpdate(List<T> regionList);

    public List<T> findAllByCriteria(DetachedCriteria dc);
}
