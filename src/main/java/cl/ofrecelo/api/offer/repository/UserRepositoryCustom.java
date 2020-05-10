package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.User;
import org.bson.types.ObjectId;

public interface UserRepositoryCustom {
    User updateProfile(String objectId, User user);
    User updatePassword(String email, String newPassword);
}
