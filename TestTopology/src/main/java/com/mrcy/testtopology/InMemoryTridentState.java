/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrcy.testtopology;

import java.util.ArrayList;
import java.util.List;
import storm.trident.state.State;

/**
 *
 * 
 * 
 * @author jfwalto
 */
public class InMemoryTridentState implements State{
   private List<String> database = new ArrayList<String>() ;

    public void beginCommit(Long txid) {
        
    }

    public void commit(Long txid) {
        
    }
   
   public void saveEvent(String event){
       database.add(event);
   }
   
  public List<String> getEvents(){
      return database;
  }
}
