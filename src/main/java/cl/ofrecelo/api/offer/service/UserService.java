package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.User;

public interface UserService {
   User create(User users);
   Boolean resetPassword(String email);
   String validateEmail(String cod);
}
