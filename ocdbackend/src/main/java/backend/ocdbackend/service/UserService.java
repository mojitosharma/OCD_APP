package backend.ocdbackend.service;

import backend.ocdbackend.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
    User save(User user);

//     List<User> getUsersByTherapistId(String therapistId);
}
