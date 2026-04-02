package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.service.apiService.BackgroundRemovalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream; // 추가됨
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final BackgroundRemovalApiService bgRemovalApiService;

    private final String UPLOAD_DIR = "C:/hairstyle_app/uploads/";
    private final String OUTPUT_DIR = "C:/hairstyle_app/outputs/";

    public String processAndSaveHairImage(MultipartFile file) {
        try {
            String originalFileName = saveFile(file);
            if (originalFileName == null) return null;

            byte[] processedBytes = bgRemovalApiService.removeBackground(UPLOAD_DIR + originalFileName);

            if (processedBytes != null) {
                File outputFolder = new File(OUTPUT_DIR);
                if(!outputFolder.exists()) outputFolder.mkdirs();

                String outputFileName = "processed_" + originalFileName + ".png";
                saveBytesToFile(processedBytes, OUTPUT_DIR + outputFileName);

                return outputFileName;
            }
            return originalFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveBytesToFile(byte[] data, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        File folder = new File(UPLOAD_DIR);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "_" + originalFileName;

        File savedPath = new File(UPLOAD_DIR + savedName);
        file.transferTo(savedPath);

        return savedName;
    }
}