package com.itheima.ax.service.impl;

import com.itheima.ax.dao.DecidedzoneDao;
import com.itheima.ax.dao.NoticeBillDao;
import com.itheima.ax.dao.WorkbillDao;
import com.itheima.ax.pojo.*;
import com.itheima.ax.service.NoticeBillService;
import com.itheima.ax.utils.AxUtils;
import com.itheima.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class NoticeBillServiceImpl implements NoticeBillService {
    @Autowired
    private NoticeBillDao noticeBillDao;
    @Autowired
    private ICustomerService iCustomerService;//远程调用crm服务
    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private WorkbillDao workbillDao;



    @Override
    public void save(Noticebill model) {
        User user = AxUtils.getUser();
        model.setUser(user);//设置当前用户
        noticeBillDao.save(model);

        //获取客户的取件地址
        String pickaddress = model.getPickaddress();
        //远程调用crm服务,根据取件地址查询定区id
        String decidedzoneId = iCustomerService.findDecidedzoneByAddress(pickaddress);
        if (decidedzoneId!=null){
            //查询到了定区id,可以完成自动分单
            Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
            Staff staff = decidedzone.getStaff();
            model.setStaff(staff);//业务通知单关联取派员对象
            //设置分单类型为自动分单
            model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
            //为 取派员产生一个工单
            Workbill workbill = new Workbill();
            workbill.setAttachbilltimes(0);//追单次数
            workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建时间，当前系统时间
            workbill.setNoticebill(model);//工单关联页面通知单
            workbill.setRemark(model.getRemark());//备注信息
            workbill.setStaff(staff);//工单关联取派员
            workbill.setType(Workbill.TYPE_1);//工单类型
            workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
            //增加工单
            workbillDao.save(workbill);
            //调用短信平台,发送短信
        }else{
            model.setOrdertype(Noticebill.ORDERTYPE_MAN);
        }
    }
}
