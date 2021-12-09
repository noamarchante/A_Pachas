package esei.tfg.apachas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "uGroup")
@Table(name = "uGroup")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "groupId")
    private long groupId;

    @Column(name = "groupName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String groupName;

    @Column(name = "groupDescription", length = 155)
    @Size(min=0,max = 155)
    private String groupDescription;

    @Lob
    @Column(name = "groupPhoto", length = 100000)
    private String groupPhoto;

    @Column(name = "groupCreation")
    private Timestamp groupCreation;

    @Column(name = "groupRemoval")
    private Timestamp groupRemoval;

    //ATRIBUTO: GRUPO ACTIVO (SI SE HA ELIMINADO O NO)
    @Column(name = "groupActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean groupActive;

    @NotNull
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupOwner", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Transient
    private Set<GroupUser> groupUserSet = new HashSet<>();

    public Group(){

    }

    public Group(long groupId, String groupName, String groupDescription, String groupPhoto, User user) {
        this.groupId = groupId;
        this.setGroupName(groupName);
        this.setGroupDescription(groupDescription);
        this.setGroupPhoto(groupPhoto);
        this.setUser(user);
        this.setGroupCreation(new Timestamp(System.currentTimeMillis()));
        this.setGroupRemoval(null);
        this.setGroupActive(true);
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<GroupUser> getUserGroupUserSet() {
        return groupUserSet;
    }

    public void setUserGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getGroupCreation() {
        return groupCreation;
    }

    public void setGroupCreation(Timestamp groupCreation) {
        this.groupCreation = groupCreation;
    }

    public Timestamp getGroupRemoval() {
        return groupRemoval;
    }

    public void setGroupRemoval(Timestamp groupRemoval) {
        this.groupRemoval = groupRemoval;
    }

    public boolean isGroupActive() {
        return groupActive;
    }

    public void setGroupActive(boolean groupActive) {
        this.groupActive = groupActive;
    }

    public Set<GroupUser> getGroupUserSet() {
        return groupUserSet;
    }

    public void setGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }
}
