package com.itheima.ax.dao;

import com.itheima.ax.dao.base.IBaseDao;
import com.itheima.ax.pojo.Region;

import java.util.List;

public interface RegionDao extends IBaseDao<Region> {

   public List<Region> findListByQ(String q);
}
