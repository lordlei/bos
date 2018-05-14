package com.itheima.ax.web.action;

import com.itheima.ax.pojo.Region;
import com.itheima.ax.service.IRegionService;
import com.itheima.ax.utils.PinYin4jUtils;
import com.itheima.ax.utils.pageBean;
import com.itheima.ax.web.base.BaseAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
    @Autowired
    private IRegionService regionService;

    //    获取excel文件
    private File regionFile;

    private String q;


    /**
     *分页
     */
    public String pageQuery() throws IOException {
        regionService.pageQuery(pageBean);
        java2Json(pageBean,
                new String[]{"currentPage","pageSize","detachedCriteria","subareas"});
        return NONE;
    }

    /**
     * 查询所有区域,写回json
     * @return
     */
    public String listajax() {
        List<Region> list=null;
        boolean b = StringUtils.isNotBlank(q);
        if (b){
            list=regionService.findListByQ(q);
        }else{
            list=regionService.findAll();
        }
        java2Json(list,new String[]{"subareas"});
        return NONE;
    }



    /**
     * 导入excel文件
     * 导入分区
     */
    public String importXls() throws Exception {
//       包装一个Excel文件对象
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
//        读取文件中第一个sheet标签
        HSSFSheet sheetAt = workbook.getSheetAt(0);
//        包装一个List<Region>集合
        List<Region> regionList = new ArrayList<>();
        for (Row row : sheetAt) {
            int rowNum = row.getRowNum();
//            跳过第一行中的标题
            if (rowNum == 0) {
                continue;
            }
//            获取每一列的值
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
//            注入region中 并将region注入到list集合中
            Region region = new Region(id, province, city, district, postcode, null, null, null);

//            设置简码与城市编码
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            String info = province + city + district;
            String[] head = PinYin4jUtils.getHeadByString(info);
            String shortCode = StringUtils.join(head);
            //城市编码---->>shijiazhuang
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");

            region.setShortcode(shortCode);
            region.setCitycode(cityCode);

            regionList.add(region);
        }
//        执行更新与增加
        regionService.saveOrUpdate(regionList);

        return NONE;
    }





    public File getRegionFile() {
        return regionFile;
    }

    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
