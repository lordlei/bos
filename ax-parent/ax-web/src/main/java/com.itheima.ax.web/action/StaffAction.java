package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Staff;
import com.itheima.ax.service.StaffService;
import com.itheima.ax.utils.pageBean;
import com.itheima.ax.web.base.BaseAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
    @Autowired
    private StaffService staffService;
//前台传过来的当前页,与每页显示条数
//    属性驱动
    private int page;
    private int rows;

    private String ids;

    /**
     * 添加取派员
     */
    public String addStaff() {
        staffService.save(getModel());
        return LIST;
    }

    /**
     * 分页查询
     */
    public String pageQuery() throws IOException {
        pageBean pageBean = new pageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
//       设置离线查询对象
        DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
        pageBean.setDetachedCriteria(dc);
        staffService.pageQuery(pageBean);

//       使用json-lib将pageBean对象转为json.通过输出流写回页面中
//        JSONObject将单一对象转化成json格式
//        JSONArray----将数组或者集合对象转为json
        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setExcludes设置不传成员
        jsonConfig.setExcludes(new String[]{"currentPage","pageSize","detachedCriteria","decidedzones"});
        String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(json);

        return NONE;
    }

    /**
     * 逻辑删除取派员
     */
    @RequiresPermissions("staff")
    public String deleteBatch() {
        staffService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 修改取派员信息
     */
    public String edit() {
        Staff staff=staffService.findById(getModel().getId());

        //使用页面提交的数据进行覆盖
        staff.setName(getModel().getName());
        staff.setTelephone(getModel().getTelephone());
        staff.setHaspda(getModel().getHaspda());
        staff.setStandard(getModel().getStandard());
        staff.setStation(getModel().getStation());
        staffService.update(staff);
        return LIST;
    }

    /**
     * 查询未删除的取派员,返回Json
     */
    public String listAjax(){
        List<Staff> list = staffService.findListNotDelete();
        this.java2Json(list, new String[]{"decidedzones"});
        return NONE;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
