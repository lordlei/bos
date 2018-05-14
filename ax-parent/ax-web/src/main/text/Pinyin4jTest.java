import com.itheima.ax.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class Pinyin4jTest {
    @Test
    public void fun1(){
        //河北省	石家庄市	桥西区
        String province = "河北省";
        String city = "石家庄市";
        String district = "桥西区";
        //简码---->>HBSJZQX
        province = province.substring(0, province.length() - 1);
        city = city.substring(0, city.length() - 1);
        district = district.substring(0, district.length() - 1);

        String info=province+city+district;

        String[] head = PinYin4jUtils.getHeadByString(info);
        String shortCode = StringUtils.join(head);
        System.out.println(shortCode);//HBSJZQX

        //城市编码---->>shijiazhuang
        String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
        System.out.println(cityCode);//shijiazhuang
    }
}
