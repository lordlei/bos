package crm.service;

import java.util.List;

import javax.jws.WebService;

import crm.domain.Customer;

@WebService
public interface ICustomerService {
    public List<Customer> findAll();
    //查找未关联的
    public List<Customer> findListNotAssociation();
    //查找以及关联的
    public List<Customer> findListHasAssociation(String decidedzoneId);
    //定区关联客户
    public void assigncustomerstodecidedzone(String decidedzoneId,Integer[] customerIds);
    //根据客户手机号查询客户信息
    public Customer findCustomerByTelephone(String telephone);
    //根据客户地址查询定区Id
    public String findDecidedzoneByAddress(String address);
}
