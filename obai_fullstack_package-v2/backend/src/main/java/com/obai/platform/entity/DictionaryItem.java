package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sys_dictionary_item")
public class DictionaryItem extends BaseEntity {
    public String dictCode;
    public String itemKey;
    public String itemValue;
    public Integer sortNo = 0;
    public Boolean enabled = true;
}
