INSERT INTO member
VALUES ('test', 'tester', 'test');

INSERT INTO coverletter
(company_name, application_year, application_half, application_type,
 is_application, is_pass, deadline, job_type, member_id)
VALUES ('testCompany1', '2019', 0, 0,
        false, false, '2019-06-01 18:00', 'Backend Engineer', 'test');

SET @coverletter_id = LAST_INSERT_ID();

INSERT INTO question
    (coverletter_id, title, contents)
VALUES (@coverletter_id, 'title1', 'contents1');

SET @question_id1 = LAST_INSERT_ID();

INSERT INTO question
    (coverletter_id, title, contents)
VALUES (@coverletter_id, 'title2', 'contents2');

SET @question_id2 = LAST_INSERT_ID();

INSERT INTO hashtag
    (member_id, `value`)
VALUES ('test', 'testTag1');

SET @hashtag1 = LAST_INSERT_ID();

INSERT INTO hashtag
    (member_id, `value`)
VALUES ('test', 'testTag2');

SET @hashtag2 = LAST_INSERT_ID();

INSERT INTO hashtag
    (member_id, `value`)
VALUES ('test', 'testTag3');

SET @hashtag3 = LAST_INSERT_ID();

INSERT INTO question_hashtag
VALUES (@question_id1, @hashtag1),
       (@question_id1, @hashtag2),
       (@question_id2, @hashtag2),
       (@question_id2, @hashtag3);


INSERT INTO coverletter
(company_name, application_year, application_half, application_type,
 is_application, is_pass, deadline, job_type, member_id)
VALUES ('testCompany2', '2019', 0, 0,
        false, false, '2019-06-01 18:00', 'Backend Engineer', 'test');

SET @coverletter_id = LAST_INSERT_ID();

INSERT INTO question
    (coverletter_id, title, contents)
VALUES (@coverletter_id, 'title1', 'contents1');

SET @question_id3 = LAST_INSERT_ID();

INSERT INTO question
    (coverletter_id, title, contents)
VALUES (@coverletter_id, 'title2', 'contents2');

SET @question_id4 = LAST_INSERT_ID();

INSERT INTO question_hashtag
VALUES (@question_id3, @hashtag1),
       (@question_id3, @hashtag2),
       (@question_id4, @hashtag2),
       (@question_id4, @hashtag3);



