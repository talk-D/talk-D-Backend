package talkd.talkd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import talkd.talkd.DTO.BoardDTO;
import talkd.talkd.Service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 목록
    @GetMapping("/community")
    public String community(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "community";
    }

    // 게시글 작성
    @GetMapping("/community/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/community/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println("boardDTO = "+boardDTO);
        boardService.save(boardDTO);
        return "redirect:/community";
    }

    // 커뮤니티 게시글 상세보기
    @GetMapping("/community/{boardId}")
    public String findById(@PathVariable Long boardId, Model model){
        //경로 상에 있는 값을 받아올 때는 @PathVariable 이용
        /*
        해당 게시글의 조회수를 하나 올리고
        게시글 데이터를 가져와서 detail.html에 출력
        */
        boardService.updateHits(boardId);
        BoardDTO boardDTO = boardService.findById(boardId);
        model.addAttribute("board",boardDTO);
        return "detail";
    }

    @GetMapping("/community/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model){
        BoardDTO boardDTO = boardService.findById(boardId);
        model.addAttribute("boardUpdate",boardDTO);
        return "update";
    }

    // 게시글 수정
    @PostMapping("/community/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board",board);
        return "detail";
        //return "redirect:/board/" + boardDTO.getId() -> 수정시 글조회수에 영향을 줌
    }

    // 게시글 삭제
    @GetMapping("/community/delete/{boardId}")
    public String delete(@PathVariable Long boardId){
        boardService.delete(boardId);
        return "redirect:/community";
    }

}
