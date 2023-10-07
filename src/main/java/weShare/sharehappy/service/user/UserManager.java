package weShare.sharehappy.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.Exception.user.ExistingUserException;
import weShare.sharehappy.Exception.user.NoExistingUserException;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dto.signup.DonorSignupRequest;
import weShare.sharehappy.dto.user.DonorSummary;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;
import weShare.sharehappy.service.random.RandomStringService;
import weShare.sharehappy.vo.PasswordForgotUser;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManager {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RandomStringService randomStringService;

    public DonorSummary signUp(DonorSignupRequest request){
        Optional<Donor> optionalDonor = donorRepository.findByEmail(request.getEmail());
        if(optionalDonor.isPresent()){
            throw new ExistingUserException(optionalDonor.get().getEmail());
        }
        String encodingPassword = passwordEncoder.encode(request.getPassword());
        return donorRepository.save(new Donor(request.getEmail(),encodingPassword,LocalDateTime.now(),request.getNickname())).changeToDonorSummary();
    }

    @Transactional
    public String changePasswordForForgotUser(PasswordForgotUser forgotUser){
        User user = userRepository.findByEmail(forgotUser.getEmail()).orElseThrow(()-> new NoExistingUserException());
        String newPassword = randomStringService.getRandomPassword();
        String encodingPassword = passwordEncoder.encode(newPassword);
        user.changePassword(encodingPassword);
        return newPassword;
    }
}
