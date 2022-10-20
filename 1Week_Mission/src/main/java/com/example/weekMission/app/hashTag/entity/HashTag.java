package com.example.weekMission.app.hashTag.entity;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.base.entity.BaseEntity;
import com.example.weekMission.app.keyword.entity.Keyword;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HashTag extends BaseEntity {

    @ManyToOne
    @ToString.Exclude
    private Article article;

    @ManyToOne
    @ToString.Exclude
    private Keyword keyword;
}