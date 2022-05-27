package com.example.springbootapi.ui;

import com.example.springbootapi.model.Member;
import com.example.springbootapi.model.MemberCreateRequest;
import com.example.springbootapi.model.MemberUpdateRequest;
import com.example.springbootapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    
    // Controller - Service - Repository 각각에 위임해주는 형식으로 사용
    private final MemberService memberService;

    /**
     * 회원 생성 API
     * [POST] /api/members
     * @return
     */
    @PostMapping("/api/members")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreateRequest request){
        Member createMember = memberService.createMember(request);
        return new ResponseEntity<>(createMember, HttpStatus.OK); // 200번대가 성공
    }


    /**
     * 특정 회원 조회 API
     * [GET] /api/members/{memberId}
     * ex) /api/members/1
     * @return
     */
    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<Member> findMember(@PathVariable Long memberId){
        Member findMember = memberService.findMember(memberId);
        return new ResponseEntity<>(findMember, HttpStatus.OK); // 200번대가 성공
    }


    /**
     * 전체 회원 조회 API
     * [GET] /api/members
     * @return
     */
    @GetMapping("/api/members")
    public ResponseEntity<List<Member>> findAllMember(){
        List<Member> allMember = memberService.findAllMember();
        return new ResponseEntity<>(allMember, HttpStatus.OK); // 200번대가 성공
    }

    /**
     * 회원 수정 API - PUT, PATCH
     * [PATCH] /api/members/{memberId}
     * @param memberId
     * @return
     */
    @PatchMapping("/api/members/{memberId}")
    public ResponseEntity<String> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequest request){
        memberService.updateMember(memberId, request);
        return new ResponseEntity<>(memberId + "번 회원의 이름이 정상적으로 수정되었습니다.", HttpStatus.OK);
    }


    /**
     * 특정 회원 삭제 API
     * [DELETE] /api/members/{memberId}
     * @param memberId
     * @return
     */
    @DeleteMapping("/api/members/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(memberId + "번 회원이 정상적으로 삭제되었습니다.", HttpStatus.OK);
    }


    /**
     * 전체 회원 삭제 API
     * [DELETE] /api/members
     * @param memberId
     * @return
     */
    @DeleteMapping("/api/members")
    public ResponseEntity<String> deleteAllMember() {
        memberService.deleteAllMember();
        return new ResponseEntity<>("모든 회원이 삭제되었습니다.", HttpStatus.OK);
    }

}
