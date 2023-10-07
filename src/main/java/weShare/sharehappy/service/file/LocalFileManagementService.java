package weShare.sharehappy.service.file;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.file.FileDeleteException;
import weShare.sharehappy.Exception.file.FileStoreException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class LocalFileManagementService{

    public void deleteFile(String parentPath,String fileName){
        File file = new File(parentPath,fileName);
        file.delete();
    }
    public void cleanDirectory(String directoryPath) {
        try {
            File file = new File(directoryPath);
            FileUtils.cleanDirectory(file);
        }catch (IOException e){
            throw new FileDeleteException(Collections.singletonList(directoryPath.substring(directoryPath.lastIndexOf("/")+1)));
        }
    }
    public List<File> getDirectoryFiles(String directoryPath){
        File file = new File(directoryPath);
        return FileUtils.listFiles(file,null,true).stream().filter(f -> f.isFile()).collect(Collectors.toList());
    }

    public String storeFile(InputStream inputStream,String path,String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String storeFileName = UUID.randomUUID()+extension;
            File file = new File(path,storeFileName);
            FileUtils.copyInputStreamToFile(inputStream,file);
            return storeFileName;
        }catch (IOException e){
            throw new FileStoreException(Collections.singletonList(fileName));
        }
    }
    public int countDirectoryFiles(String directoryPath){
        File file = new File(directoryPath);
        return (int) FileUtils.listFiles(file,null,true).stream().filter(f -> f.isFile()).count();
    }

    public String mkdir(String parentPath,String dir){
        File file = new File(parentPath,dir);
        if(!file.exists()){
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

}
