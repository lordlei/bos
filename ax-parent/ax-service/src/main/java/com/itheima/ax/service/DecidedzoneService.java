package com.itheima.ax.service;

import com.itheima.ax.pojo.Decidedzone;
import com.itheima.ax.utils.pageBean;

public interface DecidedzoneService {

   public void save(Decidedzone model, String[] subareaId);

    public  void pageQuery(pageBean pageBean);
}
