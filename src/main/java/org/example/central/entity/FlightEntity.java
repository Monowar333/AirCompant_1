package org.example.central.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "flight")
@Entity
@Getter
@Setter
public class FlightEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origin_id")
    AirportEntity origin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    AirportEntity destination;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "etd")
    long etd;

    @Column(name = "eta")
    long eta;
}
