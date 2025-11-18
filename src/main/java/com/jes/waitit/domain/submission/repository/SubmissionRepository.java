package com.jes.waitit.domain.submission.repository;

import com.jes.waitit.domain.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query(value = "SELECT MAX(s.waiting_num) FROM submissions s WHERE s.reservation_id = :reservationId", nativeQuery = true)
    Optional<Integer> findMaxWaitingNumByReservationId(Long reservationId);
}
