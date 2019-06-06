package download;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author BURNERON
 */

public class FileListProvider {
    public static List<File> getFiles(ServletContext context) throws IOException {
        List<File> files = new ArrayList<>(); 
        
        File filepath = getFilepath(context);
        
        if(!filepath.exists())
            return files;
        
        File[] fileArray = filepath.listFiles();
        
        if(filepath == null)
            throw new IOException("Cant open filepath");
       
        
        for(File file : fileArray) {
            if(file.isFile())
                files.add(file);
        }
        
        return files;
    }
    
    public static File getFilepath(ServletContext context) {
        String saveDir = context.getInitParameter("FilePath");
        return new File(saveDir);
    } 
    
    public static boolean ensureFilePath(File filepath) throws IOException {
        if(!filepath.exists()) {
            if (!filepath.mkdirs()) {
                throw new IOException("Cant create FilePath");
            }
            return true;
        } else if(!filepath.isDirectory()) {
            throw new IOException("FilePath isnt a directory");
        } else {
            return false;
        }
    }
}
