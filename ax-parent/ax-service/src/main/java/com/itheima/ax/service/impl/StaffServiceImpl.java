package com.itheima.ax.service.impl;

import com.itheima.ax.dao.StaffDao;
import com.itheima.ax.pojo.Staff;
import com.itheima.ax.service.StaffService;
import com.itheima.ax.utils.pageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务中默认为(只读为 false)
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    public void save(Staff model) {
        staffDao.save(model);
    }

    @Override
    public void pageQuery(pageBean pageBean) {
        staffDao.pageQuery(pageBean);
    }


    public void deleteBatch(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            staffDao.update("staff.delete", id);
        }
    }

    public Staff findById(String id) {
        Staff staff = staffDao.findById(id);
        return staff;

    }

    public void update(Staff staff) {
        staffDao.update(staff);
    }

    public List<Staff> findListNotDelete() {
        DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
        dc.add(Restrictions.eq("deltag", "0"));
        return staffDao.findAllByCriteria(dc);
    }
}
