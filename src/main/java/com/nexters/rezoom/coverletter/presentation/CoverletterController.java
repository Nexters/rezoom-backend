package com.nexters.rezoom.coverletter.presentation;

import com.nexters.rezoom.config.dto.Paging;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coverletters")
public class CoverletterController {

    @Autowired
    private CoverletterService service;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@AuthenticationPrincipal Member member, @Valid @RequestBody CoverletterDto.SaveReq req) {
        service.save(member, req);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal Member member, @PathVariable long id, @Valid @RequestBody CoverletterDto.UpdateReq req) {
        service.update(member, id, req);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public CoverletterDto.ListRes getList(@AuthenticationPrincipal Member member,
                                          @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return service.getList(member, new Paging(pageNo));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Member member, @PathVariable long id) {
        service.delete(member, id);
    }
}
