package com.imdachillo.associationrules.services;

import com.imdachillo.associationrules.models.Transaction;
import org.apache.commons.math3.util.Combinations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilService {
    public List<Transaction> combinson(List<Transaction> k_frequent_item_set, int k){
        Set<String> newList = new HashSet<>();
        k_frequent_item_set.forEach(e->newList.addAll(e.getArticles()));
        return getCombination(newList.stream().toList(),k,true,k_frequent_item_set);
    }
    public List<Transaction> getCombination(List<String> list,int r,boolean isPrune,List<Transaction> k_frequent_item_set){
        List<Transaction> transactions = new ArrayList<>();
        Combinations combinations = new Combinations(list.size(), r);
        for (int[] indices : combinations){
            //join step
            Transaction t = new Transaction(getCombination(list, indices),-1);
            if(isPrune && r > 2){
                if(k_frequent_item_set!=null && pruneStep(k_frequent_item_set,t))
                    transactions.add(t);
            }else
                transactions.add(t);

        }
        return transactions;
    }
    public static List<String> getCombination(List<String> list, int[] indices) {
        List<String> combination = new ArrayList<>();
        for (int index : indices)
            combination.add(list.get(index));

        return combination;
    }
    private boolean pruneStep(List<Transaction> kFrequentItemSet, Transaction candidate) {
        int size = candidate.getArticles().size();
        List<Transaction> ts = getCombination(candidate.getArticles(),size-1,false,null);
        for (Transaction transaction : ts)
            if (!kFrequentItemSet.contains(transaction))
                return false;
        return true;
    }
    public int calculFequency(Transaction candidate,List<Transaction> transactions){
        int s_c = 0;
        for (Transaction list: transactions)
            if(list.equalsC(candidate))
                s_c++;
        return s_c;
    }
}
