package com.itheima.ax.dao.impl;

import com.itheima.ax.dao.RoleDao;
import com.itheima.ax.pojo.Role;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
}
