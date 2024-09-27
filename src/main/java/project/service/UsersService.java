package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Account;
import project.repository.UsersRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public Account findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
    public Account findByEmail(String username) {
        return usersRepository.findByEmail(username);
    }
    public Account save(Account user){
        return usersRepository.save(user);
    }
    public Account findByConfirmationToken(String token){
        return usersRepository.findByConfirmationToken(token);
    }

    public void enableUser(String email) {
        Account user = usersRepository.findByEmail(email);
        user.setActiveByEmail(Account.ACTIVE_BY_EMAIL.ACTIVE);
        usersRepository.save(user);
    }

    public void setConfirmedAt(String token) {
        Account user = usersRepository.findByConfirmationToken(token);
        user.setConfirmedAt(LocalDate.now());
        usersRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public boolean existsByMobile(String mobile) {
        return usersRepository.existsByMobile(mobile);
    }
}
