package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.nexters.rezoom.config.security.SecurityConstants.SESSION_USER;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    HttpSession session;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public int createResume(@RequestBody Resume resume) {
        resume.setUserId(getUserId());
        resumeService.createResume(resume);
        return resume.getResumeId();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Resume> getResumeList() {
        List<Resume> resumes = resumeService.getAllResume(getUserId());
        return resumes;
    }

    @GetMapping("/{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public Resume getResume(@PathVariable int resumeId){
        int userId = 15;
        Resume resume = resumeService.getResume(resumeId, userId);
        return resume;
    }

    @PutMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResume(@PathVariable int resumeId, @RequestBody Resume resume){
        resumeService.updateResume(resume, resumeId, getUserId());
    }

    @DeleteMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(@PathVariable int resumeId){
        resumeService.deleteResume(resumeId, getUserId());
    }

    private int getUserId() {
        int userId = ((User)session.getAttribute(SESSION_USER)).getUserId();
        return userId;
    }
}
