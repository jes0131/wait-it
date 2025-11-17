package com.jes.waitit.domain.submission.repository;

import com.jes.waitit.domain.submission.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
