package com.nexters.rezoom.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by momentjin@gmail.com on 2019-10-07
 * Github : http://github.com/momentjin
 */
public class FileUtils {

    public static String getFileExtension(File file) {
        if (!file.getName().contains("."))
            throw new RuntimeException("파일 확장자가 존재하지 않습니다.");

        return file.getName().split("\\.")[1];
    }

    public static File convertFile(MultipartFile multipartFile) {
        String fileName = getMultipartFileName(multipartFile);

        try {
            File convertFile = new File(fileName);
            convertFile.createNewFile();

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return convertFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isDirectory(String filePath) {
        File resource = new File(filePath);
        return resource.exists() && resource.isDirectory();
    }

    private static String getMultipartFileName(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("파일명이 존재하지 않습니다.");
        }

        return fileName;
    }
}
