/**
 * Created by del on 2017/7/3.
 */
public class RateResult {
    /**
     * 定义一个日期 汇率类
     */

        String date;
        double price;


        public RateResult(String date, double price){
            this.date = date;
            this.price = price;
        }
        public double getPrice() {
            return price;
        }
        public String getDate() {
            return date;
        }
}
