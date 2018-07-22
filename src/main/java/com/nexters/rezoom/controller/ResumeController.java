package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;
    public void setResumeService(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping("/resume/{resume_id}")
    @ResponseBody
    public Resume getResume(@PathVariable("resume_id") int resumeId, Principal principal){
        int userId = resumeService.getUserId(principal.getName());
        Resume resume = resumeService.getResume(resumeId, userId);
        return resume;
    }

    @PutMapping(value = "/resume/{resume_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateResume(@PathVariable("resume_id") int resumeId, @RequestBody Resume resume, Principal principal){
        int userId = resumeService.getUserId(principal.getName());
        resumeService.updateResume(resume, resumeId, userId);
    }

    @DeleteMapping(value = "/resume/{resume_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResume(@PathVariable("resume_id") int resumeId, Principal principal){
        //이력서 전체를 삭제하는 경우, 문항도 함께 삭제돼야함 - deleteQuestion
        int userId = resumeService.getUserId(principal.getName());
        resumeService.deleteQuestion(resumeId, userId);
        resumeService.deleteResume(resumeId, userId);
    }

    @PostMapping("/resume")
    public void createResume(@RequestBody Resume resume, Principal principal) {
        int userId = resumeService.getUserId(principal.getName());
        resume.setUserId(userId);
        resumeService.createResume(resume);
    }
}
