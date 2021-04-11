package esei.tfg.apachas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
    @Pattern(regexp = "^[[:alpha:]áéíóúÁÉÍÓÚñÑ]+(?:[[:space:]][[:alnum:]áéíóúÁÉÍÓÚñÑ]+)*$")
    private String userGroupName;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    @Transient
    private Set<UserGroupUser> userGroupUserSet = new HashSet<>();

    public UserGroup(){

    }

    public UserGroup(long userGroupId, String userGroupName) {
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
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
}
