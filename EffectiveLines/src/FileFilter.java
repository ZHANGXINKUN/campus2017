import java.io.FilenameFilter;
import java.io.File;

/**
 * Created by del on 2017/6/12.
 */
public class FileFilter implements FilenameFilter {

    //过滤.java文件
    @Override
    public boolean accept(File path, String name) {
        File file = new File(path, name);
        if (file.getName().toLowerCase().endsWith(".java")) {
            return true;
        }
        if (file.isDirectory()) {
            return true;
        }
        return false;
    }
}