package sb.ecomm.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UserDTO;
import sb.ecomm.user.dto.UpdateUserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    UserDTO findUserById(long id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return mapper.map(user, UserDTO.class);
    }

    UserDTO createNewUserAccount(CreateUserDTO createUserDTO) {
        User user = mapper.map(createUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return mapper.map(newUser, UserDTO.class);
    }

    UserDTO updateUserAccount(long id,
                                  UpdateUserDTO updateUserDTO) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        updateUserEmail(user,updateUserDTO);
        updateUserPassword(user, updateUserDTO);

        User savedUser = userRepository.save(user);

        return mapper.map(savedUser, UserDTO.class);
    }

    void deleteUserAccount(long id) {
        userRepository.deleteById(id);
    }

    private void updateUserEmail(User user,
                                     UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
    }

    private void updateUserPassword(User user,
                                        UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
    }
}
