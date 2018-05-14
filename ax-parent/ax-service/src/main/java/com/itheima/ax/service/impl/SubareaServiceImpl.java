package com.itheima.ax.service.impl;

import com.itheima.ax.dao.SubareaDao;
import com.itheima.ax.pojo.Staff;
import com.itheima.ax.pojo.Subarea;
import com.itheima.ax.service.SubareaService;
import com.itheima.ax.utils.pageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaDao subareaDao;
    public void add(Subarea model) {
        subareaDao.save(model);
    }

    @Override
    public void pageQuery(pageBean pageBean) {
        subareaDao.pageQuery(pageBean);
    }

    @Override
    public List<Subarea> findAll() {
        return  subareaDao.findAll();
    }

    @Override
    public List<Subarea> findIsNotAssocation() {
        DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
        dc.add(Restrictions.isNull("decidedzone"));
        return subareaDao.findAllByCriteria(dc);
    }

    @Override
    public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
        dc.add(Restrictions.eq("decidedzone.id",decidedzoneId));
        return subareaDao.findAllByCriteria(dc);
    }
}
