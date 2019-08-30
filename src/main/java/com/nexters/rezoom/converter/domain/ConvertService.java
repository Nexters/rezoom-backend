package com.nexters.rezoom.converter.domain;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@Service
public class ConvertService {

    private CoverletterRepository coverletterRepository;

    public ConvertService(CoverletterRepository coverletterRepository) {
        this.coverletterRepository = coverletterRepository;
    }

    public void convertFromFileToCoverletter(Member member, MultipartFile[] files) {
        for (MultipartFile multipartFile : files) {
            File file = this.convertFile(multipartFile);
            String fileExtension = file.getName().split("\\.")[1];
            CoverletterConverter converter = ConverterFactory.createConverterByExtension(fileExtension, file);
            Coverletter coverletter = converter.convert();
            coverletter.setMember(member);

            coverletterRepository.save(coverletter);
            file.delete();
        }
    }

    private File convertFile(MultipartFile multipartFile) {
        try {
            File convertFile = new File(multipartFile.getOriginalFilename());
            convertFile.createNewFile();

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return convertFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
