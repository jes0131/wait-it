package com.jes.waitit.domain.submission.repository;

import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query(value = "SELECT MAX(s.waiting_num) FROM submissions s WHERE s.reservation_id = :reservationId AND s.submission_state IN ('CANCELED', 'RESOLVED')", nativeQuery = true)
    Optional<Integer> findLastProcessedWaitingNumByReservationId(Long reservationId);

    Integer countByReservationIdAndSubmissionState(Long reservationId, SubmissionState submissionState);
}
