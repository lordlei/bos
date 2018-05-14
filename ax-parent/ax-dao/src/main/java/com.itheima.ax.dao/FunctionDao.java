package com.itheima.ax.dao;

import com.itheima.ax.dao.base.IBaseDao;
import com.itheima.ax.pojo.Function;

import java.util.List;

public interface FunctionDao extends IBaseDao<Function> {

    List<Function> findFunctionListByUserId(String id);

    List<Function> findAllMenu();

    List<Function> findMenuById(String id);

}
