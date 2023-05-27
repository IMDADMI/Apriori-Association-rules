package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.models.Transaction;
import com.imdachillo.associationrules.services.interfaces.UtilService;
import org.apache.commons.math3.util.Combinations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilServiceImpl implements UtilService {

    /**
     * <b><i>this method use the method below for generating composition from the k-frequent-item-set.</i></b>
     * @param k this is the number of composition that we want to make.
     * @param k_frequent_item_set this is the base item-set that we want to generate the k-composition from.
     * @return this return the new k-frequent-item-set that contain new composition values.
     * */
    @Override
    public List<Transaction> combination(List<Transaction> k_frequent_item_set, int k){
        Set<String> newList = new HashSet<>();
        k_frequent_item_set.forEach(e->newList.addAll(e.getArticles()));
        if(newList.size() < k)
            return null;

        return getCombination(newList.stream().toList(),k,true,k_frequent_item_set);
    }
    /**
     * <b><i>this is the main method that generate compositions.</i></b>
     * @param isPrune this boolean variable indicate if we want to make the prune step or not.
     * @param k_frequent_item_set this list is to help up in the composition work specifically in the prune step.
     * @param r this is the number of composition that we want.
     * @param list this is the list that we want to generate the k-item-set from.
     * @return the return value is the new r-frequent-item-set
     * */
    @Override
    public List<Transaction> getCombination(List<String> list,int r,boolean isPrune,List<Transaction> k_frequent_item_set){
        List<Transaction> transactions = new ArrayList<>();
        Combinations combinations = new Combinations(list.size(), r);
        for (int[] indices : combinations){
            //join step
            Transaction t = new Transaction(UtilService.getCombination(list, indices),-1);
            if(isPrune && r > 2){
                if(k_frequent_item_set!=null && pruneStep(k_frequent_item_set,t))
                    transactions.add(t);
            }else
                transactions.add(t);
        }
        return transactions;
    }
    /**
     * <i>this is the method that implement the apriori property which is <b>All subsets of a frequent item-set must be frequent</b><i/>
     * @param kFrequentItemSet the first parameter is the parent list
     * @param candidate the second parameter is the child list that we want to make subsets from it and verify the property
     * @return the returned value is a bollean that indicate if the candidate is able to be frequent item set or not
     * */
    @Override
    public boolean pruneStep(List<Transaction> kFrequentItemSet, Transaction candidate) {
        int size = candidate.getArticles().size();
        List<Transaction> ts = getCombination(candidate.getArticles(),size-1,false,null);
        for (Transaction transaction : ts)
            if (!kFrequentItemSet.contains(transaction))
                return false;
        return true;
    }
    /**
     * <b><i>this method calculate the frequency the candidate in the transaction list</i></b>
     * @param candidate this is the candidate that we want to get it frequency
     * @param transactions this is the transaction that either contain the candidate or not.
     * */
    @Override
    public int FrequencyCalculator(Transaction candidate, List<Transaction> transactions){
        int s_c = 0;
        for (Transaction list: transactions)
            if(list.equalsC(candidate))
                s_c++;
        return s_c;
    }
}
