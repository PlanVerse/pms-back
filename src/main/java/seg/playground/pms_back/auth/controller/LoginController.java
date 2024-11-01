package seg.playground.pms_back.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.playground.pms_back.auth.domain.AuthLoginDto;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthLoginDto authLoginDto) {

        return ResponseEntity.ok(authLoginDto);
    }
}
