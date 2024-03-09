package backend.ocdbackend.model;

import java.util.Date;

public class RegistrationDTO {
    private String email;
    private String password;
    private Name name;
    private Integer patientNumber;
    private Date dob;
    private Date dayOfEnrollment;
    private String gender;
    private String education;
    private String occupation;
    private Integer therapistId;
    private String profileImage; // Assuming this is a Base64 encoded string

    public RegistrationDTO() {
        super();
    }

    public RegistrationDTO(String email, String password, Name name, Integer patientNumber, Date dob, Date dayOfEnrollment, String gender, String education, String occupation, Integer therapistId, String profileImage) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.patientNumber = patientNumber;
        this.dob = dob;
        this.dayOfEnrollment = dayOfEnrollment;
        this.gender = gender;
        this.education = education;
        this.occupation = occupation;
        this.therapistId = therapistId;
        this.profileImage = profileImage;
    }

    // Getters and setters for each field

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Integer getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(Integer patientNumber) {
        this.patientNumber = patientNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDayOfEnrollment() {
        return dayOfEnrollment;
    }

    public void setDayOfEnrollment(Date dayOfEnrollment) {
        this.dayOfEnrollment = dayOfEnrollment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Integer therapistId) {
        this.therapistId = therapistId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "Registration info: email: " + this.email + ", password: " + this.password + ", name: " + this.name + ", patient number: " + this.patientNumber + ", dob: " + this.dob + ", day of enrollment: " + this.dayOfEnrollment + ", gender: " + this.gender + ", education: " + this.education + ", occupation: " + this.occupation + ", therapist id: " + this.therapistId + ", profile image: " + this.profileImage;
    }
}
