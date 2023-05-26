package com.imdachillo.associationrules.services.interfaces;

import com.imdachillo.associationrules.models.Transaction;

import java.util.List;

public interface AprioriService {
    List<Transaction> GenerationFrequentArticles(int minSupport, List<List<String>> dataSet);
    List<Transaction> generationIndividualArticles();
    List<Transaction> listOfCandidates(List<Transaction> CandidatesList, int k);
    List<Transaction> filtering(List<Transaction> list, int minSupport);
    List<Transaction> modifierSupport(List<Transaction> CandidatesList);
}
