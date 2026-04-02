package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.service.apiService.BackgroundRemovalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final BackgroundRemovalApiService bgRemovalApiService;

    private final String BASE_DIR = "C:/hairstyle_app/users/";

    public String processAndSaveHairImage(MultipartFile file, String userId) {
        try {
            String userUploadDir = BASE_DIR + userId + "/uploads/";
            String userOutputDir = BASE_DIR + userId + "/outputs/";

            String savedName = saveFile(file, userUploadDir);
            if (savedName == null) return null;

            byte[] processedBytes = bgRemovalApiService.removeBackground(userUploadDir + savedName);

            if (processedBytes != null) {
                File outputFolder = new File(userOutputDir);
                if (!outputFolder.exists()) {
                    outputFolder.mkdirs();
                }

                String fileNameWithoutExt = savedName.substring(0, savedName.lastIndexOf("."));
                String outputFileName = "processed_" + fileNameWithoutExt + ".png";

                saveBytesToFile(processedBytes, userOutputDir + outputFileName);

                return userId + "/outputs/" + outputFileName;
            }

            return userId + "/" + savedName;

        } catch (Exception e) {
            System.err.println("파일 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) return null;

        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "_" + originalFileName;

        File savedPath = new File(uploadPath + savedName);
        file.transferTo(savedPath);

        return savedName;
    }

    private void saveBytesToFile(byte[] data, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
            fos.flush();
        }
    }
}