package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Noticebill;
import com.itheima.ax.service.NoticeBillService;
import com.itheima.ax.web.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class NoticeBillAction extends BaseAction<Noticebill> {
//    注入crm客户端代理对象
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private NoticeBillService noticeBillService;

    /**
     * 回显信息:通过电话查找客户
     */
    public String findCustomerByTelephone(){
        String telephone = getModel().getTelephone();
        Customer customer = iCustomerService.findCustomerByTelephone(telephone);
        java2Json(customer,new String[]{});
        return  NONE;
    }

    /**
     * 添加业务通知单
     */
    public String add(){
        noticeBillService.save(getModel());
        return "noticeBill_add";
    }


}
