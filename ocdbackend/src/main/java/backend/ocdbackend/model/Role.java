package backend.ocdbackend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "roles")
public class Role implements GrantedAuthority {

    @Id
    private ObjectId id; // Use ObjectId for the _id field
    private String authority;

    public Role() {
        super();
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(ObjectId id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public ObjectId getId(){
        return this.id;
    }

    public void setId(ObjectId id){
        this.id = id;
    }

}


