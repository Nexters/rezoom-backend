package com.nexters.rezoom.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.domain.HashTagList;
import com.nexters.rezoom.dto.QuestionDTO;
import com.nexters.rezoom.dto.QuestionListRequestDTO;
import com.nexters.rezoom.dto.QuestionListResponseDTO;
import com.nexters.rezoom.repository.HashTagRepository;
import com.nexters.rezoom.repository.QuestionRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HashTagRepository hashTagRepository;

    /**
     * TODO : 트랜잭션 필요
     * 여러 개의 문항을 삽입한다.<br>
     * 1. 문항 데이터를 삽입한다.<br>
     * 2. 문항에 속한 해쉬태그를 삽입한다.<br>
     * 3. 문항-해쉬태그를 삽입한다.<br>
     */
    public void createQuestions(QuestionListRequestDTO requestDTO, String username) {
        List<QuestionDTO> questions = requestDTO.getQuestions();

        // 1 문항 저장 -> key 할당
        questionRepository.insertQuestions(requestDTO.getResumeId(), questions, username);


        // 2 -1 전달받은 모든 해쉬태그를 중복없이 저장한다.
        Set<HashTag> hashtags = new HashSet<>();
        for (QuestionDTO question : questions) {
            hashtags.addAll(question.getHashTags());
        }

        // 2 -2 해쉬태그 저장
        if (isUsable(hashtags)) {
            hashTagRepository.insertHashtags(new ArrayList(hashtags), username);

            // 2 -3 기존 + 추가된 모든 해쉬태그의 ID값을 가져온다.
            List<HashTag> hashTags = hashTagRepository.selectHashTagByKeyword(new ArrayList(hashtags), username);
            Map<String, Integer> hashtagMap = new HashMap<>();
            for (HashTag tag : hashTags) {
                hashtagMap.put(tag.getHashtagKeyword(), tag.getHashtagId());
            }

            for (QuestionDTO question : questions) {
                List<HashTag> hashTagList = question.getHashTags();
                if (hashTagList != null) {
                    for (HashTag hashTag : hashTagList) {
                        hashTag.setHashtagId(hashtagMap.get(hashTag.getHashtagKeyword()));
                    }
                }
            }
        }

        // 2 -2 해쉬태그 저장 -> key 할당
        // hashTagRepository.insertHashtags(new ArrayList(hashtags), username);

        // 여기까지 왔으면 각 객체별 id가 할당되어 있다.
        // 3. question-hashtag mapping 저장
        hashTagRepository.insertQuestionHashtagMapping(questions);
    }

    // 이력서 내 모든 문항 조회
    public List<QuestionListResponseDTO> getAllQuestion(int resumeId, String username) {
        return questionRepository.selectAllQuestionByResumeId(resumeId, username);
    }

    // 이력서 내 단일 문항 상세 조회
    public QuestionDTO getQuestion(String username, int resumeId, int questionId) {
        return questionRepository.getQuestion(username, resumeId, questionId);
    }

    // TODO : 아.. 망할 똥 코딩.. 로직 다시 정리해서 최적화 어떻게 시킬지 고민좀 해보자.
    // TODO : 트랜잭션 필요
    // 이력서 내 모든 문항 수정
    public void updateAllQuestion(@RequestParam QuestionListRequestDTO requestDTO, String username) {
        List<QuestionDTO> questions = requestDTO.getQuestions();

        // 1 문항 수정
        for (QuestionDTO questionDTO : questions) {
            int resumeId = requestDTO.getResumeId();
            questionRepository.updateQuestion(resumeId, questionDTO, username);
        }

        // 2 -1 전달받은 모든 해쉬태그를 중복없이 저장한다.
        Set<HashTag> hashtags = new HashSet<>();
        for (QuestionDTO question : questions) {
            List<HashTag> hashTags = question.getHashTags();
            if (hashTags != null) {
                hashtags.addAll(question.getHashTags());
            }
        }

        // 2 -2 해쉬태그 저장
        if (isUsable(hashtags)) {
            hashTagRepository.insertHashtags(new ArrayList(hashtags), username);

            // 2 -3 기존 + 추가된 모든 해쉬태그의 ID값을 가져온다.
            List<HashTag> hashTags = hashTagRepository.selectHashTagByKeyword(new ArrayList(hashtags), username);
            Map<String, Integer> hashtagMap = new HashMap<>();
            for (HashTag tag : hashTags) {
                hashtagMap.put(tag.getHashtagKeyword(), tag.getHashtagId());
            }

            for (QuestionDTO question : questions) {
                List<HashTag> hashTagList = question.getHashTags();
                if (hashTagList != null) {
                    for (HashTag hashTag : hashTagList) {
                        hashTag.setHashtagId(hashtagMap.get(hashTag.getHashtagKeyword()));
                    }
                }
            }
        }

        // 3. 해쉬태그-맵핑에서 모두 삭제
        for (QuestionDTO questionDTO : questions) {
            hashTagRepository.deleteQuestionHashtagMapping(questionDTO);
        }

        // 4. question-hashtag mapping 저장
        if (isUsable(hashtags))
            hashTagRepository.insertQuestionHashtagMapping(questions);
    }

    private boolean isUsable(Set set) {
        return !set.isEmpty() && set != null;
    }
}

