package com.example.weekMission.app.hashTag.repository;

import com.example.weekMission.app.hashTag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    List<HashTag> findAllByArticleId(Long id);

    Optional<HashTag> findByArticleIdAndKeywordId(Long articleId, Long keywordId);

    List<HashTag> findAllByArticleIdIn(long[] ids);
}
