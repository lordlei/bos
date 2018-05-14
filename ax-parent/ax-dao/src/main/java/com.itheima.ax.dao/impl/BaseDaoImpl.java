package com.itheima.ax.dao.impl;

import com.itheima.ax.dao.base.IBaseDao;
import com.itheima.ax.pojo.Role;
import com.itheima.ax.utils.pageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

//注入SessionFactory
    @Resource  //Resource 可以根据对象名注入，也可根据类型注入    （根据类型注入Spring 工厂中的会话工厂对象sessionFactory）
    public void setMySessionFactory(SessionFactory sessionFactory) {
         super.setSessionFactory(sessionFactory);
    }

    private Class<T>  entityClass ;

    public BaseDaoImpl () {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass(); //获取父类类型，并强转为ParameterizedType类型

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();//获取父类声明上的泛型数组

        entityClass = (Class<T>) actualTypeArguments[0];  //获取数组中的第一个值，并强转为 Class类型
    }

    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    public T findById(Serializable id) {

        return this.getHibernateTemplate().get(entityClass,id);//获取

    }

  
    public List<T> findAll() {

        String hql = " from  "+entityClass.getSimpleName();

        return (List<T>) this.getHibernateTemplate().find(hql);

    }


    public void update(String queryName, Object... objects) {
        Session currentSession = this.getSessionFactory().getCurrentSession();
        Query query = currentSession.getNamedQuery(queryName);
        int i=0;
//        添加条件
        for (Object object: objects){
            query.setParameter(i++,object);
        }
//        执行更新
        query.executeUpdate();
    }

//    分页查询
    public List<Role> pageQuery(pageBean pageBean) {
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
//        查询总数
        DetachedCriteria dc = pageBean.getDetachedCriteria();
        dc.setProjection(Projections.rowCount());
        List<Long> total = (List<Long>) this.getHibernateTemplate().findByCriteria(dc);
        Long l = total.get(0);
        pageBean.setTotal(l.intValue());
//      把条件消除
        dc.setProjection(null);
//       指定hibernate框架封装对象的方式
        dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);

//        查询当前页的集合
        int firstResult =(currentPage-1)*pageSize;
        int maxResults=pageSize;
        List<T> list = (List<T>) this.getHibernateTemplate().findByCriteria(dc, firstResult, maxResults);
        pageBean.setRows(list);

        return null;
    }

    public void saveOrUpdate(List<T> regionList) {
        for (T t: regionList) {
            this.getHibernateTemplate().saveOrUpdate(t);
        }
    }

    @Override
    public List<T> findAllByCriteria(DetachedCriteria dc) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
    }


}
