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
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configurations.class);
        AssociationService associationService = context.getBean(AssociationServiceImpl.class);
        AssociationService service = (AssociationService) UnicastRemoteObject.exportObject(associationService,0);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind("AssociationService",service);

    }
}
