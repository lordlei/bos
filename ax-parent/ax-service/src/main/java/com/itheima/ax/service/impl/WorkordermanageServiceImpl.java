package com.itheima.ax.service.impl;

import com.itheima.ax.dao.WorkordermanageDao;
import com.itheima.ax.pojo.Workordermanage;
import com.itheima.ax.service.WorkordermanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {
    @Autowired
    private WorkordermanageDao workordermanageDao;

    @Override
    public void save(Workordermanage model) {
        workordermanageDao.save(model);
    }
}
