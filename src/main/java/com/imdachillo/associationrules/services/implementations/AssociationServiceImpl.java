package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.models.Association;
import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.models.Transaction;
import com.imdachillo.associationrules.services.interfaces.AprioriService;
import com.imdachillo.associationrules.services.interfaces.AssociationService;
import com.imdachillo.associationrules.services.interfaces.DataService;
import com.imdachillo.associationrules.services.interfaces.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AssociationServiceImpl implements AssociationService {
    private final DataService dataservice;
    private final AprioriService aprioriService;
    private final UtilService utils ;


    @Autowired
    public AssociationServiceImpl(DataService service, AprioriService aprioriService, UtilService utils) {
        this.dataservice = service;
        this.aprioriService = aprioriService;
        this.utils = utils;
    }

    @Override
    public FrequentItemsAssociation getAssociationRules(String path, String min_supp, String min_conf) throws IOException {
        double start = new Date().getTime();
        List<List<String>> itemSets = dataservice.getDataFromDataSet(path);
        List<Transaction> lastFrequentItemsSets = aprioriService.GenerationFrequentArticles(Integer.parseInt(min_supp),itemSets);
        List<Association> rules = this.generationReglesAssoctiation(lastFrequentItemsSets,Integer.parseInt(min_conf));
        double end = new Date().getTime();
        double time = end-start;
        AprioriServiceImpl.total.clear();
        return new FrequentItemsAssociation(lastFrequentItemsSets,rules,time);
    }
    @Override
    public void writeTransactions(String transactions) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\dataSet\\userData.csv"));
        writer.write(transactions);
        writer.close();
    }

    @Override
    public List<Association> generationReglesAssoctiation(List<Transaction> frequentArticles, double minConfidence){
        List<Association> _associations = new ArrayList<>();
        for(Transaction transaction : frequentArticles){
            for (int combination = 1; combination < transaction.getArticles().size(); combination++) {
                List<Transaction> joinTransaction = utils.getCombination(transaction.getArticles(),combination,false,null);
                for(Transaction ass : joinTransaction){
                    ass.setSupport(utils.calculFequency(ass, AprioriServiceImpl.total));
                    List<String> removedElement = new ArrayList<>(transaction.getArticles());
                    List<String> l_s = new ArrayList<>(transaction.getArticles());
                    l_s.removeAll(ass.getArticles());
                    removedElement.removeAll(ass.getArticles());
                    double l_support = transaction.getSupport();
                    double s_support = ass.getSupport();
                    double l_s_support = utils.calculFequency(new Transaction(l_s), AprioriServiceImpl.total);
                    double confidence_value = (l_support/s_support)*100;
                    if(confidence_value >= minConfidence){
                        Association association = new Association(ass.getArticles(),removedElement,confidence_value,confidence_value/l_s_support);
                        _associations.add(association);
                    }
                }
            }
        }
        return _associations;
    }


}