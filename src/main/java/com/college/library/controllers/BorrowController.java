package com.college.library.controllers;

import com.college.library.models.Book;
import com.college.library.models.BorrowRecord;
import com.college.library.models.User;
import com.college.library.services.BorrowService;
import com.college.library.services.BookService;
import com.college.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping
    public BorrowRecord borrowBook(@RequestParam Long userId,
                                   @RequestParam Long bookId,
                                   @RequestParam long dueTimestamp) {
        Optional<User> user = userService.getUserById(userId);
        Optional<Book> book = bookService.getBookById(bookId);

        if (user.isPresent() && book.isPresent()) {
            return borrowService.borrowBook(user.get(), book.get(), new Date(dueTimestamp));
        } else {
            throw new RuntimeException("User or Book not found.");
        }
    }

    @PostMapping("/return/{recordId}")
    public BorrowRecord returnBook(@PathVariable Long recordId) {
        return borrowService.returnBook(
            borrowService.getUserRecords(null) // placeholder - you should get record by id
                .stream()
                .filter(r -> r.getId().equals(recordId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Borrow record not found"))
        );
    }

    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getUserRecords(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(borrowService::getUserRecords)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}
