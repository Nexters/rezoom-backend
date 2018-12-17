package com.nexters.rezoom.coverletter.presentation;

import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coverletters")
public class CoverletterController {

    @Autowired
    private CoverletterService service;

    private Member member = new Member("admin@admin.admin", "", "");

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CoverletterDto.SaveReq req) {
        service.save(member, req);
    }

    @PutMapping(value = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CoverletterDto.UpdateReq req) {
        service.update(member, req);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CoverletterDto.ViewRes getView(@PathVariable long id) {
        return service.getView(member, id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public CoverletterDto.ListRes getList(@RequestParam(required = false, defaultValue = "1") int pageNo) {
        // TODO : 페이지 수 계산 클래스화 (default, max 설정)
        int numberPerPage = 10;
        int beginRow = numberPerPage * (pageNo - 1);

        return service.getList(member, beginRow, numberPerPage);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(member, id);
    }

}
