package com.itheima.ax.utils;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class pageBean {
//    当前页
    private int currentPage;
//    每页条数
    private int pageSize;
//    总数
    private int total;
//    离线查询对象
    private DetachedCriteria detachedCriteria;
//    每页数据集合
    private List rows;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DetachedCriteria getDetachedCriteria() {
        return detachedCriteria;
    }

    public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
        this.detachedCriteria = detachedCriteria;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
