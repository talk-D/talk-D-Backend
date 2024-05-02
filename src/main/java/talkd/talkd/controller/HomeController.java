package talkd.talkd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import talkd.talkd.DTO.MemberDTO;
import talkd.talkd.Service.MemberService;

@Controller
public class HomeController {
    private MemberService memberService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    //로그인

    // 테마 둘러보기

    // 테마 제작하기

    // 내 보관함



        // 로그아웃



    }


    // 웰컴페이지

