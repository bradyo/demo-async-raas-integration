package demo.async_tangocard_integration.user;

import demo.async_tangocard_integration.config.StubUserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final StubUserSettings stubUserSettings;
    
    public User getCurrentUser() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            User user = new User();
            user.setName(stubUserSettings.getName());
            user.setEmailAddress(stubUserSettings.getEmailAddress());
            userRepository.save(user);
            return user;
        }
    }
    
}
