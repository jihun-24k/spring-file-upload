package com.ll.exam.app10.app.member.controller;

import com.ll.exam.app10.app.member.entity.Member;
import com.ll.exam.app10.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String showJoin(){
        return "member/join";
    }

    @PostMapping("/join")
    public String postJoin(String username, String password, String email, MultipartFile profileImg, HttpSession session){
        Member oldMember = memberService.getMemberByUsername(username);

        if (oldMember != null){
            return "redirect:/?errorMsg=Already done.";
        }

        Member member = memberService.join(username, "{noop}"+password,email,profileImg);

        session.setAttribute("loginMemberId",member.getId());

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session){
        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        boolean isLogined = loginMemberId != null;

        if (isLogined == false){
            return "redirect:/?errorMsg=Need to login!";
        }

        Member loginedMember = memberService.getMemberById(loginMemberId);

        model.addAttribute("loginedMember", loginedMember);
        return "member/profile";
    }
}
