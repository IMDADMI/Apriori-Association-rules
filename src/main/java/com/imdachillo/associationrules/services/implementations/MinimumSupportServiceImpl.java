package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.services.interfaces.MinimumSupportService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MinimumSupportServiceImpl implements MinimumSupportService {

    Map<String,Integer> frequencyOfItem;
    Map<String,Integer> supportValueOfItem;

    @Override
    public int getMinimumSupport(List<List<String>> dataset){
        double D = dataset.size();
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        frequencyOfItem = new HashMap<>();
        supportValueOfItem = new HashMap<>();
        for(List<String> transaction : dataset )
            transaction.forEach(e-> frequencyOfItem.merge(e,1,Integer::sum));

        double N = frequencyOfItem.size();
        for(List<String> transaction : dataset )
            transaction.forEach(e-> {
                double frequency = (double)frequencyOfItem.get(e);
                double S_d = frequency/D;
                double U_d = (frequency/2)*S_d;
                sum.updateAndGet(v ->  (v + U_d));
            });
        double avgSup = sum.get()/N;
        double minimum_threshold = avgSup/D;
        double minimumSupport = (minimum_threshold * D)/N;
        return (int)Math.ceil(minimumSupport);
    }
}
