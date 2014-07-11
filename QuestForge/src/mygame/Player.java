package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;
import java.util.ArrayList;

public class Player extends Node {
 
  public BetterCharacterControl phys;
  public Node                   model;
  public AnimControl            animControl;
  public AnimChannel            armChannel;
  public AnimChannel            legChannel;
  public boolean                hasSwung;
  public boolean                intCheck;
  public long                   lastSwing;
  public float                  speedMult;
  public QuestList              questList;
  public ArrayList<String>      inventory;
  
  public Player(AppStateManager stateManager) {
     
    model       = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Person/Person.j3o");  
    phys        = new BetterCharacterControl(.5f, 1f, 100);
    animControl = model.getChild("Person").getControl(AnimControl.class);
    armChannel  = animControl.createChannel();
    legChannel  = animControl.createChannel();
    questList   = new QuestList(this);
    inventory   = new ArrayList();
    model.scale(.3f);
    attachChild(model);
    model.addControl(phys);
    armChannel.addFromRootBone("TopSpine");
    legChannel.addFromRootBone("BottomSpine");
    armChannel.setAnim("ArmIdle");
    legChannel.setAnim("LegsIdle"); 
    attachChild(model);
    }
  
  public void swing(AppStateManager stateManager) {
    
    if (!hasSwung) {
    armChannel.setAnim("ArmSwing");
    armChannel.setSpeed(2);
    armChannel.setLoopMode(LoopMode.DontLoop);
    lastSwing = System.currentTimeMillis()/1000;
    hasSwung  = true;
    intCheck  = true;
    }
    
  }
  
  public void run(){
    if (!armChannel.getAnimationName().equals("ArmRun") && !hasSwung){
      armChannel.setAnim("ArmRun");
      }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
      }
    
    }
  
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle") && !hasSwung){
      armChannel.setAnim("ArmIdle");
      }
    
    if (!legChannel.getAnimationName().equals("LegsIdle")){
      legChannel.setAnim("LegsIdle");
      }
    
    }
  
  }
  
