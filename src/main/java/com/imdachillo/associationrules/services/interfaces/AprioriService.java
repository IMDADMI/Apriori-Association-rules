package com.imdachillo.associationrules.services.interfaces;

import com.imdachillo.associationrules.models.Transaction;

import java.util.List;

public interface AprioriService {
    List<Transaction> GenerationFrequentArticles(int minSupport, List<List<String>> dataSet);
    List<Transaction> generationArticlesIndividuel();
    List<Transaction>  candidatEnsemble(List<Transaction> listCand, int k);
    List<Transaction> filtrage(List<Transaction> list, int minSupport);
    List<Transaction>  modiferSupport(List<Transaction> listCand);
}
