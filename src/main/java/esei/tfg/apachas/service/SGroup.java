package esei.tfg.apachas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConGroup;
import esei.tfg.apachas.entity.Group;
import esei.tfg.apachas.model.MGroup;
import esei.tfg.apachas.repository.RGroup;
import java.sql.Timestamp;

@Service("SGroup")
public class SGroup {
    @Autowired
    @Qualifier("RGroup")
    private RGroup rGroup;

    @Autowired
    @Qualifier("ConGroup")
    private ConGroup conGroup;

    public synchronized Long insertGroup(MGroup mGroup) {
        Group group = conGroup.conMGroup(mGroup);
        Group existingGroup = rGroup.findByGroupId(group.getGroupId());
        if (existingGroup != null) {
            return 0L;
        } else {
            group.setGroupCreation(new Timestamp(System.currentTimeMillis()));
            group.setGroupRemoval(null);
            group.setGroupActive(true);
            return rGroup.save(group).getGroupId();
        }
    }

    public synchronized boolean updateGroup(MGroup mGroup) {
        Group group = conGroup.conMGroup(mGroup);
        Group existingGroup = rGroup.findByGroupId(group.getGroupId());
        if (existingGroup != null) {
            rGroup.save(group);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteGroup(long groupId) {
        Group existingGroup = rGroup.findByGroupId(groupId);
        if (existingGroup != null) {
            existingGroup.setGroupActive(false);
            existingGroup.setGroupRemoval(new Timestamp(System.currentTimeMillis()));
            rGroup.save(existingGroup);
            return true;
        } else {
            return false;
        }
    }
}
