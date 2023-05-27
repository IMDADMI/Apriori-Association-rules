package com.imdachillo.associationrules.rmi;

import com.imdachillo.associationrules.Configurations;
import com.imdachillo.associationrules.services.interfaces.AssociationService;
import com.imdachillo.associationrules.services.implementations.AssociationServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AssociationServer {
    /***
     * <b><i>here the main method should be executed so that the server push the available methods to register and waiting to receive the calls from the clients to execute the specified methods.</i></b>
     * @param args
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configurations.class);
        AssociationService associationService = context.getBean(AssociationServiceImpl.class);
        AssociationService service = (AssociationService) UnicastRemoteObject.exportObject(associationService,0);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind("AssociationService",service);

    }
}
