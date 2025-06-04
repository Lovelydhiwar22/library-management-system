package com.college.library.services;

import com.college.library.models.BorrowRecord;
import com.college.library.models.Book;
import com.college.library.models.User;
import com.college.library.repositories.BorrowRecordRepository;
import com.college.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    public BorrowRecord borrowBook(User user, Book book, Date dueDate) {
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book not available.");
        }

        BorrowRecord record = new BorrowRecord(user, book, new Date(), dueDate);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        return borrowRecordRepository.save(record);
    }

    public BorrowRecord returnBook(BorrowRecord record) {
        record.setReturned(true);
        record.setReturnDate(new Date());

        Book book = record.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getUserRecords(User user) {
        return borrowRecordRepository.findByUser(user);
    }
}
