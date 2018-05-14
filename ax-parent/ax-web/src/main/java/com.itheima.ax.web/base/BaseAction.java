package com.itheima.ax.web.base;

import com.itheima.ax.utils.pageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    public  final  String HOME = "home";
    public  final  String LIST = "list";

    private T model;

    //    分页属性
    protected int page;
    protected int rows;
    protected pageBean pageBean = new pageBean();
    DetachedCriteria detachedCriteria=null;

    public T getModel() {
        return model;
    }

    public BaseAction (){
        //获取父类的Class
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获取运行期的泛型<T>类型
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        //运用反射创建对象
        Class<T> actualTypeArgument = (Class<T>)actualTypeArguments[0];

        detachedCriteria = DetachedCriteria.forClass(actualTypeArgument);
        pageBean.setDetachedCriteria(detachedCriteria);

        try {
//            利用反射，创建对象
            model = actualTypeArgument.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPage(int page) {
        pageBean.setCurrentPage(page);
        this.page = page;
    }

    public void setRows(int rows) {
        pageBean.setPageSize(rows);
        this.rows = rows;
    }

//    字符串转为json
    public void java2Json(Object o,String[] excludes){
        // 使用json-lib将pageBean对象转为json.通过输出流写回页面中
//        JSONObject将单一对象转化成json格式
//        JSONArray----将数组或者集合对象转为json
        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setExcludes设置不传成员
        jsonConfig.setExcludes(excludes);
        String json = JSONObject.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //    字符串转为json 数组
    public void java2Json(List o, String[] excludes){
        //        使用json-lib将pageBean对象转为json.通过输出流写回页面中
//        JSONObject将单一对象转化成json格式
//        JSONArray----将数组或者集合对象转为json
        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setExcludes设置不传成员
        jsonConfig.setExcludes(excludes);
        String json = JSONArray.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
