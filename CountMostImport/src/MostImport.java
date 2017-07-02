import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
/**
 * Created by del on 2017/6/15.
 */
public class MostImport {
    //使用hashmap来存储
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private void GetFile(String filePath) {
        File file = new File(filePath);
        DepthSearch(file);
    }
    //查找文件
    public String DepthSearch(File f) {
        if (f.isDirectory()) {//如果是文件夹
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    DepthSearch(file);
                }
            }
        } else {//如果是文件
            try {
                findNumberofImport(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //  查找Import语句
    private void findNumberofImport(File f) throws Exception {
        InputStream in = new FileInputStream(f);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String str = "";
        while ((str = reader.readLine()) != null) {
            if(str.startsWith("package")||"".equals(str)){
                continue;
            }
            if (str.startsWith("import")) {
                str = str.substring(7);
                Integer value = map.get(str);
                if(value==null){
                    map.put(str, 1);
                }else{
                    map.put(str, value+1);
                }
            } else {
                break;
            }
        }
        reader.close();
    }
    //找到使用最多的Import语句
    private String getMostImportFile(){
        String result = "";
        int max = 0;
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String  key = iter.next();
            //System.out.println(key+" 出现次数："+map.get(key));
            if(map.get(key)>max){
                max = map.get(key);
                result = key;
            }
        }
        //对value进行排序
        // 将map.entrySet()转换成list
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        int i = 0;
        System.out.print("引用次数最多的十个类：\n");
        for(Map.Entry<String,Integer> mapping:list){
            if(i < 10){
                System.out.println("引用类：" + mapping.getKey() + " 次数：" + mapping.getValue());
                i++;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String path = "C:/Users/del/Desktop/abc.java";
        MostImport most = new MostImport();
        most.GetFile(path);
        String result= most.getMostImportFile();
        System.out.println("import最多的类:\n"+result);
    }


}
