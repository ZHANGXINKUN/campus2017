import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by del on 2017/6/13.
 */
public class CountLines{

    //总的有效代码行数
    private static int LineCount = 0;
    //java源文件数
    private static int fileCount = 0;

    //用于统计.java文件数量和文件中有效代码的行数
    public static void statistics(File file){
        if(file.isFile()) {
            LineCount += count(file);
            fileCount++;
        } else {
            File[] filenames = file.listFiles(new FileFilter());//过滤文件
            if(filenames != null) {
                for (File ff : filenames) {
                    statistics(ff);
                }
            }
        }
    }

    //统计文件中的有效代码行数排除空行和注释
    private static int count(File file){
        BufferedReader br = null;
        int count = 0;
        try {
            try {
                br = new BufferedReader(new FileReader(file));
                String line = "";
                while((line = br.readLine()) != null){
                    //按照编码规范剔除空行和注释行
                    if (!line.trim().equals("")) {//line.trim()用于去掉字符串前后的空格
                        if(!line.trim().startsWith("/**") && !line.trim().startsWith("*")
                                && !line.trim().startsWith("*/") &&  !line.trim().startsWith("//")){
                            count++;
                        }
                    }
                }

            } finally {
                if(br != null)
                    br.close();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args){
        //String path = "C:\\Users\\del\\Desktop";
        statistics(new File("C:/Users/del/Desktop/abc.java"));
        System.out.println("文件数量：" + fileCount + "\n代码行数：" + LineCount);

    }




}
