package com.itheima.ax.service;

import com.itheima.ax.pojo.Subarea;
import com.itheima.ax.utils.pageBean;

import java.util.List;

public interface SubareaService {

    public void add(Subarea model);

    public void pageQuery(pageBean pageBean);

    public List<Subarea> findAll();

    public List<Subarea> findIsNotAssocation();

    public List<Subarea> findListByDecidedzoneId(String decidedzoneId);
}
