package weShare.sharehappy.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.ExistingUserException;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dto.signup.SignupRequest;
import weShare.sharehappy.dto.signup.SignupResponse;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManager {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSummary signUp(SignupRequest request){
        Optional.ofNullable(repository.findByEmail(request.getEmail())).ifPresent(user -> {throw new ExistingUserException(user.getEmail());});
        String encodingPassword = passwordEncoder.encode(request.getPassword());
        return repository.save(new User(request.getEmail(),encodingPassword,request.getNickname(), LocalDateTime.now())).changeToUserSummary();
    }

}
