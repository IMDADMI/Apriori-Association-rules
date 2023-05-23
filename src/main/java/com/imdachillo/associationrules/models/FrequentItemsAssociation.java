package com.imdachillo.associationrules.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FrequentItemsAssociation {
    @JsonProperty(value = "frequentItemSet", required = true)
    List<Transaction> frequentItemSet;
    @JsonProperty(value = "association", required = true)
    List<Association> associationList;
    @JsonProperty(value = "processTime", required = true)
    double ProcessTime;

}
