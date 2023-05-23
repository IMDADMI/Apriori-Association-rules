package com.imdachillo.associationrules.models;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Association implements Serializable {

    private List<String> antecedent, consequent;
    private double confidence;
    private double lift;

}
