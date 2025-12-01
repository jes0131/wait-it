package com.jes.waitit.domain.submission.entity;

import com.jes.waitit.domain.reservation.entity.Reservation;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "submission_state")
    @Enumerated(EnumType.STRING)
    private SubmissionState submissionState;

    @Column(length = 100)
    private String comment;

    @Column(name = "submitted_at")
    @CreatedDate
    private LocalDateTime submittedAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public void update(SubmissionState submissionState, String comment) {
        this.submissionState = submissionState;
        this.comment = comment;
    }
}
