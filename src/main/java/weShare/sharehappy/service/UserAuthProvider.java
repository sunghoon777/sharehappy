package weShare.sharehappy.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.NoExistingUserException;
import weShare.sharehappy.Exception.PasswordMismatchException;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dto.login.LoginRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.User;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthProvider {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSummary login(LoginRequest loginRequest){
        User user = Optional.ofNullable(repository.findByEmail(loginRequest.getEmail())).orElseThrow(()->new NoExistingUserException());
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new PasswordMismatchException();
        }
        return user.changeToUserSummary();
    }
}
