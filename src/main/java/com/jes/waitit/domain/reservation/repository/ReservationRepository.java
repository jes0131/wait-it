package com.jes.waitit.domain.reservation.repository;

import com.jes.waitit.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByIdAndIsDeletedFalse(Long id);
    Optional<Reservation> findByIdAndIsDeletedFalse(Long id);
}
