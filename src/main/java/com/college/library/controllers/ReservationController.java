package com.college.library.controllers;

import com.college.library.models.Reservation;
import com.college.library.models.User;
import com.college.library.services.ReservationService;
import com.college.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Reservation reserveBook(@RequestParam Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationService.reserveBook(user, null); // Extend this with bookId if needed
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationService.getUserReservations(user);
    }
}
