package com.imdachillo.associationrules.services.implementations;

import com.imdachillo.associationrules.services.interfaces.DataService;
import com.imdachillo.associationrules.services.interfaces.MinimumSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    private final MinimumSupportService service;

    @Autowired
    public DataServiceImpl(MinimumSupportService service) {
        this.service = service;
    }

    @Override
    public List<List<String>> getDataFromDataSet(String path) throws IOException {
        if(path.isEmpty())
            path="src\\dataSet\\Market_Basket_Optimisation.csv";
        return generationData(path);
    }
    @Override
    public List<List<String>> generationData(String link)throws IOException {
        List<List<String>> data =new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(link))){
            String row;
            while ((row=bufferedReader.readLine())!=null){
                List<String> element = new ArrayList<>(Arrays.asList(row.split(",")));
                data.add(element);
            }
        }
        return  data;
    }
}
