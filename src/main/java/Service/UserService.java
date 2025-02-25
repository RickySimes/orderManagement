package Service;

import Entity.User;
import Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name , String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email " + email + " already exists");
        }
        User user = new User(name, email);
        logger.info("User created: {}", user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<User> existingWithEmail = userRepository.findByEmail(email);
        if (existingWithEmail.isPresent() && !existingWithEmail.get().getId().equals(id)) {
            throw new RuntimeException("User with email " + email + " already exists");
        }

        user.setName(name);
        user.setEmail(email);

        user = userRepository.save(user);

        logger.info("User updated: {}", user);

        return user;

    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
        logger.info("User deleted: {} ({})", user.getName(), user.getEmail());
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
