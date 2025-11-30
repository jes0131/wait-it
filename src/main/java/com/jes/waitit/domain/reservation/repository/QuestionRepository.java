package com.jes.waitit.domain.reservation.repository;

import com.jes.waitit.domain.reservation.entity.Question;
import com.jes.waitit.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByReservationOrderByQuestionOrderAsc(Reservation reservation);
    List<Question> findAllByReservationIdOrderByQuestionOrderAsc(Long reservationId);
    Question findByReservationAndQuestionOrder(Reservation reservation, Integer questionOrder);
}
