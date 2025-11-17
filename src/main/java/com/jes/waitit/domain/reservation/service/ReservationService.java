package com.jes.waitit.domain.reservation.service;

import com.jes.waitit.domain.reservation.dto.QuestionCreateRequestDTO;
import com.jes.waitit.domain.reservation.dto.QuestionDetailResponseDTO;
import com.jes.waitit.domain.reservation.dto.ReservationCreateRequestDTO;
import com.jes.waitit.domain.reservation.dto.ReservationDetailResponseDTO;
import com.jes.waitit.domain.reservation.entity.Question;
import com.jes.waitit.domain.reservation.entity.Reservation;
import com.jes.waitit.domain.reservation.repository.QuestionRepository;
import com.jes.waitit.domain.reservation.repository.ReservationRepository;
import com.jes.waitit.domain.user.entity.User;
import com.jes.waitit.domain.user.service.UserService;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final UserService userService;

    private final ReservationRepository reservationRepository;
    private final QuestionRepository questionRepository;

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
}
