package backend.ocdbackend.repository;

import backend.ocdbackend.model.ApplicationUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.bson.types.ObjectId;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<ApplicationUser, ObjectId> {

    Optional<ApplicationUser> findByEmail(String email);
    @Query(value = "{ 'email' : ?0 }", delete = true)
    void deleteByEmail(String email);

    @Query(value = "{ '_id' : ?0 }", delete = true)
    void deleteById(ObjectId id);
}