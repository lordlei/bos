package com.itheima.ax.service;

import com.itheima.ax.pojo.Staff;
import com.itheima.ax.utils.pageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface StaffService{
   public void save(Staff model);


   public void pageQuery(pageBean pageBean);

    public void deleteBatch(String ids);

    public Staff findById(String id);

    public void update(Staff staff);

   public List<Staff> findListNotDelete();
}
