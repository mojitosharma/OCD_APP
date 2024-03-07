package backend.ocdbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import backend.ocdbackend.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends MongoRepository<Role, ObjectId> {
    Optional<Role> findByAuthority(String authority);
}
