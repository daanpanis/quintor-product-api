package nl.quintor.dpanis.productapi.services;

import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User registerUser(RegistrationModel model);

}
