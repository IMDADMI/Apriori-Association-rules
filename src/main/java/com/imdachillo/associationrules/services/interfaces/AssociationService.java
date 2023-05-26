package com.imdachillo.associationrules.services.interfaces;

import com.imdachillo.associationrules.models.Association;
import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.models.Transaction;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AssociationService extends Remote {
    FrequentItemsAssociation getAssociationRules(String path, String min_supp, String min_conf) throws RemoteException,IOException;
    List<Association> associationRulesGeneration(List<Transaction> frequentArticles, double minConfidence) throws RemoteException;
    void writeTransactions(String transactions) throws IOException,RemoteException;
    int getMinimumSupport() throws IOException;
}
