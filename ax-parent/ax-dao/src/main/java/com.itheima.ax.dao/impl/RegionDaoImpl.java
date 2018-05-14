package com.itheima.ax.dao.impl;

import com.itheima.ax.dao.RegionDao;
import com.itheima.ax.pojo.Region;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {
    @Override
    public List<Region> findListByQ(String q) {
        String hql="from Region r where r.citycode like ? " +
                "or r.shortcode like ? or r.province like ? " +
                "or r.city like ? or r.district like ?";
        List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
        return list;
    }
}
