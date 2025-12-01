package com.jes.waitit.domain.reservation.repository;

import com.jes.waitit.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByIdAndIsDeletedFalse(Long id);
}
