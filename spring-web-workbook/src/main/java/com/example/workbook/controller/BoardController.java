package com.example.workbook.controller;

import com.example.workbook.dto.*;
import com.example.workbook.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-02-27
 * description   :
 **/
@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Board", description = "Board 조회/등록/수정/삭제")
public class BoardController {

    @Value("${com.example.workbook.path}")
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
//        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
//        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    @Operation(summary = "Board 등록", description = "새로운 게시물을 등록합니다.")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("call");
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    /**
     * Modify string.
     * 수정 처리
     * 수정 후에는 다시 조회 화면으로 이동해서 수정된 내용을 확인할 수 있도록 구현
     * 수정 후 조회화면을 이동했을 때 검색했던 조건이 해당하지 않을 수 있다. 이 떄문에 안전하게 구현하기 위해서 단순 조회 화면으로 이동하도록 구현
     * modify에서 문제 발생시 errors를 이용해 다시 수정페이지로 이동한다.
     *
     * @param pageRequestDTO     the page request dto
     * @param boardDTO           the board dto
     * @param bindingResult      the binding result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board modify post.........." + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors...........");

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read";
    }

    //    @PostMapping("/remove")
//    public String remove(Long bno, RedirectAttributes redirectAttributes) {
//        log.info("remove post..." + bno);
//        boardService.remove(bno);
//        redirectAttributes.addFlashAttribute("result", "removed");
//        return "redirect:/board/list";
//    }
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        Long bno = boardDTO.getBno();

        log.info("remove post..." + bno);
        boardService.remove(bno);

        // 게시물이 데이터베이스상에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = boardDTO.getFileNames();
        if (fileNames != null && fileNames.size() > 0) removeFiles(fileNames);

        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }

    private void removeFiles(List<String> files) {
        for (String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                // 썸네일 존재
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
