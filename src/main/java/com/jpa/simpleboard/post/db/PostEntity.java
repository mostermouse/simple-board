package com.jpa.simpleboard.post.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpa.simpleboard.board.db.BoardEntity;
import com.jpa.simpleboard.reply.db.ReplyEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boardId;
    private String userName;
    private String password;
    private String email;
    private String status;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime postedAt;
    @Transient
    private List<ReplyEntity> replyList = List.of();
}
