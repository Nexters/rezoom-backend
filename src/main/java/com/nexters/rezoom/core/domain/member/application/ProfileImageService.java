package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.domain.member.domain.Member;
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
            throw new BusinessException(ErrorType.PROFILE_IMG_NOT_FOUND);

        return memberProfileImages[0];
    }

    private void createDirectories() {
        if (FileUtils.isDirectory(profileImageStorePath)) {
            return;
        }

        Path path = Paths.get(profileImageStorePath);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 전용 폴더를 생성할 수 없습니다.");
        }
    }

    private void createFile(MultipartFile multipartFile, Member member) {
        initProfileImage(member);

        File file = FileUtils.convertFile(multipartFile);
        final String fileName = member.getId() + "." + FileUtils.getFileExtension(file);
        File dest = new File(profileImageStorePath + fileName);
        file.renameTo(dest);
    }

    /**
     * 프로필 이미지를 삭제한다.
     */
    private void initProfileImage(Member member) {
        File imageStoreDir = new File(profileImageStorePath);
        File[] memberProfileImages = imageStoreDir.listFiles(file -> file.getName().contains(member.getId()));

        if (memberProfileImages != null && memberProfileImages.length > 0) {
            for (File profileImage : memberProfileImages) {
                profileImage.delete();
            }
        }
    }
}
