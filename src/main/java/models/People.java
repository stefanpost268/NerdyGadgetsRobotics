package models;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PersonID;

    @Column(nullable = false, length = 50)
    private String FullName;

    public String getFullName() {
        return FullName;
    }

    public int getPersonID() {
        return PersonID;
    }
}
