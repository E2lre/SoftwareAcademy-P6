/*
package com.paymybuddy.paysystem.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BuddyKey implements Serializable {
*/
/*    @Id
    Long id;*//*


    @Column(name="person_id")
    long personId;

    @Column(name="friend_person_id")
    long friendPersonId;

    public BuddyKey() {
    }
    public BuddyKey(long personId, long friendPersonId) {
        this.personId = personId;
        this.friendPersonId = friendPersonId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getFriendPersonId() {
        return friendPersonId;
    }

    public void setFriendPersonId(long friendPersonId) {
        this.friendPersonId = friendPersonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BuddyKey that = (BuddyKey) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(friendPersonId, that.friendPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, friendPersonId);
    }


}
*/
