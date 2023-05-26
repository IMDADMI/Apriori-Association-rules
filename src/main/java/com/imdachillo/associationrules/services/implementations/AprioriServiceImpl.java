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

        List<Transaction> frequentTransaction;
        List<Transaction> candidateTransaction;

        frequentTransaction = generationIndividualArticles();

        List<Transaction> filteredTransactions;
        for (int k = 2;; k++) {
            if(frequentTransaction.size() == 1)
                if(frequentTransaction.get(0).getArticles().size() < k)
                    break;
            candidateTransaction = listOfCandidates(frequentTransaction,k);

            filteredTransactions = filtering(candidateTransaction,minSupport);
            if (filteredTransactions.isEmpty())
                break;
            frequentTransaction = filteredTransactions;
        }
        return frequentTransaction;
    }
    @Override
    public List<Transaction> generationIndividualArticles(){
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
    public  List<Transaction> listOfCandidates(List<Transaction> CandidatesList, int k){
        List<Transaction> transactions = utils.combination(CandidatesList,k);
            CandidatesList = modifierSupport(transactions);
        return CandidatesList;
    }
    @Override
    public List<Transaction> filtering(List<Transaction> list, int minSupport){
        List<Transaction> newListe = new ArrayList<>();
        for (Transaction transaction :list)
            if(transaction.getSupport() >= minSupport)
                newListe.add(transaction);
        return newListe;
    }
    @Override
    public  List<Transaction> modifierSupport(List<Transaction> CandidatesList){
        for (Transaction transaction : CandidatesList)
            transaction.setSupport(utils.FrequencyCalculator(transaction,total));

        return CandidatesList;
    }


}
