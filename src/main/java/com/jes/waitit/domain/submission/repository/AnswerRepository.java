package com.jes.waitit.domain.submission.repository;

import com.jes.waitit.domain.submission.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllBySubmissionId(Long submissionId);
}
