package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Decidedzone;
import com.itheima.ax.service.DecidedzoneService;
import com.itheima.ax.web.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope("prototype") //原型注解，扫描时不创建对象
public class DecidedzoneAction extends BaseAction<Decidedzone> {
    @Autowired
    private DecidedzoneService decidedzoneService;

    private String[] subareaId;

    /**
     *添加定区,同时在分区中添加联系
     */
    public String add(){
        decidedzoneService.save(getModel(),subareaId);
        return LIST;
    }

    /**
     * 分页
     */
    public String pageQuery() {
        decidedzoneService.pageQuery(pageBean);
        java2Json(pageBean, new String[]{"currentPage", "pageSize", "detachedCriteria", "decidedzones", "subareas"});
        return NONE;
    }
    @Autowired
    private ICustomerService proxy;

    /**用webService
     * 用代理对象查询未关联的对象
     */
    public String findListNotAssociation(){
        List<Customer> notAssociation = proxy.findListNotAssociation();
        java2Json(notAssociation,new String[]{});
        return NONE;
    }

    /**用webService
     * 用代理对象查询已经关联的对象
     */
    public String findListHasAssociation(){
        List<Customer> hasAssociation = proxy.findListHasAssociation(getModel().getId());
        java2Json(hasAssociation,new String[]{});
        return NONE;
    }

    //客户id
    private List<Integer> customerIds;
    /**
     * 定区关联客户
     */
    public String assigncustomerstodecidedzone(){
        proxy.assigncustomerstodecidedzone(getModel().getId(),customerIds);
        return LIST;
    }

    public String[] getSubareaId() {
        return subareaId;
    }

    public void setSubareaId(String[] subareaId) {
        this.subareaId = subareaId;
    }

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }
}
