package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.models.Transaction;
import com.imdachillo.associationrules.services.interfaces.AprioriService;
import com.imdachillo.associationrules.services.interfaces.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AprioriServiceImpl implements AprioriService {
    private List<List<String>> dataSet;
    public static List <Transaction> total = new ArrayList<>();
    private final UtilService utils;

    @Autowired
    public AprioriServiceImpl(UtilService utils) {
        this.utils = utils;
    }
    @Override
    public List<Transaction> GenerationFrequentArticles(int minSupport,List<List<String>> dataSet){
        this.dataSet = dataSet;
        System.out.println("start ...");

        List<Transaction> transactionFrequent;
        List<Transaction> transactionCandidat;

        transactionFrequent = generationArticlesIndividuel();

        List<Transaction> filteredTransactions;
        double start = new Date().getTime();
        for (int k = 2;; k++) {
            if(transactionFrequent.size() == 1)
                if(transactionFrequent.get(0).getArticles().size() < k)
                    break;
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
    @Override
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
    @Override
    public  List<Transaction>  candidatEnsemble(List<Transaction> listCand, int k){
        List<Transaction> transactions = utils.combinson(listCand,k);
            listCand = modiferSupport(transactions);
        return listCand;
    }
    @Override
    public List<Transaction> filtrage(List<Transaction> list, int minSupport){
        List<Transaction> newListe = new ArrayList<>();
        for (Transaction transaction :list)
            if(transaction.getSupport() >= minSupport)
                newListe.add(transaction);
        return newListe;
    }
    @Override
    public  List<Transaction>  modiferSupport(List<Transaction> listCand){
        for (Transaction transaction : listCand)
            transaction.setSupport(utils.calculFequency(transaction,total));

        return listCand;
    }


}
