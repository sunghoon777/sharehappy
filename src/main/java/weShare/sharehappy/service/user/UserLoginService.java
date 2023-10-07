package weShare.sharehappy.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.user.NoExistingUserException;
import weShare.sharehappy.Exception.auth.PasswordMismatchException;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.dao.OrganizationRepository;
import weShare.sharehappy.dto.login.LoginRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.Organization;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLoginService {

    private final DonorRepository donorRepository;
    private final OrganizationRepository organizationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSummary login(LoginRequest loginRequest){
        UserSummary userSummary;
        String encodingPassword = "";
        if(loginRequest.getOrganizationLogin()){
            Organization organization = Optional.ofNullable(organizationRepository.findByEmail(loginRequest.getEmail())).orElseThrow(()->new NoExistingUserException());
            encodingPassword = organization.getPassword();
            userSummary = organization.changeToUserSummary();
        }
        else{
            Donor donor = donorRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NoExistingUserException());
            encodingPassword = donor.getPassword();
            userSummary = donor.changeToUserSummary();
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(),encodingPassword)){
            throw new PasswordMismatchException();
        }
        return userSummary;
    }
}
