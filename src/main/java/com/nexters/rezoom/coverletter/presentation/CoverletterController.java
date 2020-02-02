package com.nexters.rezoom.coverletter.presentation;

import com.nexters.global.dto.ApiResponse;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.dto.CoverletterDto;
import com.nexters.rezoom.coverletter.dto.PageRequest;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coverletters")
public class CoverletterController {

    private final CoverletterService service;

    public CoverletterController(CoverletterService service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public ApiResponse save(@AuthenticationPrincipal Member member, @Valid @RequestBody CoverletterDto.SaveReq req) {
        service.save(member, req);
        return ApiResponse.success();
    }

    @GetMapping(value = "")
    public ApiResponse<Page<CoverletterDto.ViewRes>> getList(@AuthenticationPrincipal Member member, final PageRequest pageRequest) {
        return ApiResponse.success(service.getList(member, pageRequest.of()));
    }

    @GetMapping(value = "{id}")
    public ApiResponse<CoverletterDto.ViewRes> getView(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        return ApiResponse.success(service.getView(member, id));
    }

    @GetMapping(value = "/search")
    public ApiResponse<CoverletterDto.ListRes> getList(@AuthenticationPrincipal Member member, @RequestParam final String companyName) {
        return ApiResponse.success(service.searchByCompanyName(member, companyName));
    }

    @PutMapping(value = "/{id}")
    public ApiResponse update(@AuthenticationPrincipal Member member, @PathVariable Long id, @Valid @RequestBody CoverletterDto.UpdateReq req) {
        service.update(member, id, req);
        return ApiResponse.builder().build();
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse delete(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        service.delete(member, id);
        return ApiResponse.success();
    }
}
