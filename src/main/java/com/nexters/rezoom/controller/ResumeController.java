package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.service.ResumeService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="이력서를 생성한다.")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public int createResume(@RequestBody Resume resume, Principal principal) {
        resume.setUsername(principal.getName());
        resumeService.createResume(resume);
        return resume.getResumeId();
    }

    @ApiOperation(value="모든 이력서를 조회한다.")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Resume> getResumeList(Principal principal) {
        List<Resume> resumes = resumeService.getAllResume(principal.getName());
        return resumes;
    }

    @ApiOperation(value="이력서 1개를 조회한다.")
    @GetMapping("/{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public Resume getResume(@PathVariable int resumeId, Principal principal){
        Resume resume = resumeService.getResume(resumeId, principal.getName());
        return resume;
    }

    @ApiOperation(value="이력서 1개를 업데이트한다.")
    @PutMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResume(@PathVariable int resumeId, @RequestBody Resume resume, Principal principal){
        resume.setResumeId(resumeId);
        resumeService.updateResume(resume, principal.getName());
    }

    @ApiOperation(value="이력서 1개를 삭제한다.")
    @DeleteMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(@PathVariable int resumeId, Principal principal){
        resumeService.deleteResume(resumeId, principal.getName());
    }

}
