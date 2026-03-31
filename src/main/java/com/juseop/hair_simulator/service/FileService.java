package com.juseop.hair_simulator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private final String uploadDir = "C:/hairstyle_app/uploads/";

    public String saveFile(MultipartFile file) throws IOException{
        File folder = new File(uploadDir);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String savedName = originalFileName + uuid;

        File savedPath = new File(uploadDir + savedName);
        file.transferTo(savedPath);

        return savedName;
    }
}
