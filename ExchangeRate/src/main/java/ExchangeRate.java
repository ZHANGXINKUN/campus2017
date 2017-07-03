import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Created by del on 2017/6/23.
 */
public class ExchangeRate {

    //从网页表格中读取数据
    public static List<RateResult> getRate() throws IOException {
        List<RateResult> list = new ArrayList<RateResult>();
        Document doc = Jsoup.connect("http://www.chinamoney.com.cn/fe-c/historyParity.do").post();
        Element HTMLbody = doc.body();
        Element Table = HTMLbody.getElementsByTag("table").last();
        Elements Row = Table.getElementsByTag("tr");
        for(int i = 1; i < Row.size(); i++ ){
            Elements HTMLtd = Row.get(i).getElementsByTag("td");
            String date = HTMLtd.get(0).getElementsByTag("div").get(0).text();
            list.add(new RateResult(date,Double.parseDouble(HTMLtd.get(1).text())));
            list.add(new RateResult(date,Double.parseDouble(HTMLtd.get(2).text())));
            list.add(new RateResult(date,Double.parseDouble(HTMLtd.get(4).text())));
        }
        System.out.println("网页解析完成");
        return list;
    }

    //输出数据到表格中
    public static void printRate(List<RateResult> list, String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet Sheet=wb.createSheet("人民币汇率中间价");
        HSSFRow row1=Sheet.createRow(0);
        //创建添加标题
        row1.createCell(0).setCellValue("日期");
        row1.createCell(1).setCellValue("美元汇率");
        row1.createCell(2).setCellValue("欧元汇率");
        row1.createCell(3).setCellValue("港币汇率");

        //循环创建每行数据
        for(int row=0;row<list.size();row+=3){
            HSSFRow row2=Sheet.createRow(row/3+1);
            row2.createCell(0).setCellValue(list.get(row).getDate());
            row2.createCell(1).setCellValue(list.get(row).getPrice());
            row2.createCell(2).setCellValue(list.get(row+1).getPrice());
            row2.createCell(3).setCellValue(list.get(row+2).getPrice());
        }
        Sheet.setColumnWidth(0,15*256);

        FileOutputStream FO = new FileOutputStream(path + "/ExchangeRate.xls");
        wb.write(FO);
        FO.close();
    }
    public static void main(String[] args){
        String path ="C:/Users/del/Desktop";
        try {
            List<RateResult> result  = getRate();
            printRate(result, path);
            System.out.println("输出到表格结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
