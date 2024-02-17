package backend.ocdbackend.repository;

import backend.ocdbackend.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);

    void deleteById(ObjectId id);

    // Additional custom queries can be defined here

    // CRUD methods are automatically provided by the base interface
    // Save a user
    // Delete a user
    // Find a user by ID
    // Find all users
    // ...
}
