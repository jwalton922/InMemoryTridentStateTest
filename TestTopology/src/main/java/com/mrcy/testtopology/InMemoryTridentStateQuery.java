/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import backtype.storm.tuple.Values;
import java.util.ArrayList;
import java.util.List;
import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseQueryFunction;
import storm.trident.tuple.TridentTuple;

/**
 *
 * @author jfwalto
 */
public class InMemoryTridentStateQuery extends BaseQueryFunction<InMemoryTridentState, List<String>> {

    public List<List<String>> batchRetrieve(InMemoryTridentState state, List<TridentTuple> args) {
        System.out.println("state query batchRetrieve called!");
        System.out.println("returning: "+state.getEvents());
        List<List<String>> returnValues = new ArrayList<List<String>>();
        returnValues.add(state.getEvents());
        return returnValues;
    }

    public void execute(TridentTuple tuple, List<String> result, TridentCollector collector) {
        System.out.println("state query execute result: " + result);
        if (tuple != null) {
            for (int i = 0; i < tuple.size(); i++) {
                if (tuple.get(i) != null) {
                    System.out.println("State query tuple index " + i + " value: " + tuple.get(i).toString());
                } else {
                    System.out.println("State query tuple index " + i + " is null");
                }
            }
        } else {
            System.out.println("state query execute tuple is null");
        }
        collector.emit(new Values(result));
    }
}
