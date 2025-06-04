package com.college.library.services;

import com.college.library.models.Reservation;
import com.college.library.models.User;
import com.college.library.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation reserveBook(User user, Long bookId) {
        Reservation res = new Reservation(user, null, new Date());
        return reservationRepository.save(res);
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }
}
