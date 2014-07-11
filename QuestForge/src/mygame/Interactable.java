/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Interactable extends Node {
  
  public Node   model;
  public Quest  quest;
  public Long   lastAlert;
  public String contactMessage;
    
  public Interactable(Node interactable) {
    model     = interactable;
    lastAlert = System.currentTimeMillis() / 1000;
    setName(model.getName());
    }
    
  }
