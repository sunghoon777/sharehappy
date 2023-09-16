package weShare.sharehappy.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.Exception.ExistingUserException;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dto.signup.DonorSignupRequest;
import weShare.sharehappy.dto.user.DonorSummary;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;
import weShare.sharehappy.vo.PasswordForgotUser;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManager {

    private final DonorRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RandomStringService randomStringService;

    public DonorSummary signUp(DonorSignupRequest request){
        Optional.ofNullable(repository.findByEmail(request.getEmail())).ifPresent(user -> {throw new ExistingUserException(user.getEmail());});
        String encodingPassword = passwordEncoder.encode(request.getPassword());
        return repository.save(new Donor(request.getEmail(),encodingPassword,LocalDateTime.now(),request.getNickname())).changeToDonorSummary();
    }

    @Transactional
    public String changePasswordForForgotUser(PasswordForgotUser forgotUser){
        String newPassword = randomStringService.getRandomPassword();
        String encodingPassword = passwordEncoder.encode(newPassword);
        User user = repository.findByEmail(forgotUser.getEmail());
        user.changePassword(encodingPassword);
        return newPassword;
    }
}
