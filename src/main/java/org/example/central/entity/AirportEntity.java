package org.example.central.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "airport")
@Entity
@Getter
@Setter
public class AirportEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "country")
    String country;
}
