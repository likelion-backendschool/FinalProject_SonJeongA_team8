package com.ll.exam.eBook.app.email.repository;

import com.ll.exam.eBook.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
