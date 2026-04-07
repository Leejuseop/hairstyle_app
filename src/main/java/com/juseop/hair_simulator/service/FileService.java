package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.service.apiService.BackgroundRemovalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final BackgroundRemovalApiService bgRemovalApiService;

    private final String BASE_DIR = "C:/hairstyle_app/users/";
    private final String DATA_PATH = "C:/hairstyle_app/data/hairstyle";

    public List<String> getStyleSampleList(){
        File directory = new File(DATA_PATH);

        return Arrays.stream(directory.list()).collect(Collectors.toList());
    }

    public String removeBackground(MultipartFile file, String userId) {
        try {
            String userUploadDir = BASE_DIR + userId + "/uploads/";
            String userOutputDir = BASE_DIR + userId + "/outputs/bgremoved/";

            String savedName = saveFile(file, userUploadDir);
            if (savedName == null) return null;

            byte[] processedBytes = bgRemovalApiService.callApi(userUploadDir + savedName);

            if (processedBytes != null) {
                String outputFileName = "processed_" + UUID.randomUUID().toString() + ".png";
                saveBytesToFile(processedBytes, userOutputDir + outputFileName);

                return "/storage/" + userId + "/outputs/bgremoved/" + outputFileName;
            }
            return "/storage/" + userId + "/uploads/" + savedName;
        } catch (Exception e) {
            return null;
        }
    }

    public String saveFile(MultipartFile file, String savePath) throws IOException {
        File folder = new File(savePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) return null;

        int lastDotIndex = originalFileName.lastIndexOf(".");

        String fileNameOnly;
        String extension;

        if (lastDotIndex == -1) {
            fileNameOnly = originalFileName;
            extension = "";
        } else {
            fileNameOnly = originalFileName.substring(0, lastDotIndex);
            extension = originalFileName.substring(lastDotIndex);
        }

        String uuid = UUID.randomUUID().toString();
        String savedName = fileNameOnly + "_" + uuid + extension;

        File savedPath = new File(savePath + savedName);
        file.transferTo(savedPath);

        return savedName;
    }

    public void saveBytesToFile(byte[] data, String savePath) throws IOException {
        File file = new File(savePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.flush();
        }
    }


}