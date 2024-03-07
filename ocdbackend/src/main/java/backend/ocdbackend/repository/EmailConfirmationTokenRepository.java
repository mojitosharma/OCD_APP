package backend.ocdbackend.repository;

import backend.ocdbackend.model.EmailConfirmationToken;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface EmailConfirmationTokenRepository extends MongoRepository<EmailConfirmationToken, String> {
    EmailConfirmationToken findByToken(String token);
}

