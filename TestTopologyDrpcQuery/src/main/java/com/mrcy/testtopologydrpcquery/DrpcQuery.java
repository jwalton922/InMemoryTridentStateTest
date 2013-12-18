/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopologydrpcquery;

import backtype.storm.utils.DRPCClient;
import com.mrcy.testtopology.TestTopologyBuilder;

/**
 *
 * @author jfwalto
 */
public class DrpcQuery {

    public static void main(String[] args) {
        DRPCClient client = new DRPCClient("localhost", 3772);
        int parallelism = Integer.parseInt(args[0]);
        while (true) {
            try {
                String result = client.execute(TestTopologyBuilder.DRPC_NAME + parallelism, "");
                System.out.println("DRPC result: "+result);                
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
