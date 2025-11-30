package com.jes.waitit.domain.submission.service;

import com.jes.waitit.domain.reservation.entity.Question;
import com.jes.waitit.domain.reservation.repository.QuestionRepository;
import com.jes.waitit.domain.submission.dto.AnswerDetailResponseDTO;
import com.jes.waitit.domain.submission.dto.SubmissionDetailResponseDTO;
import com.jes.waitit.domain.submission.dto.SubmissionUpdateRequestDTO;
import com.jes.waitit.domain.submission.dto.SubmissionUpdateResponseDTO;
import com.jes.waitit.domain.submission.entity.Answer;
import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.repository.AnswerRepository;
import com.jes.waitit.domain.submission.repository.SubmissionRepository;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // Submission 정보 가져오기
    @Transactional
    public SubmissionDetailResponseDTO getSubmissionDetail(String accessCode) {
        Submission submission = submissionRepository.findByAccessCodeAndIsDeletedFalse(accessCode)
                .orElseThrow(() -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND));

        List<Answer> answers = answerRepository.findAllBySubmissionId(submission.getId());
        List<Question> questions = questionRepository.findAllByReservationIdOrderByQuestionOrderAsc(submission.getReservation().getId());
        Map<Long, Answer> answerMap = answers.stream()
                .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));

        List<AnswerDetailResponseDTO> answerDetails = questions.stream().map(q ->
                AnswerDetailResponseDTO.builder()
                        .title(q.getTitle())
                        .questionType(q.getQuestionType())
                        .content(answerMap.get(q.getId()).getContent())
                        .build()
        ).toList();

        return SubmissionDetailResponseDTO.builder()
                .id(submission.getId())
                .waitingNum(submission.getWaitingNum())
                .submissionState(submission.getSubmissionState())
                .comment(submission.getComment())
                .answers(answerDetails)
                .submittedAt(submission.getSubmittedAt())
                .build();
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
