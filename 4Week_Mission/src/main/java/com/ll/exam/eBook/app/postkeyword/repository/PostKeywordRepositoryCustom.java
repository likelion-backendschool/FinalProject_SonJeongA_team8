package com.ll.exam.eBook.app.postkeyword.repository;

import com.ll.exam.eBook.app.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}