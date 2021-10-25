package com.dependra.importing_exporitng.util;
import com.dependra.importing_exporitng.model.User;
import com.dependra.importing_exporitng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploadHelper {
    private  final String   UPLOAD_DIR="/home/dependra/Downloads/Images";

    @Autowired
    UserService  userService;

    public FileUploadHelper() throws IOException {
    }

    public Boolean uploadFile(MultipartFile file,int id){

        Boolean b=false;

        try {
            InputStream inputStream=file.getInputStream();
            byte data[]=new byte[inputStream.available()];
            inputStream.read(data);
            System.out.println("After reading");

            FileOutputStream fileOutputStream=new FileOutputStream(UPLOAD_DIR+File.separator+file.getOriginalFilename());
            System.out.println("Before Writing");
            fileOutputStream.write(data);

            User user= userService.getUserByid(id);
            userService.updateLocation(user,UPLOAD_DIR+File.separator+file.getOriginalFilename());

            fileOutputStream.flush();
            fileOutputStream.close();
            b =true;

            // this below line is for the same as above lines but using nio
           /// Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }
}
