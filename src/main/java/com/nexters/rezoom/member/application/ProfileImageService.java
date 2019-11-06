package com.nexters.rezoom.member.application;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by momentjin@gmail.com on 2019-10-23
 * Github : http://github.com/momentjin
 */

@Service
public class ProfileImageService {

    private final static String profileImageStorePath = "./profileImages/";

    public void createProfileImage(Member member, MultipartFile multipartFile) {
        createDirectories();
        createFile(multipartFile, member);
    }

    public File getProfileImage(Member member) {
        File imageStoreDir = new File(profileImageStorePath);
        File[] memberProfileImages = imageStoreDir.listFiles(file -> file.getName().contains(member.getId()));

        if (memberProfileImages == null || memberProfileImages.length == 0)
            throw new RuntimeException("프로필 사진을 찾을 수 없습니다");

        return memberProfileImages[0];
    }

    private void createDirectories() {
        try {
            if (!new File(profileImageStorePath).exists()) {
                Path path = Paths.get(profileImageStorePath);
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 전용 폴더를 생성할 수 없습니다.");
        }
    }

    private void createFile(MultipartFile multipartFile, Member member) {
        File file = FileUtils.convertFile(multipartFile);
        final String fileName = member.getId() + "." + FileUtils.getFileExtension(file);
        File dest = new File(profileImageStorePath + fileName);
        file.renameTo(dest);
    }

}
