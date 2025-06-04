package com.college.library.repositories;

import com.college.library.models.BorrowRecord;
import com.college.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUser(User user);
}
