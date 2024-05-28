package org.example.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.example.central.entity.Status;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@FieldNameConstants
public class Ticket implements Serializable {

    Long flightId;
    
    Integer place;
}