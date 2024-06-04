package com.websocket.app.service.Impl;

import com.websocket.app.Feign.FirebaseUserRepository;
import com.websocket.app.entity.FirebaseUser;
import com.websocket.app.service.FirebaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirebaseUserServiceImpl implements FirebaseUserService {
    @Autowired
    private FirebaseUserRepository feignUserRepository;

    @Override
    public FirebaseUser saveFirebaseUser(FirebaseUser feignUser) {
        return feignUserRepository.save(feignUser);
    }

    @Override
    public List<FirebaseUser> getAllFirebaseUsers() {
        return feignUserRepository.findAll();
    }

    @Override
    public Optional<FirebaseUser> getFirebaseUserById(Long userid) {
        return feignUserRepository.findById(userid);
    }

    @Override
    public Optional<FirebaseUser> getFirebaseUserByEmail(String email) {
        return feignUserRepository.findByEmail(email);
    }

    @Override
    public Optional<FirebaseUser> getFirebaseUserByExpoToken(String expoToken) {
        return feignUserRepository.findByExpoToken(expoToken);
    }

    @Override
    public FirebaseUser updateFirebaseUser(Long userid, FirebaseUser feignUserDetails) {
        Optional<FirebaseUser> optionalFirebaseUser = feignUserRepository.findById(userid);
        if (optionalFirebaseUser.isPresent()) {
            FirebaseUser existingFirebaseUser = optionalFirebaseUser.get();
            existingFirebaseUser.setEmail(feignUserDetails.getEmail());
            existingFirebaseUser.setName(feignUserDetails.getName());
            existingFirebaseUser.setExpoToken(feignUserDetails.getExpoToken());
            return feignUserRepository.save(existingFirebaseUser);
        } else {
            throw new RuntimeException("FirebaseUser not found with id " + userid);
        }
    }

    @Override
    public void deleteFirebaseUser(Long userid) {
        feignUserRepository.deleteById(userid);
    }
}
