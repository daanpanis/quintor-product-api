package nl.quintor.dpanis.productapi.services.implementations;

import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.exceptions.EmailExistsException;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import nl.quintor.dpanis.productapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    Expects model to be valid
     */
    @Override
    public User registerUser(RegistrationModel model) {
        if (userRepository.findByEmail(model.getEmail()) != null) {
            throw new EmailExistsException("User by email " + model.getEmail() + " already exists");
        }

        return null;
    }
}
