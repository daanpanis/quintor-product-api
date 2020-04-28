package nl.quintor.dpanis.productapi.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

}
