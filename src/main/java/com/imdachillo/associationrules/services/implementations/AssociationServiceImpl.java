package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.models.Association;
import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.models.Transaction;
import com.imdachillo.associationrules.services.interfaces.*;
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
    private final MinimumSupportService minimumSupportService;

    @Autowired
    public AssociationServiceImpl(DataService service, AprioriService aprioriService, UtilService utils, MinimumSupportService minimumSupportService) {
        this.dataservice = service;
        this.aprioriService = aprioriService;
        this.utils = utils;
        this.minimumSupportService = minimumSupportService;
    }

    /**
     * this is the method used for getting the association rules
     * @param path the path parameter indicate the path of the dataset
     * @param min_supp the min_supp indicate the minimum support for generating frequent itemset
     * @param min_conf the min_conf is for the minimum confidence
     * @return the returned value is the association rules
    * */
    @Override
    public FrequentItemsAssociation getAssociationRules(String path, String min_supp, String min_conf) throws IOException {
        double start = new Date().getTime();
        List<List<String>> itemSets = dataservice.getDataFromDataSet(path);
        List<Transaction> lastFrequentItemsSets = aprioriService.GenerationFrequentArticles(Integer.parseInt(min_supp),itemSets);
        List<Association> rules = this.associationRulesGeneration(lastFrequentItemsSets,Integer.parseInt(min_conf));
        double end = new Date().getTime();
        double time = end-start;
        AprioriServiceImpl.total.clear();
        return new FrequentItemsAssociation(lastFrequentItemsSets,rules,time);
    }
    /**
     * this method is used to write user transaction into the userData.csv file
     * @param transactions the transactions are a list of string that represent transaction
     * */
    @Override
    public void writeTransactions(String transactions) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\dataSet\\userData.csv"));
        writer.write(transactions);
        writer.close();
    }

    /**
     * this method get the minimum support from the MinimumSupport service
     * */
    @Override
    public int getMinimumSupport() throws IOException {
        return minimumSupportService.getMinimumSupport(dataservice.getDataFromDataSet("src\\dataSet\\userData.csv"));
    }
    /**
     * this method is responsible for generating the association rules after getting the frequent item set
     * @param frequentArticles the first parameter is the list of frequent item set
     * @param minConfidence the second parameter is used for filtering the associations based on the minimum Confidence
     * */
    @Override
    public List<Association> associationRulesGeneration(List<Transaction> frequentArticles, double minConfidence){
        List<Association> _associations = new ArrayList<>();
        for(Transaction transaction : frequentArticles){
            for (int combination = 1; combination < transaction.getArticles().size(); combination++) {
                List<Transaction> joinTransaction = utils.getCombination(transaction.getArticles(),combination,false,null);
                for(Transaction ass : joinTransaction){
                    ass.setSupport(utils.FrequencyCalculator(ass, AprioriServiceImpl.total));
                    List<String> removedElement = new ArrayList<>(transaction.getArticles());
                    List<String> l_s = new ArrayList<>(transaction.getArticles());
                    l_s.removeAll(ass.getArticles());
                    removedElement.removeAll(ass.getArticles());
                    double l_support = transaction.getSupport();
                    double s_support = ass.getSupport();
                    double l_s_support = utils.FrequencyCalculator(new Transaction(l_s), AprioriServiceImpl.total);
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
