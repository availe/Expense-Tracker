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
    private Boolean hasApproval;
    private String passHash;

    public UserRecord(Integer userId, String firstName, String lastName, String email, String createdOn, String lastLogin, Boolean isManager, Boolean isRoot, Boolean hasApproval, String passHash) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
        this.isManager = isManager;
        this.isRoot = isRoot;
        this.hasApproval = hasApproval;
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

    public Boolean getIsManager() {
        return isManager;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public String getPassHash() {
        return passHash;
    }
    public Boolean getHasApproval() {
        return hasApproval;
    }
}
