package babich.projects.demo.service;

import babich.projects.demo.dto.RegistrationDto;
import babich.projects.demo.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
