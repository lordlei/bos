package com.itheima.ax.service.impl;

import com.itheima.ax.dao.DecidedzoneDao;
import com.itheima.ax.dao.SubareaDao;
import com.itheima.ax.pojo.Decidedzone;
import com.itheima.ax.pojo.Subarea;
import com.itheima.ax.service.DecidedzoneService;
import com.itheima.ax.utils.pageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private SubareaDao subareaDao;

    /**
     *添加定区,同时在分区中添加联系
     */
    public void save(Decidedzone model, String[] subareaId) {
        decidedzoneDao.save(model);
        for (String id : subareaId) {
            Subarea subarea = subareaDao.findById(id);
            subarea.setDecidedzone(model);
        }
    }

    @Override
    public void pageQuery(pageBean pageBean) {
        decidedzoneDao.pageQuery(pageBean);
    }


}
