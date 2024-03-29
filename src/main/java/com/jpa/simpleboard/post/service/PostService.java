package com.jpa.simpleboard.post.service;

import com.jpa.simpleboard.board.db.BoardEntity;
import com.jpa.simpleboard.board.db.BoardRepository;
import com.jpa.simpleboard.post.db.PostEntity;
import com.jpa.simpleboard.post.db.PostRepository;
import com.jpa.simpleboard.post.model.PostRequest;
import com.jpa.simpleboard.post.model.PostViewRequest;
import com.jpa.simpleboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ReplyService replyService;
    private final BoardRepository boardRepository;

    public PostEntity create(
            PostRequest postRequest

    ) {
        var boardEntity = boardRepository.findById(postRequest.getBoardId()).get();
        var entity = PostEntity.builder()
                .boardId(1L) // <<임시 고정
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();
        return postRepository.save(entity);
    }

    /*게시글이 있는가
     * 비밀번호가 맞는가
     * */
    public PostEntity view(PostViewRequest postViewRequest) {
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(), "REGISTERED")
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    //답변 글도 같이 적용
                    var replyList = replyService.findAllByPostId(it.getId());
                    it.setReplyList(replyList);

                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다. :" + postViewRequest.getPostId());
                        }
                );

    }

    public List<PostEntity> all() {
        return postRepository.findAll();
    }

    public void delete(PostViewRequest postViewRequest) {
        postRepository.findById(postViewRequest.getPostId())
                .map(it -> {
                    if (it.getPassword().equals(postViewRequest.getPassword())) {
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다. :" + postViewRequest.getPostId());
                        }
                );

    }
}
