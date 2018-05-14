package com.itheima.ax.web.action;


import com.itheima.ax.pojo.Region;
import com.itheima.ax.pojo.Subarea;
import com.itheima.ax.service.SubareaService;
import com.itheima.ax.utils.FileUtils;
import com.itheima.ax.web.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype") //原型注解，扫描时不创建对象
public class SubareaAction extends BaseAction<Subarea> {
    @Autowired
    private SubareaService subareaService;

    /**
     * 添加分区
     */
    public String add() {
        subareaService.add(getModel());
        return LIST;
    }

    /**
     * 分页
     */
    public String pageQuery() {
        DetachedCriteria dc = pageBean.getDetachedCriteria();
//      动态添加过滤条件
        String addresskey = getModel().getAddresskey();
        if(StringUtils.isNotBlank(addresskey)){
            dc.add(Restrictions.like("addresskey","%"+addresskey+"%"));
        }
        Region region = getModel().getRegion();
        if (region!=null){
            String city = region.getCity();
            String district = region.getDistrict();
            String province = region.getProvince();
            //添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
            //参数一：分区对象中关联的区域对象属性名称
            //参数二：别名，可以任意
            dc.createAlias("region", "r");
            if(StringUtils.isNotBlank(city)){
                dc.add(Restrictions.like("r.city","%"+city+"%"));
            }
            if(StringUtils.isNotBlank(district)){
                dc.add(Restrictions.like("r.district","%"+district+"%"));
            }
            if(StringUtils.isNotBlank(province)){
                dc.add(Restrictions.like("r.province","%"+province+"%"));
            }
        }

        //平常使用懒加载时:当执行一条sql语句时,session并没有关闭,
            //例如执行后返回一个对象,而对象中有另一个对象,此时该对象是代理对象,当JSP在渲染的过程中实际是发出sql查询关联的对象,因为此时是代理对象..当jsp渲染完毕后 session才关闭
            // 要解决在Action中将对象转成json对象,则不能是代理对象,所以需要关闭懒加载,使得没到搜到时就将所有的对象获取到.无代理对象
        subareaService.pageQuery(pageBean);
        java2Json(pageBean, new String[]{"currentPage", "pageSize", "detachedCriteria", "decidedzone", "subareas"});
        return NONE;
    }


    /**
     * 导出excel文件
     */
    public String exportXls() throws IOException {
//        1查询所有的分区数据
        List<Subarea> list=subareaService.findAll();
//        2使用POI将数据写入到excel文件中

//      在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
//      创建一个标题页
        HSSFSheet sheet = workbook.createSheet("分区数据");
//      创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("分区编号");
        headRow.createCell(1).setCellValue("开始编号");
        headRow.createCell(2).setCellValue("结束编号");
        headRow.createCell(3).setCellValue("位置信息");
        headRow.createCell(4).setCellValue("省市区");

        for (Subarea s : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(s.getId());
            dataRow.createCell(1).setCellValue(s.getStartnum());
            dataRow.createCell(2).setCellValue(s.getEndnum());
            dataRow.createCell(3).setCellValue(s.getPosition());
            dataRow.createCell(4).setCellValue(s.getRegion().getName());
        }

//        3使用输出流进行文件下载(一个流,两个头)

        String filename = "分区数据.xls";
        //获取文件类型
        String type = ServletActionContext.getServletContext().getMimeType(filename);
        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
        //设置文件类型
        ServletActionContext.getResponse().setContentType(type);


        //获取客户端浏览器类型
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        //针对不同浏览器,实现不同的编码 将filename重新修饰下
        filename=FileUtils.encodeDownloadFilename(filename, agent);//filename文件名,agent浏览器类型
        ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
        workbook.write(outputStream);

        return NONE;
    }

    /**
     * 添加定区时,查询未关联的分区
     */
    public String listAjax(){
        List<Subarea> list=subareaService.findIsNotAssocation();
        java2Json(list,new String[]{"decidedzone","region"});
        return NONE;
    }
    //属性驱动
    private String decidedzoneId;

    /**
     * 定区查询包含的分区
     */
    public String findListByDecidedzoneId(){
        List<Subarea> list=subareaService.findListByDecidedzoneId(decidedzoneId);
        java2Json(list,new String[]{"decidedzone","subareas"});
        return NONE;
    }

    public String getDecidedzoneId() {
        return decidedzoneId;
    }

    public void setDecidedzoneId(String decidedzoneId) {
        this.decidedzoneId = decidedzoneId;
    }
}
