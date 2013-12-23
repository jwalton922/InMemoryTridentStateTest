/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import backtype.storm.tuple.Values;
import java.util.ArrayList;
import java.util.List;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.CombinerAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 *
 * @author jfwalto
 */
public class StateResultAggregator implements CombinerAggregator<List<String>>{

    @Override
    public List<String> init(TridentTuple tuple) {
        System.out.println("Init in StateResultAggregator");
        List<String> initList = new ArrayList<String>();
        if(tuple != null){
            for(int i = 0; i < tuple.size(); i++){
                Object value = tuple.get(i);
                if(value == null){
                    System.out.println("result agg, tuple value at "+i+" is null");
                    continue;
                }
                if(value instanceof List){
                    
                    List<String> valueList = (List<String>) value;
                    System.out.println("Value list is: "+valueList.toString());
                    initList.addAll(valueList);
                } else {
                    System.out.println("Tuple at index "+i+" is type: "+value.getClass().getName());
                }
            }
        } else {
            System.out.println("init tuple isn ull!");
        }
        return initList;
    }

    @Override
    public List<String> combine(List<String> val1, List<String> val2) {
        System.out.println("val1: "+val1.toString()+"; val2: "+val2.toString());
        val1.addAll(val2);
        System.out.println("Combined value: "+val1.toString());
        return val1;
    }

    @Override
    public List<String> zero() {
        System.out.println("zero called");
        return new ArrayList<String>();
    }

    
    
    
    
//    public void execute(TridentTuple tuple, TridentCollector collector) {
//        System.out.println("StateResultAggregator");
//        if(tuple == null){
//            return;
//        } 
//        List<String> mergedResults = new ArrayList<String>();
//        for(int i = 0; i < tuple.size(); i++){
//            Object value = tuple.get(i);
//            if(value == null){
//                System.out.println("StateResultAggregator null value at: "+i);
//                continue;
//            }
//            System.out.println("Tuple value at "+i+": "+value.toString());
//            if(value instanceof List){
//                List<String> values = (List<String>) tuple.get(i);
//                mergedResults.addAll(values);
//            }
//        }
//        System.out.println("Merged results: "+mergedResults);
//        collector.emit(new Values(mergedResults));
//    }
    
    
}
