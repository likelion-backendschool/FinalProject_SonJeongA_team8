package com.ll.exam.eBook.app.myBook.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyBookResponse {
    private MyBookDetailDto myBook;
}