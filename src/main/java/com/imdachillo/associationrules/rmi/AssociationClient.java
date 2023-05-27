package com.imdachillo.associationrules.rmi;

import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.services.interfaces.AssociationService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AssociationClient {

    /***
     * <i><b>in this method the client ask the server to get the association rules by provide the required parameters.</i></b>
     * @param path this is the path where the dataset is locate
     * @param min_support this is the minimum support
     * @param min_confidence this is the minimum confidence
     * @return it returns the association rules including the last frequent item set and time of calculations.
     * @throws NotBoundException
     * @throws IOException
     */
    public FrequentItemsAssociation getAssociationsRulesFromTheServer(String path, String min_support, String min_confidence) throws NotBoundException, IOException {
        Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        AssociationService associationService = (AssociationService) registry.lookup("AssociationService");
        return associationService.getAssociationRules(path,min_support,min_confidence);
    }

}
