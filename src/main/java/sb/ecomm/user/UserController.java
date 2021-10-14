package sb.ecomm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.user.dto.CreateUserDTO;
import sb.ecomm.user.dto.UserDTO;
import sb.ecomm.user.dto.UpdateUserDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    UserDTO findUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PostMapping
    UserDTO createNewUserAccount(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createNewUserAccount(createUserDTO);
    }

    @PutMapping("/{id}")
    UserDTO updateUserAccount(@PathVariable UUID id,
                                  @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUserAccount(id, updateUserDTO);
    }

    @DeleteMapping("/{id}")
    void deleteUserAccount(@PathVariable UUID id) {
        userService.deleteUserAccount(id);
    }
}
