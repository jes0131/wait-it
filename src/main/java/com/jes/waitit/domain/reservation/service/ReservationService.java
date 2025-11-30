package com.jes.waitit.domain.reservation.service;

import com.jes.waitit.domain.reservation.dto.*;
import com.jes.waitit.domain.reservation.entity.Question;
import com.jes.waitit.domain.reservation.entity.Reservation;
import com.jes.waitit.domain.reservation.repository.QuestionRepository;
import com.jes.waitit.domain.reservation.repository.ReservationRepository;
import com.jes.waitit.domain.submission.dto.AnswerSummaryResponseDTO;
import com.jes.waitit.domain.submission.dto.SubmissionSummaryResponseDTO;
import com.jes.waitit.domain.submission.entity.Answer;
import com.jes.waitit.domain.submission.entity.Submission;
import com.jes.waitit.domain.submission.enums.SubmissionState;
import com.jes.waitit.domain.submission.repository.AnswerRepository;
import com.jes.waitit.domain.submission.repository.SubmissionRepository;
import com.jes.waitit.domain.user.entity.User;
import com.jes.waitit.domain.user.service.UserService;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import com.jes.waitit.global.security.TmpPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final UserService userService;

    private final ReservationRepository reservationRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;

    private final TmpPasswordGenerator tmpPasswordGenerator;

    @Transactional(readOnly = true)
    public boolean existReservationById(Long reservationId) {
        return reservationRepository.existsById(reservationId);
    }

    // 예약 폼 생성
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
        List<Question> questions = newQuestions.stream().map(q -> Question.builder()
                .questionOrder(q.getOrder())
                .questionType(q.getQuestionType())
                .title(q.getTitle())
                .description(q.getDescription())
                .placeholder(q.getPlaceholder())
                .required(q.isRequired())
                .reservation(reservation)
                .build()
        ).toList();
        questionRepository.saveAll(questions);
    }

    // 예약 폼 세부 정보 조회
    @Transactional(readOnly = true)
    public ReservationDetailResponseDTO getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        List<Question> questions = questionRepository.findAllByReservationIdOrderByQuestionOrderAsc(reservation.getId());
        List<QuestionDetailResponseDTO> questionDetails = questions.stream().map(q -> QuestionDetailResponseDTO.builder()
                    .order(q.getQuestionOrder())
                    .questionType(q.getQuestionType())
                    .title(q.getTitle())
                    .description(q.getDescription())
                    .placeholder(q.getPlaceholder())
                    .required(q.isRequired())
                    .build()
        ).toList();

        return ReservationDetailResponseDTO.builder()
                .title(reservation.getTitle())
                .description(reservation.getDescription())
                .authorName(reservation.getOwner().getUsername())
                .createdAt(reservation.getCreatedAt())
                .questions(questionDetails)
                .build();
    }

    // 예약 폼 삭제
    @Transactional
    public void deletedReservation(Long reservationId, String username) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!username.equals(reservation.getOwner().getUsername())) {
            throw new CustomException(ErrorCode.RESERVATION_DELETE_FORBIDDEN);
        }

        reservation.delete();
    }

    // 예약 폼 제출
    @Transactional
    public ReservationSubmitResponseDTO submitReservation(Long reservationId,ReservationSubmitRequestDTO dto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        Integer waitingNum = submissionRepository
                .findLastWaitingNumByReservationIdAndIsDeletedFalse(reservationId).orElse(0) + 1;
        String accessCode = tmpPasswordGenerator.generatePassword(6);

        Submission tmpSubmission = Submission.builder()
                .reservation(reservation)
                .waitingNum(waitingNum)
                .accessCode(accessCode)
                .submissionState(SubmissionState.PENDING)
                .isDeleted(false)
                .build();
        Submission submission = submissionRepository.save(tmpSubmission);

        List<Answer> answers = dto.getAnswers().stream().map(q -> {
            Question question = questionRepository.findByReservationAndQuestionOrder(reservation, q.getOrder());
            return Answer.builder()
                    .submission(submission)
                    .question(question)
                    .content(q.getContent())
                    .build();
        }).toList();
        answerRepository.saveAll(answers);

        return ReservationSubmitResponseDTO.builder()
                .waitingNum(waitingNum)
                .accessCode(accessCode)
                .build();
    }

    // 제출 조회
    @Transactional
    public Page<SubmissionSummaryResponseDTO> getSubmissions(Long reservationId, String username, Pageable pageable) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!username.equals(reservation.getOwner().getUsername())) {
            throw new CustomException(ErrorCode.RESERVATION_GET_SUBMISSIONS_FORBIDDEN);
        }

        List<Submission> submissions = submissionRepository.findAllByReservationIdAndIsDeletedFalse(reservationId);

        List<SubmissionSummaryResponseDTO> submissionSummaries = submissions.stream().map(s -> {
            List<Answer> answers = answerRepository.findAllBySubmissionIdOrderByQuestionIdAsc(s.getId());
            List<AnswerSummaryResponseDTO> answerSummaries = answers.stream().map(a ->
                    new AnswerSummaryResponseDTO(a.getContent())
            ).toList();

            return SubmissionSummaryResponseDTO.builder()
                    .id(s.getId())
                    .submissionState(s.getSubmissionState())
                    .comment(s.getComment())
                    .waitingNum(s.getWaitingNum())
                    .answers(answerSummaries)
                    .submittedAt(s.getSubmittedAt())
                    .build();
        }).toList();

        return new PageImpl<>(submissionSummaries, pageable, submissions.size());
    }

    // Websocket 최초 데이터 전송
    @Transactional
    public ReservationStatusInitialDataDTO getInitialData(Long reservationId) {
        Integer lastProcessedWaitingNum = submissionRepository.findLastProcessedWaitingNumByReservationId(reservationId).orElse(0);
        Integer waitingCount = submissionRepository.countByReservationIdAndSubmissionState(reservationId, SubmissionState.PENDING);
        return new ReservationStatusInitialDataDTO(waitingCount, lastProcessedWaitingNum);
    }
}
