package com.ll.exam.eBook.app.myBook.repository;

import com.ll.exam.eBook.app.myBook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);

    List<MyBook> findAllByOwnerId(long ownerId);

    Optional<MyBook> findByIdAndOwnerId(long myBookId, long ownerId);
}
