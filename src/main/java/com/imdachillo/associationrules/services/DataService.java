package com.imdachillo.associationrules.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    public List<List<String>> getDataFromDataSet(String path) throws IOException {
        if(path.isEmpty())
            path="src\\dataSet\\Market_Basket_Optimisation.csv";
        List<List<String>> data;
        data = generationData(path);
        System.out.println(data.size());
        return data;
    }
    public List<List<String>> generationData(String link)throws IOException {
        List<List<String>> data =new ArrayList<>();
        FileReader fileReader = new FileReader(link);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String row;
        while ((row=bufferedReader.readLine())!=null){
            List<String> element = new ArrayList<>(Arrays.asList(row.split(",")));
            data.add(element);
        }

        return  data;
    }
}
