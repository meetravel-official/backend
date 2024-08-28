package com.meetravel.domain.user.controller;


import com.meetravel.domain.user.dto.response.GetMyPageResponse;
import com.meetravel.domain.user.service.UserService;
import com.meetravel.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @GetMapping("/{userId}/my-page")
    public ResponseEntity<GetMyPageResponse> getMyPage(String userId) {
        return ResponseEntity.ok(userService.getMyPage(userId));
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


}
