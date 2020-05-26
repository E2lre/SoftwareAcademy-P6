package com.paymybuddy.paysystem.model;

import javax.persistence.*;

@Entity
@Table(name="credit")
@DiscriminatorValue("Credit")
public class Credit extends Transaction {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "buddy_credit_fk2"), value =
            {
                    @JoinColumn( name = "buddy_person_id", referencedColumnName="person_id"),
                    @JoinColumn( name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
            }
    )
    private Buddy buddy;

    public Credit() {}



}
