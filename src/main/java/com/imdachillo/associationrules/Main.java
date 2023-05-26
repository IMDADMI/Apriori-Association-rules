package com.imdachillo.associationrules;
import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.services.implementations.AssociationServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configurations.class);
        AssociationServiceImpl  service = context.getBean(AssociationServiceImpl.class);
        FrequentItemsAssociation association =  service.getAssociationRules("src\\dataSet\\Market_Basket_Optimisation.csv", "50", "50");
        System.out.println(association.getAssociationList().size());

    }
}
