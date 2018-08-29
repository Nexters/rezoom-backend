package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.dto.RecentClickResumeDTO;
import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.dto.QuestionListRequestDTO;
import com.nexters.rezoom.repository.DashboardRepository;
import com.nexters.rezoom.repository.HashTagRepository;
import com.nexters.rezoom.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HashTagRepository hashTagRepository;

    @Autowired
    DashboardRepository clickRepository;

    /**
     * TODO : 트랜잭션 필요
     * TODO : 노답 코드 개선..
     * 여러 개의 문항을 삽입한다.<br>
     * 1. 문항 데이터를 삽입한다.<br>
     * 2. 문항에 속한 해쉬태그를 삽입한다.<br>
     * 3. 문항-해쉬태그를 삽입한다.<br>
     */
    public void createQuestions(QuestionListRequestDTO requestDTO, String username) {
        List<Question> questions = requestDTO.getQuestions();

        // 1 문항 저장 -> key 할당
        questionRepository.insertQuestions(requestDTO.getResumeId(), questions, username);

        // 2 -1 전달받은 모든 해쉬태그를 중복없이 저장한다.
        Set<HashTag> hashtags = new HashSet<>();
        for (Question question : questions) {
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

            for (Question question : questions) {
                List<HashTag> hashTagList = question.getHashTags();
                if (hashTagList != null) {
                    for (HashTag hashTag : hashTagList) {
                        hashTag.setHashtagId(hashtagMap.get(hashTag.getHashtagKeyword()));
                    }
                }
            }
        }

        // 여기까지 왔으면 각 객체별 id가 할당되어 있다.
        // 3. question-hashtag mapping 저장
        hashTagRepository.insertQuestionHashtagMapping(questions);
    }

    // 이력서 내 모든 문항 조회
    // TODO : 최근 조회한 데이터는 이력서가 될 수 있고, 증빙자료가 될 수도 있어서,, 공통적으로 처리할 수 있는 AOP 등 사용해서 해결하기
    public List<Question> getAllQuestion(int resumeId, String username) {
        RecentClickResumeDTO recentClickResume = new RecentClickResumeDTO();
        recentClickResume.setResumeId(resumeId);
        recentClickResume.setUsername(username);

        clickRepository.insertResumeClick(recentClickResume);

        return questionRepository.selectAllQuestionByResumeId(resumeId, username);
    }

    // 이력서 내 단일 문항 상세 조회
    public Question getQuestion(String username, int resumeId, int questionId) {
        return questionRepository.getQuestion(username, resumeId, questionId);
    }

    // TODO : 아.. 망할 똥 코딩.. 로직 다시 정리해서 최적화 어떻게 시킬지 고민좀 해보자.
    // TODO : 트랜잭션 필요
    // 이력서 내 모든 문항 수정
    public void updateAllQuestion(@RequestParam QuestionListRequestDTO requestDTO, String username) {
        List<Question> questions = requestDTO.getQuestions();
        List<Question> newQuestions = new ArrayList<>();

        // 1 문항 수정
        for (Question question : questions) {
            int resumeId = requestDTO.getResumeId();

            // 새롭게 추가된 문항은 아이디를 할당받아야 하기 떄문에 따로 모아 insert 수행
            if (question.getQuestionId() != 0 )  questionRepository.updateQuestion(resumeId, question, username);
            else newQuestions.add(question);
        }

        // 1-2 새롭게 추가된 문항 삽입
        if (!newQuestions.isEmpty())
        questionRepository.insertQuestions(requestDTO.getResumeId(), newQuestions, username);

        // 1-3 여기까지 왔으면 모든 question에 id가 할당되어 있다.
        // 이 question id를 제외한 나머지 question를 삭제해줘야 한다. (삭제된게 있을 수 있으므로)
        List<Integer> questionIds = new ArrayList<>();
        for (Question question : questions) {
            questionIds.add(question.getQuestionId());
        }
        questionRepository.deleteQuestion(requestDTO.getResumeId(), questionIds, username);

        // 2 -1 전달받은 모든 해쉬태그를 중복없이 저장한다.
        Set<HashTag> hashtags = new HashSet<>();
        for (Question question : questions) {
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

            for (Question question : questions) {
                List<HashTag> hashTagList = question.getHashTags();
                if (hashTagList != null) {
                    for (HashTag hashTag : hashTagList) {
                        hashTag.setHashtagId(hashtagMap.get(hashTag.getHashtagKeyword()));
                    }
                }
            }
        }

        // 3. 해쉬태그-맵핑에서 모두 삭제
        for (Question question : questions) {
            hashTagRepository.deleteQuestionHashtagMapping(question);
        }

        // 4. question-hashtag mapping 저장
        if (isUsable(hashtags))
            hashTagRepository.insertQuestionHashtagMapping(questions);
    }

    private boolean isUsable(Set set) {
        return !set.isEmpty() && set != null;
    }
}

