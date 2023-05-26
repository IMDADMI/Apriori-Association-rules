package com.imdachillo.associationrules.services.interfaces;

import java.io.IOException;
import java.util.List;

public interface DataService {
    List<List<String>> getDataFromDataSet(String path) throws IOException;
    List<List<String>> generationData(String link)throws IOException;
}
