package com.nexters.rezoom.core.domain.converter;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by momentjin@gmail.com on 2019-12-03
 * Github : http://github.com/momentjin
 */
public class MultipartFileStub implements MultipartFile {


    @Override
    public String getName() {
        return "A사 2019 하반기 신입.txt";
    }

    @Override
    public String getOriginalFilename() {
        return "A사 2019 하반기 신입.txt";
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        String fileContents = "Q1. 지원동기와 포부는?\n" +
                "T1. 지원동기, 포부\n" +
                "A1. [개인의 발전이 곧 회사의 발전이다]\n" +
                "E사의 해커톤에 관한 다큐멘터리를 본 경험이 있습니다. 이를 통해 E사의 개발 문화와 구성원들의 개발 열정을 느꼈고 반드시 입사해야겠다고 결심했습니다.\n" +
                "저는 항상 즐거움이 동기부여가 되어 스스로 성장하는 사람입니다. 일례로, Back-End 개발자를 목표로 인턴과 개발 동아리 등에 도전하며 개발 역량을 쌓았습니다. 또한, 주변의 불편함을 해결하기 위해 스스로 프로젝트를 진행했습니다. 일례로, 학과 게시판 알림 시스템과 자기소개서 관리 시스템을 개발했습니다. 이처럼 5년, 10년 후에도 주도적으로 학습하며 개발자로서 성장하고자 합니다.\n" +
                "이런 측면에서, E사는 개발자들이 성장할 수 있는 문화를 가졌기에 지원을 결심했습니다. 해커톤은 개인의 역량을 적극적으로 회사 성장에 기여하는 훌륭한 문화라고 생각합니다. 직원에게 즐거움과 의미를 줄 수 있고, 직접적으로 기여한다는 점에서 주인의식을 갖게 할 수 있기 때문입니다.\n" +
                "따라서 항상 나의 일이라고 생각하며 근무하겠다는 저의 직업적 가치관이 이베이에서 잘 융화될 수 있다고 생각합니다. 주도적인 성장을 통해 이베이에 기여할 수 있는 S/W 개발자가 되겠습니다.\n" +
                "END\n" +
                "\n" +
                "Q2. 그간 수강한 소프트웨어 관련 과목에는 무엇이 있었고, 그 중 무엇이 흥미로웠는지 기술해주세요.\n" +
                "T2. 학습, 과목, 강의\n" +
                "A2. [Web Programming]\n" +
                "Back-End 개발자가 되겠다는 목표를 갖게 해준 과목입니다. 웹 어플리케이션의 내부에서 데이터베이스와 상호작용하고, 비즈니스 로직을 설계하여 직접적인 서비스를 제공하는 코드를 작성하는 것이 흥미로웠습니다. 독학으로 Servlet/JSP를 공부했고 나아가 Spring Boot를 활용한 API 서버 구축 능력까지 갖추게 되었습니다. 그뿐만 아니라 개발 동아리, 인턴에서 Back-End 개발을 담당하며 Ajax와 JavaScript도 자주 사용해봤습니다. 이것을 기반으로 이베이코리아의 Back-End 개발에 기여하겠습니다.\n" +
                "[Data Structure]\n" +
                "데이터의 구조와 이것을 실제로 구현해보면서 풀어보면서 흥미를 가졌습니다. 나아가 문제 해결 프로그래밍이라는 것을 접하는 계기가 된 과목입니다. 이 과목을 접하면서 완전 탐색과 DP, 정렬 등의 알고리즘을 배웠습니다. 문제의 핵심을 파헤쳐 일반화하는 과정을 통해 저만의 라이브러리를 만들면서 수업에 임했습니다. 또한, 타인의 코드를 보면서 다른 사고방식을 깨우치는 것이 흥미로웠습니다. 입사 후에도 자료구조와 알고리즘 지식이 바탕이 되어 실제 업무에 활용될 수 있도록 알고리즘을 꾸준히 학습할 것입니다.\n" +
                "END\n" +
                "\n" +
                "Q3. 그간 수행했던 프로젝트가 있다면 자세히 설명해주세요.\n" +
                "T3. 프로젝트\n" +
                "A3. [학과 게시판 새 글 알림 시스템]\n" +
                "학과 게시판은 RSS가 지원되지 않는 문제로, 새 글이 게시되었을 때 알림을 받을 수 없었습니다. 이러한 불편함을 해결하고자 자체적으로 개인 프로젝트를 진행했습니다.\n" +
                "Quartz 스케줄링을 기반으로 게시판을 주기적으로 크롤링하고, MAP의 Key를 기반으로 데이터 비교를 수행해 새 글을 탐색합니다. 새 글을 발견하면, 해당 게시판을 알림 받기 원하는 사용자에게 FCM을 보내 새 글을 알리는 프로세스로 구성되어 있습니다.\n" +
                "이 프로젝트는 저와 함께 발전하는 프로젝트입니다. 처음 프로젝트를 시작했을 땐 Socket을 이용한 방식이었습니다. 하지만 개인 학습과 인턴, 개발 동아리 활동을 통해 JSP/Servlet과 Spring Boot, ORM 등을 배우고 이를 프로젝트에 적용하며 리팩토링을 수행했습니다.\n" +
                "입사 후에도, 배우는 지식이나 업무를 단순히 활용하는데 그치지 않고 개인 프로젝트에 적용해보면서 개발 실력을 키울 것입니다. 뿐만 아니라, 업무 방식에서 불편함이 존재한다면 이를 소프트웨어로 해결할 수 있는 개발자가 되겠습니다.\n" +
                "END";

        return fileContents.getBytes();

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {

    }
}
