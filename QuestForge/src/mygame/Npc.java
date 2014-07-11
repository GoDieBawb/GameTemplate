/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Npc extends Node {
 
  public Node model;
  public BetterCharacterControl phys;
  public AnimControl animControl;
  public AnimChannel armChannel;
  public AnimChannel legChannel;
  public Long        lastSpoke;
  public Quest       quest;
    
  public Npc(Node npc) {
      
    model       = npc;
    phys        = new BetterCharacterControl(.5f, 1f, 100);
    animControl = model.getChild("Person").getControl(AnimControl.class);
    armChannel  = animControl.createChannel();
    legChannel  = animControl.createChannel();
    lastSpoke   = System.currentTimeMillis() / 1000;
    setName(model.getName());
    model.addControl(phys);
    armChannel.addFromRootBone("TopSpine");
    legChannel.addFromRootBone("BottomSpine");  
    armChannel.setAnim("ArmIdle");
    legChannel.setAnim("LegsIdle");
    
    }
  
  public void run(){
    if (!armChannel.getAnimationName().equals("ArmRun")){
      armChannel.setAnim("ArmRun");
      }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
      }
    
    }
  
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle")){
      armChannel.setAnim("ArmIdle");
      }
    
    if (!legChannel.getAnimationName().equals("LegsIdle")){
      legChannel.setAnim("LegsIdle");
      }
    
    }  
     
  }
