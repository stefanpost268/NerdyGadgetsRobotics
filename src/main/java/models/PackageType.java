package models;

import jakarta.persistence.*;

@Entity
@Table(name = "packagetypes")
public class PackageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PackageTypeID;
}
