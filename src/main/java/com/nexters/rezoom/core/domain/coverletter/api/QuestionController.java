package com.nexters.rezoom.core.domain.coverletter.api;

import com.nexters.rezoom.core.domain.coverletter.api.dto.PageRequest;
import com.nexters.rezoom.core.domain.coverletter.api.dto.QuestionDto;
import com.nexters.rezoom.core.domain.coverletter.application.QuestionService;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("")
    public ApiResponse<Page<QuestionDto.ViewRes>> getList(@AuthenticationPrincipal Member member, PageRequest pageRequest) {
        pageRequest.setSort(Sort.by("id").descending());
        return ApiResponse.success(service.getList(member, pageRequest.of()));
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionDto.ViewRes> getView(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        return ApiResponse.success(service.getView(id, member));
    }

    @GetMapping("/search")
    public ApiResponse<List<QuestionDto.ViewRes>> getQuestionsByHashtags(
            @AuthenticationPrincipal Member member, @RequestParam List<String> hashtags) {

        return ApiResponse.success(service.getQuestionsByHashtags(member, hashtags));
    }
}
