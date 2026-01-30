package com.zeus.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zeus.domain.Member;
import com.zeus.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@MapperScan(basePackages = "com.zeus.mapper")
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/insertForm")
	public String boardInsertForm(Model model) {
		return "member/insertForm";
	}

	@PostMapping("/insert")
	public String memberInsert(Member member, Model model) {
		log.info("insert member=" + member.toString());
		try {
			int count = memberService.register(member);
			if (count > 0) {
				model.addAttribute("message", "%s님의 등록성공".formatted(member.getName()));
				return "member/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s님의 등록실패".formatted(member.getName()));
		return "member/failed";
	}

	@GetMapping("/memberList")
	public String memberList(Model model) {
		log.info("memberList");
		try {
			List<Member> memberList = memberService.list();
			model.addAttribute("memberList", memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "member/memberList";
	}

	
	@GetMapping("/detail")
	public String boardDetail(Member m, Model model) {
		log.info("Detail " + m.toString());
		try {
			Member member = memberService.read(m);
			if (member == null) {
				model.addAttribute("message", "%d 님의 상세정보가 없습니다.".formatted(member.getNo()));
				return "member/failed";
			}
			log.info("detail  member"+ member.toString());
			model.addAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "member/detail";
	}
	
	@GetMapping("/updateForm")
	public String memberUpdateForm(Member m, Model model) {
		log.info("UpdateForm " + m.toString());
		try {
			Member member = memberService.read(m);
			if (member == null) {
				model.addAttribute("message", "%d 님의 정보가 없습니다.".formatted(member.getNo()));
				return "member/failed";
			}
			model.addAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/updateForm";
	}


	
	@PostMapping("/update")
	public String boardUpdate(Member m, Model model) {
		log.info("Update member=" + m.toString());
		try {
			int count = memberService.update(m);
			if (count > 0) {
				model.addAttribute("message", "%s 님의 회원수정성공".formatted(m.getName()));
				return "member/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s 님의 회원수정실패".formatted(m.getName()));
		return "member/failed";
	}
	
	@GetMapping("/delete")
	public String boardDelete(Member member, Model model) {
		log.info("memberDelete member=" + member.toString());
		try {
			int count = memberService.delete(member);
			if (count > 0) {
				model.addAttribute("message", "%d 님의 회원탈퇴".formatted(member.getNo()));
				return "member/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%d 님의 회원실패".formatted(member.getNo()));
		return "member/failed";

	}
	
	@GetMapping("/search")
	public String boardSearch(Member member, Model model) {
		log.info("memberSearch member" + member.toString());
		try {
			List<Member> memberList = memberService.search(member);
			model.addAttribute("memberList", memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/memberList";
	}

}
