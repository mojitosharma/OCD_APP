package backend.ocdbackend.repository;

import backend.ocdbackend.model.ApplicationUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends MongoRepository<ApplicationUser, ObjectId> {

    Optional<ApplicationUser> findByEmail(String email);
}