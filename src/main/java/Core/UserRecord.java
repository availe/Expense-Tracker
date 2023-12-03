package Core;

public class UserRecord {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String createdOn;
    private String lastLogin;
    private Boolean isManager;
    private Boolean isRoot;
    private String passHash;

    public UserRecord(Integer id, String firstName, String lastName, String email, String createdOn, String lastLogin, Boolean isManager, Boolean isRoot, String passHash) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
        this.isManager = isManager;
        this.isRoot = isRoot;
        this.passHash = passHash;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public Boolean getManager() {
        return isManager;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public String getPassHash() {
        return passHash;
    }
}
