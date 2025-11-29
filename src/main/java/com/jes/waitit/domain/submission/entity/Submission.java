package com.jes.waitit.domain.submission.entity;

import com.jes.waitit.domain.reservation.entity.Reservation;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "waiting_num")
    private Integer waitingNum;

    @Column
    private String password;

    @Column(name = "submission_state")
    @Enumerated(EnumType.STRING)
    private SubmissionState submissionState;

    @Column(length = 100)
    private String comment;

    @Column(name = "submitted_at")
    @CreatedDate
    private LocalDateTime submittedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
