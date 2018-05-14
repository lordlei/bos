package com.itheima.ax.service;

import com.itheima.ax.pojo.Region;
import com.itheima.ax.utils.pageBean;

import java.util.List;

public interface IRegionService {
    public void saveOrUpdate(List<Region> regionList);

    public void pageQuery(pageBean pageBean);

    public List<Region> findAll();

    public List<Region> findListByQ(String q);
}

