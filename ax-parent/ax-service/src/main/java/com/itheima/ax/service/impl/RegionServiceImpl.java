package com.itheima.ax.service.impl;

import com.itheima.ax.dao.RegionDao;
import com.itheima.ax.pojo.Region;
import com.itheima.ax.service.IRegionService;
import com.itheima.ax.utils.pageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
    @Autowired
    private RegionDao regionDao;

    public void saveOrUpdate(List<Region> regionList) {
        regionDao.saveOrUpdate(regionList);

    }

    public void pageQuery(pageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    public List<Region> findAll() {
        return regionDao.findAll();
    }

    public List<Region> findListByQ(String q) {
        return regionDao.findListByQ(q);
    }
}
