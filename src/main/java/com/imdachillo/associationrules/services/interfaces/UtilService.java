package com.imdachillo.associationrules.services.interfaces;

import com.imdachillo.associationrules.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface UtilService {
    List<Transaction> combination(List<Transaction> k_frequent_item_set, int k);
    List<Transaction> getCombination(List<String> list,int r,boolean isPrune,List<Transaction> k_frequent_item_set);
    static List<String> getCombination(List<String> list, int[] indices) {
        List<String> combination = new ArrayList<>();
        for (int index : indices)
            combination.add(list.get(index));

        return combination;
    }
    boolean pruneStep(List<Transaction> kFrequentItemSet, Transaction candidate);
    int FrequencyCalculator(Transaction candidate, List<Transaction> transactions);
}
