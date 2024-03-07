package backend.ocdbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUser implements UserDetails {

    @Id
    private ObjectId user_id; // MongoDB uses ObjectId for _id by default
    private Name name;
    private int patient_number;
    private Date dob;
    private String password;
    private String email;
    private Date day_of_enrollment;
    private String gender;
    private String education;
    private String occupation;
    private int therapist_id;
    private String profile_image; // Base64 encoded image data

    private Boolean locked = false;

    private Boolean enabled = true;
    private Set<ObjectId> authorities; // Assuming roles are stored as strings



    public ApplicationUser() {
        super();
        authorities = new HashSet<>();
    }

    public ApplicationUser(String email, String password, Set<ObjectId> authorities,
                           Name name, Integer patient_number, Date dob, Date day_of_enrollment,
                           String gender, String education, String occupation, Integer therapist_id,
                           String profile_image) {
        super();
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.name = name;
        this.patient_number = patient_number;
        this.dob = dob;
        this.day_of_enrollment = day_of_enrollment;
        this.gender = gender;
        this.education = education;
        this.occupation = occupation;
        this.therapist_id = therapist_id;
        this.profile_image = profile_image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (ObjectId authorityId : authorities) {
            GrantedAuthority authority = new SimpleGrantedAuthority(authorityId.toString());
            grantedAuthorities.add(authority);
        }
        return grantedAuthorities;
    }

    public ObjectId getUserId(){
        return this.user_id;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !locked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public ObjectId getId() {
        return user_id;
    }

    public void setId(ObjectId id) {
        this.user_id = id;
    }
}
