/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import backtype.storm.task.IMetricsContext;
import java.util.Map;
import storm.trident.state.State;
import storm.trident.state.StateFactory;

/**
 *
 * @author jfwalto
 */
public class InMemoryTridentStateFactory implements StateFactory{

    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new InMemoryTridentState();
    }
    
}
