package weShare.sharehappy.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.Exception.FileStoreException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class LocalFileManagementService {

    public String storeRandomNameFile(MultipartFile multipartFile,String directory){
        try {
            String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID()+extension;
            multipartFile.transferTo(new File(directory,fileName));
            return fileName;
        }catch (IOException e){
            throw new FileStoreException(multipartFile.getOriginalFilename());
        }
    }

    public void deleteFile(String path){
        File file = new File(path);
        file.delete();
    }

    public int countDirectoryFiles(String directory){
        File file = new File(directory);
        return file.listFiles().length;
    }

    public String mkdir(String parentPath,String dir){
        File file = new File(parentPath,dir);
        if(!file.exists()){
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

}
