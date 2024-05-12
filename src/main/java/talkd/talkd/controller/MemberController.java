package talkd.talkd.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import talkd.talkd.DTO.MemberDTO;
import talkd.talkd.DTO.PasswdDTO;
import talkd.talkd.KakaoApi;
import talkd.talkd.Service.MemberService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final KakaoApi kakaoApi;

    //로그인 페이지
    @GetMapping("/login")
    public String loin(Model model){
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공(loginEmail은 세션 정보 이름이다.)
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }
    @GetMapping("/login/join")
    public String join(){
        return "join_general";
    }

    //회원가입 페이지
    @RequestMapping("/login/join")
    public String getKakaoToken(@RequestParam String code, Model model){
        String accessToken = kakaoApi.getAccessToken(code);

        // 3. 사용자 정보 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String)userInfo.get("email");
        String nickname = (String)userInfo.get("nickname");

        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);

        model.addAttribute("Email",email);

        return "join";
    }

    //회원가입 정보 가져와서 저장
    @PostMapping ("/login/join")
    public String kakaoEmailJoin(@ModelAttribute MemberDTO memberDTO){
        System.out.println(memberDTO);
        memberService.save(memberDTO);
        return "index";
    }

    //마이페이지 - 회원 정보 수정
    @GetMapping("/mypage/edit")
    public String updateForm(HttpSession session, Model model) {
        //getAttribute는 기본적으로 오브젝트 타입이다 오브젝트>스트링 이므로 강제 형변환을 한 것이다.
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "mypage";
    }
    @PostMapping("/mypage/edit")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/mypage/edit";
        //return "redirect:/member/" + memberDTO.getMemberId();
    }
    //마이페이지 - 비밀번호 변경
    @GetMapping("/mypage/changePassword")
    public String passwdModifiedForm(HttpSession session, Model model) {

        String passwd = (String) session.getAttribute("loginEmail");
        PasswdDTO passwdDTO = memberService.passwordChange(passwd);
        model.addAttribute("updateMember", passwdDTO);
        return "passwordChangeForm";
    }
    @PostMapping("/mypage/changePassword")
    public String passwdModified(@ModelAttribute PasswdDTO passwdDTO){
        MemberDTO memberDTO = memberService.passwordCheck(passwdDTO);
        if(memberDTO != null){

            memberService.change(memberDTO, passwdDTO);
            return "passwordChangeForm";
        }else{
            return null;
        }
    }
}
