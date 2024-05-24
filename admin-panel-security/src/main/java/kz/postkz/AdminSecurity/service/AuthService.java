package kz.postkz.AdminSecurity.service;

import kz.postkz.AdminSecurity.dto.*;
import kz.postkz.AdminSecurity.entity.OurUsers;
import kz.postkz.AdminSecurity.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<SignupResponse> signUp(SignUpRequest registrationRequest){
        SignupResponse resp = new SignupResponse();
        try {
            Optional<OurUsers> check=ourUserRepo.findByEmail(registrationRequest.getEmail());
            if (!check.isEmpty()) {
                resp.setStatusCode(400);
                resp.setError("user already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            ourUsers.setName(registrationRequest.getName());
            ourUsers.setDeviceToken(registrationRequest.getDeviceToken());
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    public SigninResponse signIn(SignInRequest signinRequest){
        SigninResponse response = new SigninResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setAccessToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(404);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenReqiest){
        RefreshTokenResponse response = new RefreshTokenResponse();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getAccessToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getAccessToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setAccessToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getAccessToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
            return response;
        }
        response.setStatusCode(500);
        return response;
    }
}
