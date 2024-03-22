package com.jpa.simpleboard.board.service;

import com.jpa.simpleboard.board.db.BoardEntity;
import com.jpa.simpleboard.board.db.BoardRepository;
import com.jpa.simpleboard.board.model.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardEntity create(
        BoardRequest boardRequest
    ){
        var entity =  BoardEntity.builder()
                .boardName(boardRequest.getBoardName())
                .status("REGISTERED")
                .build()
                ;
        return boardRepository.save(entity);
    }

}
