package weShare.sharehappy.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.Exception.FileStoreException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LocalFileManagementService {

    public void storeFile(MultipartFile multipartFile,String directory){
        try {
            String fileFullPath = directory+multipartFile.getOriginalFilename()+ LocalDateTime.now();
            multipartFile.transferTo(new File(fileFullPath));
        }catch (IOException e){
            throw new FileStoreException(multipartFile.getOriginalFilename());
        }
    }

    public void deleteFile(String path){

    }

    public void storeFiles(List<MultipartFile> multipartFile, String directory){
        multipartFile.stream().forEach(file -> {

        });
    }
}
