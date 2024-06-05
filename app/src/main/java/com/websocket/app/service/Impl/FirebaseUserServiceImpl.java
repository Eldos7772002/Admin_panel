package com.websocket.app.service.Impl;

import com.websocket.app.Feign.FirebaseUserRepository;
import com.websocket.app.entity.Balance;
import com.websocket.app.entity.FirebaseUser;
import com.websocket.app.repository.BalanceRepository;
import com.websocket.app.service.FirebaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirebaseUserServiceImpl implements FirebaseUserService {
    @Autowired
    private BalanceRepository balanceRepository;

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
            existingFirebaseUser.setBalance(feignUserDetails.getBalance());
            return feignUserRepository.save(existingFirebaseUser);
        } else {
            throw new RuntimeException("FirebaseUser not found with id " + userid);
        }
    }

    @Override
    public void deleteFirebaseUser(Long userid) {
        feignUserRepository.deleteById(userid);
    }
    @Override
    public List<Balance> findAllBalances() {
        return balanceRepository.findAll();
    }

    @Override
    public Optional<Balance> findBalanceById(Long id) {
        return balanceRepository.findById(id);
    }

    @Override
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public void deleteBalance(Long id) {
        balanceRepository.deleteById(id);
    }



    @Override
    public void increaseBalance(Long userId) {
        Optional<Balance> balanceOptional = balanceRepository.findById(userId);
        if (balanceOptional.isPresent()) {
            Balance balance = balanceOptional.get();
            double currentAmount = Double.parseDouble(balance.getAmount());
            currentAmount += 100;
            balance.setAmount(Double.toString(currentAmount));
            balanceRepository.save(balance);
        } else {
            // Создание нового баланса, если он отсутствует
            Balance balance = new Balance();
            balance.setId(userId);
            balance.setAmount(Double.toString(100));
            balanceRepository.save(balance);
        }
    }

    @Override
    public void decreaseBalance(Long userId, double amount) {
        Optional<Balance> balanceOptional = balanceRepository.findById(userId);
        if (balanceOptional.isPresent()) {

            Balance balance = balanceOptional.get();
            double currentAmount = Double.parseDouble(balance.getAmount());
            if (currentAmount >= amount) {
                currentAmount -= amount;
                balance.setAmount(Double.toString(currentAmount));
                balanceRepository.save(balance);
            } else {
                throw new IllegalArgumentException("Insufficient balance for user with id: " + userId);
            }
        } else {
            throw new RuntimeException("Balance not found for user with id: " + userId);
        }
    }
}
