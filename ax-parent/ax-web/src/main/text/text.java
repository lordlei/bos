import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class text {
    @Test
    public void fun1() throws IOException {
        String path = "E:\\学习视频\\11.物流BOS系统\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls";
        //包装一个Excel文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(new File(path)));
//        读取文件中第一个sheet标签
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
        //遍历标签页中所有的行
        for (Row row :sheetAt) {
            System.out.println();
            for (Cell cell:row){
                String value = cell.getStringCellValue();
                System.out.println(value+"");
            }

        }

    }
}
