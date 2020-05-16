package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.exceptions.UserAlreadyCreatedException;
import cl.ofrecelo.api.offer.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User updateProfile(String objectId, User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(objectId)));
        User userDB = mongoTemplate.findOne(query, User.class);
        if(userDB!=null && user!=null) {
            if (user.getEmail() != null) {
                userDB.setEmail(user.getEmail());
            }
            if (user.get_id()!=null){
                userDB.set_id(user.get_id());
            }
            if(user.getName()!=null){
                userDB.setName(user.getName());
            }
            userDB.setLastName(user.getLastName());

            mongoTemplate.save(userDB);
        }
        return userDB;
    }

    @Override
    public User updatePassword(String email, String newPassword){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        User userDB = mongoTemplate.findOne(query, User.class);
        if(userDB!=null) {
            if (newPassword != null) {
                userDB.setPassword(newPassword);
            }
            mongoTemplate.save(userDB);
        }else{
            throw new UserAlreadyCreatedException(email);
        }
        return userDB;
    }
}
