package com.example.weekMission.app.article.entity;

import com.example.weekMission.app.base.entity.BaseEntity;
import com.example.weekMission.app.member.entity.Member;
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
public class Article extends BaseEntity {

    @ManyToOne
    private Member author;

    private String title;

    private String content;
}
