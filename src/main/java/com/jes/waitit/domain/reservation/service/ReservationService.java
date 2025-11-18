package com.jes.waitit.domain.reservation.service;

import com.jes.waitit.domain.reservation.dto.*;
import com.jes.waitit.domain.reservation.entity.Question;
import com.jes.waitit.domain.reservation.entity.Reservation;
import com.jes.waitit.domain.reservation.repository.QuestionRepository;
import com.jes.waitit.domain.reservation.repository.ReservationRepository;
import com.jes.waitit.domain.submission.entity.Answer;
import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import com.jes.waitit.domain.submission.service.SubmissionService;
import com.jes.waitit.domain.user.entity.User;
import com.jes.waitit.domain.user.service.UserService;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import com.jes.waitit.global.security.TmpPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final UserService userService;
    private final SubmissionService submissionService;

    private final ReservationRepository reservationRepository;
    private final QuestionRepository questionRepository;

    private final TmpPasswordGenerator tmpPasswordGenerator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createReservation(String username, ReservationCreateRequestDTO dto) {
        User user  = userService.findByUsername(username);

        Reservation newReservation = Reservation.builder()
                .owner(user)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        Reservation reservation = reservationRepository.save(newReservation);

        List<QuestionCreateRequestDTO> newQuestions = dto.getQuestions();
        List<Question> questions = new ArrayList<>();
        for (QuestionCreateRequestDTO q : newQuestions) {
            Question question = Question.builder()
                    .questionOrder(q.getOrder())
                    .questionType(q.getQuestionType())
                    .title(q.getTitle())
                    .description(q.getDescription())
                    .placeholder(q.getPlaceholder())
                    .required(q.isRequired())
                    .reservation(reservation)
                    .build();
            questions.add(question);
        }
        questionRepository.saveAll(questions);
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponseDTO getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        List<Question> questions = questionRepository.findAllByReservationOrderByQuestionOrderAsc(reservation);
        List<QuestionDetailResponseDTO> questionDetails = new ArrayList<>();
        for (Question q : questions) {
            QuestionDetailResponseDTO questionDetail = QuestionDetailResponseDTO.builder()
                    .order(q.getQuestionOrder())
                    .questionType(q.getQuestionType())
                    .title(q.getTitle())
                    .description(q.getDescription())
                    .placeholder(q.getPlaceholder())
                    .required(q.isRequired())
                    .build();
            questionDetails.add(questionDetail);
        }

        return ReservationDetailResponseDTO.builder()
                .title(reservation.getTitle())
                .description(reservation.getDescription())
                .authorName(reservation.getOwner().getUsername())
                .createdAt(reservation.getCreatedAt())
                .questions(questionDetails)
                .build();
    }

    @Transactional
    public ReservationSubmitResponseDTO submitReservation(Long reservationId,ReservationSubmitRequestDTO dto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        Integer waitingNum = submissionService.findMaxWaitingNumByReservationId(reservationId) + 1;
        String password = tmpPasswordGenerator.generatePassword(6);

        Submission tmpSubmission = Submission.builder()
                .reservation(reservation)
                .waitingNum(waitingNum)
                .password(passwordEncoder.encode(password))
                .submissionState(SubmissionState.PENDING)
                .build();
        Submission submission = submissionService.saveSubmission(tmpSubmission);

        List<Answer> answers = new ArrayList<>();
        for (QuestionSubmitRequestDTO q : dto.getAnswers()) {
            Question question = questionRepository.findByReservationAndQuestionOrder(reservation, q.getOrder());

            Answer answer = Answer.builder()
                    .submission(submission)
                    .question(question)
                    .content(q.getContent())
                    .build();
            answers.add(answer);
        }
        submissionService.saveAllAnswers(answers);

        return ReservationSubmitResponseDTO.builder()
                .waitingNum(waitingNum)
                .password(password)
                .build();
    }
}
