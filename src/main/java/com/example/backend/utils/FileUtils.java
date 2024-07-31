package com.example.backend.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {
    private static final String UPLOAD_DIR = "D:\\projects\\demo\\file-system";

    public static String uploadFile(MultipartFile file) {
        try {
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path destinationFilePath = uploadPath.resolve(fileName);
            file.transferTo(destinationFilePath.toFile());

            return "/images/" + fileName;
        } catch (IOException e) {
            // 파일 저장 중 문제가 발생했을 때 여기서 로그를 찍거나 대응할 수 있음
            System.err.println("Failed to upload file: " + e.getMessage());
            return null;
        }
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
