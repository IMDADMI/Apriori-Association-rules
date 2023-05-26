package com.imdachillo.associationrules.controllers;

import com.imdachillo.associationrules.models.FrequentItemsAssociation;
import com.imdachillo.associationrules.rmi.AssociationClient;
import com.imdachillo.associationrules.services.interfaces.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/rules")
public class AprioriAndAssociationController {
    private final AssociationService service;
    @Autowired
    public AprioriAndAssociationController(AssociationService service) {
        this.service = service;
    }
    @GetMapping("minTest")
    public ResponseEntity<FrequentItemsAssociation> getSmallTest() throws IOException, NotBoundException {
        AssociationClient associationClient = new AssociationClient();
        FrequentItemsAssociation association1 = associationClient.getAssociationsRulesFromTheServer("src\\dataSet\\test.csv", "2", "70");
        return new ResponseEntity<>(association1, HttpStatus.OK);

    }

    @GetMapping("maxTest")
    public ResponseEntity<FrequentItemsAssociation> getBigTest() throws IOException, NotBoundException {
        AssociationClient associationClient = new AssociationClient();
        FrequentItemsAssociation association1 = associationClient.getAssociationsRulesFromTheServer("", "50", "50");
        return new ResponseEntity<>(association1, HttpStatus.OK);

    }
    @PostMapping(path = "userTest")
    public ResponseEntity<FrequentItemsAssociation> get(@RequestBody Map<String,String> body) throws IOException, NotBoundException {
        String minimum_support = body.get("min_sup");
        String minimum_confidence = body.get("min_conf");
        String dataSet = body.get("dataset");
        service.writeTransactions(dataSet);
        AssociationClient associationClient = new AssociationClient();
        FrequentItemsAssociation association = associationClient.getAssociationsRulesFromTheServer("src\\dataSet\\userData.csv", minimum_support, minimum_confidence);
        return new ResponseEntity<>(association, HttpStatus.OK);
    }
    @PostMapping("userTest/auto")
    public ResponseEntity<FrequentItemsAssociation> autoChoosing(@RequestBody Map<String,String> body) throws IOException, NotBoundException {
        String dataSet = body.get("dataset");
        service.writeTransactions(dataSet);
        String minimumSupport = String.valueOf(service.getMinimumSupport());
        AssociationClient associationClient = new AssociationClient();
        FrequentItemsAssociation association = associationClient.getAssociationsRulesFromTheServer("src\\dataSet\\userData.csv", minimumSupport,"50" );
        return new ResponseEntity<>(association, HttpStatus.OK);

    }



}
