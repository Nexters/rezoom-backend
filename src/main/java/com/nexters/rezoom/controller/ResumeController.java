package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public int createResume(@RequestBody Resume resume, Principal principal) {
        resume.setUsername(principal.getName());
        resumeService.createResume(resume);
        return resume.getResumeId();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Resume> getResumeList(Principal principal) {
        List<Resume> resumes = resumeService.getAllResume(principal.getName());
        return resumes;
    }

    @GetMapping("/{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public Resume getResume(@PathVariable int resumeId, Principal principal){
        Resume resume = resumeService.getResume(resumeId, principal.getName());
        return resume;
    }

    @PutMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResume(@PathVariable int resumeId, @RequestBody Resume resume, Principal principal){
        resume.setResumeId(resumeId);
        resumeService.updateResume(resume, principal.getName());
    }

    @DeleteMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(@PathVariable int resumeId, Principal principal){
        resumeService.deleteResume(resumeId, principal.getName());
    }

}
