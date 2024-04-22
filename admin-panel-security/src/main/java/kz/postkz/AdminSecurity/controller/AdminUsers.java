package kz.postkz.AdminSecurity.controller;

import kz.postkz.AdminSecurity.dto.OurUserDto;
import kz.postkz.AdminSecurity.repository.OurUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class AdminUsers {

    @Autowired
    private OurUserRepo ourUserRepo  ;

    @GetMapping()
    public ResponseEntity<OurUserDto> getUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // Здесь предполагаем, что username это email
        }

        log.info("User email: {}", email);

        return ourUserRepo.findByEmail(email)
                .map(user -> ResponseEntity.ok(new OurUserDto(user.getId(), user.getEmail(), user.getName(), user.getRole())))
                .orElse(ResponseEntity.notFound().build());
    }


}
