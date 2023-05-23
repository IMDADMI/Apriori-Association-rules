package com.imdachillo.associationrules.services;

import com.imdachillo.associationrules.models.Association;
import com.imdachillo.associationrules.models.Transaction;
import org.apache.commons.math3.util.Combinations;

import java.util.*;

public class Traitement {
    private List<List<String>> dataSet ;
    public List<Transaction> getTotal() {
        return total;
    }
    ArrayList <Transaction> total = new ArrayList<>();
    public Traitement(List<List<String>> dataSet) {
        this.dataSet = dataSet;
    }
//    public List<Transaction> combinson(List<Transaction> k_frequent_item_set, int k){
//           Set<String> newList = new HashSet<>();
//           k_frequent_item_set.forEach(e->newList.addAll(e.getArticles()));
//           return getCombination(newList.stream().toList(),k,true,k_frequent_item_set);
//
//
////        System.out.println("\n\n"+k+" item set :\n"+k_frequent_item_set);
////        ArrayList<Transaction> candidateItemSet = new ArrayList<>();
////        for (Transaction l:k_frequent_item_set) {
////            for (String s : l.getArticles()) {
////                for (Transaction l1:k_frequent_item_set) {
////                    for (String s1:l1.getArticles()) {
////                        ArrayList<String> ar = new ArrayList<>();
////                        ar.addAll(l.getArticles());
////                        if (!s1.equals(s) && !ar.contains(s1)) {
////                            ar.add(s1);
////                            if (!contien(ar,candidateItemSet) && ar.size()==k) {
////                                Transaction candidate = new Transaction(ar,-1);
////
////                                if(k > 2){
////                                    if(pruneStep(k_frequent_item_set,candidate))
////                                        candidateItemSet.add(candidate);
////                                }else
////                                    candidateItemSet.add(candidate);
////
////                            }
////                        }
////                    }
////                }
////            }
////        }
////        System.out.println("\nthe result is : "+candidateItemSet+"\n\n");
////
////        return  candidateItemSet;
//
//    }

//    private boolean pruneStep(List<Transaction> kFrequentItemSet, Transaction candidate) {
//        int size = candidate.getArticles().size();
//            List<Transaction> ts = getCombination(candidate.getArticles(),size-1,false,null);
//            for (Transaction transaction : ts)
//                if (!kFrequentItemSet.contains(transaction))
//                    return false;
//        return true;
//    }

//    public List<Transaction> getCombination(List<String> list,int r,boolean isPrune,List<Transaction> k_frequent_item_set){
//        List<Transaction> transactions = new ArrayList<>();
//        Combinations combinations = new Combinations(list.size(), r);
//        for (int[] indices : combinations){
//            //join step
//            Transaction t = new Transaction(getCombination(list, indices),-1);
//              if(isPrune && r > 2){
//                  //try to sout the k_frequent_item_set in the previous example
//                    if(k_frequent_item_set!=null && pruneStep(k_frequent_item_set,t))
//                        transactions.add(t);
//              }else
//                transactions.add(t);
//
//        }
////        System.out.println("suu : "+transactions);
//
//        return transactions;
//    }
//    public static List<String> getCombination(List<String> list, int[] indices) {
//        List<String> combination = new ArrayList<>();
//        for (int index : indices)
//            combination.add(list.get(index));
//
//        return combination;
//    }


//    public int calculFequency(Transaction candidate,List<Transaction> transactions){
//        int s_c = 0;
//        for (Transaction list: transactions)
//            if(list.equalsC(candidate))
//                s_c++;
//    return s_c;
//    }
//    public Boolean contien(List<String> transaction ,List<Transaction> transactions){
//        Boolean isHere=false;
//        for (Transaction tran: transactions) {
//            for (String a: transaction) {
//                if (tran.getArticles().contains(a)) {
//                    isHere=true;
//                }else {
//                    isHere=false;
//                    break;
//                }
//            }
//            if (isHere){
//                break;
//            }
//
//        }
//        return isHere;
//    }

    //this one
//    public List<Transaction> generationArticlesIndividuel(){
//        List<Transaction> transactionUniqueHashed = new ArrayList<>();
//        Map<String,Integer> _transactionUnique = new HashMap<>();
//
//        for (List<String> articles : dataSet){
//            total.add(new Transaction(articles,0));
//            for (String article : articles)
//                _transactionUnique.merge(article, 1, Integer::sum);
//        }
//        _transactionUnique.forEach((article,support_count)->{
//            List<String> articles = new ArrayList<>();
//            articles.add(article);
//            transactionUniqueHashed.add(new Transaction(articles,support_count));
//        });
//        return transactionUniqueHashed ;
//    }
//    public  List<Transaction>  modiferSupport(List<Transaction> listCand){
//        for (Transaction transaction : listCand) {
//            transaction.setSupport(calculFequency(transaction,total));
//        }
//        return listCand;
//    }
    //this one
//    public  List<Transaction>  candidatEnsemble(List<Transaction> listCand, int k){
//        listCand = modiferSupport(combinson(listCand,k));
//        return listCand;
//    }
//
    //this one

//    public List<Transaction> filtrage(List<Transaction> list, int minSupport){
//        List<Transaction> newListe = new ArrayList<>();
//        for (Transaction transaction :list)
//            if(transaction.getSupport() >= minSupport)
//                newListe.add(transaction);
//        return newListe;
//    }
    //calculate
    //this one
//    public List<Transaction> GenerationFrequentArticles(int minSupport){
//        List<Transaction> transactionFrequent;
//        List<Transaction> transactionCandidat;
//
//        transactionFrequent = generationArticlesIndividuel();
//        List<Transaction> filteredTransactions;
//        for (int k = 2;; k++) {
//            transactionCandidat = candidatEnsemble(transactionFrequent,k);
//            filteredTransactions = filtrage(transactionCandidat,minSupport);
//                if (filteredTransactions.isEmpty())
//                    break;
//                transactionFrequent = filteredTransactions;
//        }
//        return transactionFrequent;
//    }
//    public List<Association> generationReglesAssoctiation(List<Transaction> frequentArticles, double minConfidence){
//        List<Association> _associations = new ArrayList<>();
//        for(Transaction transaction : frequentArticles){
//            for (int combination = 1; combination < transaction.getArticles().size(); combination++) {
//                List<Transaction> joinTransaction = getCombination(transaction.getArticles(),combination,false,null);
//                for(Transaction ass : joinTransaction){
//                    ass.setSupport(calculFequency(ass,total));
//                    List<String> removedElement = new ArrayList<>(transaction.getArticles());
//                    removedElement.removeAll(ass.getArticles());
//                    double l_support = transaction.getSupport();
//                    double s_support = ass.getSupport();
//                    double confidence_value = (l_support/s_support)*100;
//                    if(confidence_value >= minConfidence){
//                        Association association = new Association(ass.getArticles(),removedElement,confidence_value,confidence_value/s_support);
//                        _associations.add(association);
//                    }
//                }
//            }
//        }
//        return _associations;
//    }
    public List<List<String>> getDataSet() {
        return dataSet;
    }
    public void setDataSet(List<List<String>> dataSet) {
        this.dataSet = dataSet;
    }


}
