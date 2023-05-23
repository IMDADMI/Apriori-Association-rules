package com.imdachillo.associationrules.services;

import com.imdachillo.associationrules.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AprioriService {
    private List<List<String>> dataSet;
    public static List <Transaction> total = new ArrayList<>();
    private final UtilService utils;

    @Autowired
    public AprioriService(UtilService utils) {
        this.utils = utils;
    }
    public List<Transaction> GenerationFrequentArticles(int minSupport,List<List<String>> dataSet){
        this.dataSet = dataSet;
        System.out.println("start ...");

        List<Transaction> transactionFrequent;
        List<Transaction> transactionCandidat;

        transactionFrequent = generationArticlesIndividuel();

        List<Transaction> filteredTransactions;
        double start = new Date().getTime();
        for (int k = 2;; k++) {
            transactionCandidat = candidatEnsemble(transactionFrequent,k);
            filteredTransactions = filtrage(transactionCandidat,minSupport);
            if (filteredTransactions.isEmpty())
                break;
            transactionFrequent = filteredTransactions;
        }
        double end = new Date().getTime();
        System.out.println("end\ntime of generation is : "+(end-start));
        return transactionFrequent;
    }

    public List<Transaction> generationArticlesIndividuel(){
        List<Transaction> transactionUniqueHashed = new ArrayList<>();
        Map<String,Integer> _transactionUnique = new HashMap<>();

        for (List<String> articles : dataSet){
            total.add(new Transaction(articles,0));
            for (String article : articles)
                _transactionUnique.merge(article, 1, Integer::sum);
        }
        _transactionUnique.forEach((article,support_count)->{
            List<String> articles = new ArrayList<>();
            articles.add(article);
            transactionUniqueHashed.add(new Transaction(articles,support_count));
        });
        return transactionUniqueHashed ;
    }

    public  List<Transaction>  candidatEnsemble(List<Transaction> listCand, int k){
        listCand = modiferSupport(utils.combinson(listCand,k));
        return listCand;
    }

    public List<Transaction> filtrage(List<Transaction> list, int minSupport){
        List<Transaction> newListe = new ArrayList<>();
        for (Transaction transaction :list)
            if(transaction.getSupport() >= minSupport)
                newListe.add(transaction);
        return newListe;
    }

    public  List<Transaction>  modiferSupport(List<Transaction> listCand){
        for (Transaction transaction : listCand) {
            transaction.setSupport(utils.calculFequency(transaction,total));
        }
        return listCand;
    }


}
