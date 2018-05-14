package com.itheima.ax.service;

import com.itheima.ax.pojo.Function;
import com.itheima.ax.utils.pageBean;

import java.util.List;

public interface FunctionService {

    List<Function> findAll();

    void save(Function model);

    void pageQuery(pageBean pageBean);

    List<Function> findMenu();

}

