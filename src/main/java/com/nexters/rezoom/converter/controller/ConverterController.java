package com.nexters.rezoom.converter.controller;

import com.nexters.rezoom.converter.domain.ConverterService;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@RestController
@RequestMapping("/converter")
public class ConverterController {

    private ConverterService service;

    public ConverterController(ConverterService service) {
        this.service = service;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void convertFromFileToCoverletter(@AuthenticationPrincipal Member member, @RequestPart(name = "file") MultipartFile[] files) {
        service.convertFromFileToCoverletter(member, files);
    }

}
