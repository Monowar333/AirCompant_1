package org.example.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.example.central.entity.Status;

import java.io.Serializable;
import java.util.Stack;

@Getter
@Setter
@EqualsAndHashCode
@FieldNameConstants
public class FlightSearch implements Serializable {
    
    String origin;
    
    String destination;

    Long etd;

    Long eta;

    Status status;
}
