package esei.tfg.apachas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "userGroup")
@Table(name = "userGroup")
public class UserGroup implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userGroupId")
    private long userGroupId;

    @Column(name = "userGroupName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String userGroupName;

    @Column(name = "userGroupDescription", length = 155)
    @Size(min=0,max = 155)
    private String userGroupDescription;

    @Lob
    @Column(name = "userGroupPhoto", length = 100000)
    private String userGroupPhoto;

    @Column(name = "userGroupCreation")
    private Timestamp userGroupCreation;

    @Column(name = "userGroupRemoval", columnDefinition = "DATETIME(3)")
    private Timestamp userGroupRemoval;

    @NotNull
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userGroupOwner", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    @Transient
    private Set<UserGroupUser> userGroupUserSet = new HashSet<>();

    public UserGroup(){

    }

    public UserGroup(long userGroupId, String userGroupName, String userGroupDescription, String userGroupPhoto, User user) {
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
        this.userGroupDescription = userGroupDescription;
        this.userGroupPhoto = userGroupPhoto;
        this.userGroupCreation = new Timestamp(System.currentTimeMillis());
        this.userGroupRemoval = null;
        this.user =user;
    }

    public long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Set<UserGroupUser> getUserGroupUserSet() {
        return userGroupUserSet;
    }

    public void setUserGroupUserSet(Set<UserGroupUser> userGroupUserSet) {
        this.userGroupUserSet = userGroupUserSet;
    }

    public String getUserGroupDescription() {
        return userGroupDescription;
    }

    public void setUserGroupDescription(String userGroupDescription) {
        this.userGroupDescription = userGroupDescription;
    }

    public String getUserGroupPhoto() {
        return userGroupPhoto;
    }

    public void setUserGroupPhoto(String userGroupPhoto) {
        this.userGroupPhoto = userGroupPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getUserGroupCreation() {
        return userGroupCreation;
    }

    public void setUserGroupCreation(Timestamp userGroupCreation) {
        this.userGroupCreation = userGroupCreation;
    }

    public Timestamp getUserGroupRemoval() {
        return userGroupRemoval;
    }

    public void setUserGroupRemoval(Timestamp userGroupRemoval) {
        this.userGroupRemoval = userGroupRemoval;
    }
}
