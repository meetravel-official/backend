package com.meetravel.domain.user.controller;


import com.meetravel.domain.user.dto.request.UpdateMyPageInfoRequest;
import com.meetravel.domain.user.dto.request.UpdateNicknameRequest;
import com.meetravel.domain.user.dto.response.GetMyPageResponse;
import com.meetravel.domain.user.service.UserService;
import com.meetravel.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @GetMapping("/{userId}/my-page")
    public ResponseEntity<GetMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Get MyPage");
        return ResponseEntity.ok(userService.getMyPage(userDetails.getUsername()));
    }


    @Override
    @PutMapping("/{userId}/nickname")
    public void updateNickname(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateNicknameRequest request) {
        log.info("Update Nickname");
        userService.updateNickname(userDetails.getUsername(), request.getNickname());
    }
    @Override
    @PutMapping("/{userId}/info")
    public void updateMyPageInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateMyPageInfoRequest request){
        log.info("Update MyPageInfo");
        userService.updateMyPageInfo(userDetails.getUsername(), request);
    }

    /**
     * 회원 로그아웃
     *
     * @param request
     */
    @Override
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        log.info("logout");
        String token = jwtService.getToken(request);
        userService.logout(token);
    }

    @Override
    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String userId) {
        log.info("Delete User");
        userService.deleteUser(userId);
    }


}
