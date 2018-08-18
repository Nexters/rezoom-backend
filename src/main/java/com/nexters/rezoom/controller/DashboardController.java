package com.nexters.rezoom.controller;

import com.nexters.rezoom.dto.RecentClickResumeDTO;
import com.nexters.rezoom.service.RecentClickResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-19.
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    RecentClickResumeService recentClickResumeService;

    @GetMapping("/recent")
    @ResponseStatus(HttpStatus.OK)
    public List<RecentClickResumeDTO> getResumeDTOList(Principal principal) {
        return recentClickResumeService.selectRecentResumeClick(principal.getName());
    }

}
