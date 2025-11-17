package com.jes.waitit.domain.submission.repository;

import com.jes.waitit.domain.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
