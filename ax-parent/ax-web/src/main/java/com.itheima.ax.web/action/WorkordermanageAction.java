package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Workordermanage;
import com.itheima.ax.service.WorkordermanageService;
import com.itheima.ax.web.base.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {

    @Autowired
    private WorkordermanageService workordermanageService;

    public String add() throws IOException {
        String f = "0";
        try {
            workordermanageService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();
            f = "1";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset:utf-8");
        ServletActionContext.getResponse().getWriter().print(f);
        return NONE;
    }

}
