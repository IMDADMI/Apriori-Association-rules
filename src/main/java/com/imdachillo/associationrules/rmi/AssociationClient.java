package com.imdachillo.associationrules.rmi;

import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.services.interfaces.AssociationService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AssociationClient {

    public FrequentItemsAssociation getAssociationsRulesFromTheServer(String path, String min_support, String min_confidence) throws NotBoundException, IOException {
        Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        AssociationService associationService = (AssociationService) registry.lookup("AssociationService");
        return associationService.getAssociationRules(path,min_support,min_confidence);
    }

}
