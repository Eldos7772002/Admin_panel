package com.websocket.app.service.Impl;

import com.websocket.app.entity.Balance;
import com.websocket.app.entity.FirebaseUser;
import com.websocket.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<FirebaseUser> findAllUsers() {
        return null;
    }

    @Override
    public Optional<FirebaseUser> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public FirebaseUser saveUser(FirebaseUser user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public List<Balance> findAllBalances() {
        return null;
    }

    @Override
    public Optional<Balance> findBalanceById(Long id) {
        return Optional.empty();
    }

    @Override
    public Balance saveBalance(Balance balance) {
        return null;
    }

    @Override
    public void deleteBalance(Long id) {

    }
}
