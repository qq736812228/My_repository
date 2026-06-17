package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "microbe_species")
public class MicrobeSpecies extends BaseEntity {
    public String latinName;
    public String cnName;
    public String functionTag;
    public String evidenceLevel;
    public String description;
}
