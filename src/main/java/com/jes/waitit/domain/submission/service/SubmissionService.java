package com.jes.waitit.domain.submission.service;

import com.jes.waitit.domain.submission.dto.SubmissionUpdateRequestDTO;
import com.jes.waitit.domain.submission.dto.SubmissionUpdateResponseDTO;
import com.jes.waitit.domain.submission.entity.Answer;
import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import com.jes.waitit.domain.submission.repository.AnswerRepository;
import com.jes.waitit.domain.submission.repository.SubmissionRepository;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;

    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Integer findLastProcessedWaitingNumByReservationId(Long reservationId) {
        return submissionRepository.findLastProcessedWaitingNumByReservationId(reservationId)
                .orElse(0);
    }

    public Integer findLastWaitingNumByReservationIdAndIsDeletedFalse(Long reservationId) {
        return submissionRepository.findLastWaitingNumByReservationIdAndIsDeletedFalse(reservationId)
                .orElse(0);
    }

    public Integer countPendingByReservationId(Long reservationId) {
        return submissionRepository.countByReservationIdAndSubmissionState(reservationId, SubmissionState.PENDING);
    }

    public List<Answer> saveAllAnswers(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }

    // Submission 상태 업데이트
    @Transactional
    public SubmissionUpdateResponseDTO updateSubmission(Long submissionId, String username, SubmissionUpdateRequestDTO dto) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND));

        if (!submission.getReservation().getOwner().getUsername().equals(username)) {
            throw new CustomException(ErrorCode.SUBMISSION_UPDATE_FORBIDDEN);
        }

        submission.update(dto.getSubmissionState(), dto.getComment());
        return SubmissionUpdateResponseDTO.builder()
                .id(submission.getId())
                .submissionState(submission.getSubmissionState())
                .comment(submission.getComment())
                .build();
    }
}
