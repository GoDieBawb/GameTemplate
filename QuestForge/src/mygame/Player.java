package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player extends Node {
 
  public BetterCharacterControl phys;
  public Node                   model;
  public AnimControl            animControl;
  public AnimChannel            armChannel;
  public AnimChannel            legChannel;
  public boolean                hasSwung;
  public boolean                intCheck;
  public long                   lastSwing;
  public int                    bestLevel;
  public int                    currentLevel;
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
  
  public void saveScore(int newScore, AppStateManager stateManager) {
    
    String filePath;
      
    try {  
      filePath         = stateManager.getState(AndroidManager.class).filePath;
      }
    
    catch(NullPointerException e) {
      filePath = System.getProperty("user.home") + "/Save";  
      }
    
    BinaryExporter exporter = BinaryExporter.getInstance();
    Node score              = new Node();
    score.setUserData("Name", "Hope");
    score.setUserData("Score", newScore);
    File file               = new File(filePath + "/player.j3o");
    
      System.out.println("Saving Score");
    
    try {
        
      exporter.save(score, file);  
      System.out.println("Score saved to: " + filePath);
        
      }
    
    catch (IOException e) {
        
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", e);  
        System.out.println("Failure");
      }
    
      System.out.println("score completion");
    
    }
  
  public int readScore(AppStateManager stateManager) {
     String       filePath     = "/data/data/com.bigbawb.murdermaze/files";
     AssetManager assetManager = stateManager.getApplication().getAssetManager();
     
     assetManager.registerLocator(filePath, FileLocator.class);
     
     Node newNode;
     int  score;
     
     try {
       newNode = (Node) assetManager.loadModel("score.j3o");
       score = newNode.getUserData("Score");
       }
     
     catch (AssetNotFoundException ex) {
       saveScore(0, stateManager);
       score = 0;    
       }
     
     catch (IllegalArgumentException e) {
       saveScore(0, stateManager);
       score = 0;
       }
     
     
     System.out.println("You've loaded: " + score);
     return score;
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
  
