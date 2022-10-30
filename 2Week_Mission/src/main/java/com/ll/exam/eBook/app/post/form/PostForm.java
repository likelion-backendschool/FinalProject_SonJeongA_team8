package com.ll.exam.eBook.app.post.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostForm {
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
    @NotBlank
    private String contentHtml;
    @NotBlank
    private String postTagContents;
}
