/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import backtype.storm.tuple.Tuple;
import java.util.List;
import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;

/**
 *
 * @author jfwalto
 */
public class TestStateUpdater  extends BaseStateUpdater<InMemoryTridentState>{

    public void updateState(InMemoryTridentState state, List<TridentTuple> tuples, TridentCollector collector) {
        System.out.println("State updater received: "+tuples.size()+" tuples");
        for(int i = 0; i < tuples.size(); i++){
            System.out.println("Processing tuple at index: "+i);
            TridentTuple tuple = tuples.get(i);            
            if(tuple == null){
                System.out.println("state updater null tuple!");                
                continue;
            }
            System.out.println("Tuple at index "+i+" size: "+tuple.size());
            for(int j = 0; j < tuple.size(); j++){
                Object value = tuple.get(j);
                if(value == null){
                    System.out.println("state updater null tuple value at index "+j);;
                    continue;
                }
                System.out.println("Saving value: "+value.toString());
                state.saveEvent(value.toString());
            }
        }
    }
    
}
