/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 *
 * @author jfwalto
 */
public class TestTopologyBuilder {
    public static String DRPC_NAME = "Parallelism_Test_";
    public FixedBatchSpout createSpout() {
        
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("event"), 4,
                new Values("event 1"),
                new Values("event 2"),
                new Values("event 3"),
                new Values("event 1"));
        spout.setCycle(false);
        
        return spout;
    }

    public StormTopology buildTopology(int parallelism) {
        TridentTopology topology = new TridentTopology();
         FixedBatchSpout spout = createSpout();
         TridentState state = topology.newStream("events", spout).name("EVENT_SPOUT").parallelismHint(parallelism).shuffle().partitionPersist(new InMemoryTridentStateFactory(), spout.getOutputFields(),new TestStateUpdater()).parallelismHint(parallelism);
         
         topology.newDRPCStream(DRPC_NAME+parallelism).name("DRPC_QUERY_SPOUT").parallelismHint(parallelism).broadcast().stateQuery(state, new InMemoryTridentStateQuery(), new Fields("output")).name("DRPC_STATE_QUERY").parallelismHint(parallelism).aggregate(new Fields("output"), new StateResultAggregator(), new Fields("combinedOtuput") ).parallelismHint(parallelism);
         
         return topology.build();
    }
    
    public static void main(String[] args) throws Exception{
        
        Config conf = new Config();
        conf.setNumWorkers(3);
        TestTopologyBuilder topologyBuilder = new TestTopologyBuilder();
        System.out.println("Pass in parallelism value as first arg");
        StormTopology testTopology = topologyBuilder.buildTopology(Integer.parseInt(args[0]));
        System.out.println("Submitting topology to storm cluster with parallelism of "+args[0]);;
        String topologyName = "Test_Parallelism_"+args[0];
        System.out.println("Topology name: "+topologyName);
        StormSubmitter.submitTopology(topologyName, conf, testTopology);
           
    }
    
}
