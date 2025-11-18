package com.jes.waitit.domain.submission.service;

import com.jes.waitit.domain.submission.entity.Answer;
import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.repository.AnswerRepository;
import com.jes.waitit.domain.submission.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;

    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Integer findMaxWaitingNumByReservationId(Long reservationId) {
        return submissionRepository.findMaxWaitingNumByReservationId(reservationId)
                .orElse(0);
    }

    public List<Answer> saveAllAnswers(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }
}
