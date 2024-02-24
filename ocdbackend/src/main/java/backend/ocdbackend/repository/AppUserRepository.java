package backend.ocdbackend.repository;

import backend.ocdbackend.appuser.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository{
    Optional<AppUser> findByEmail(String email);
}
