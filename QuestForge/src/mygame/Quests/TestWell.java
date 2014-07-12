/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.Quests;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import mygame.Quest;

/**
 *
 * @author Bob
 */
public class TestWell extends Quest {
    
  public TestWell(AppStateManager stateManager, Node holder) {
    super(stateManager, holder);
    name = "TestQuest";
    }
  
  @Override
  public void act(){
      
    Quest testQuest = player.questList.getQuest("TestQuest");
    String speech;
    
    if (testQuest == null) {
      testQuest = new TestQuest(stateManager, player);
      player.questList.add(testQuest);
      testQuest.step = "Start";
      }
    
    if (testQuest.step.equals("Start")) {
      speech = "You shouldn't go messing around with someone's well";
      }
    
    else if (testQuest.step.equals("GetWater")) {
      if (player.inventory.contains("Bucket")) {
        speech = "You fill the bucket with water";
        testQuest.step = "HasWater";
        }
      
      else {
        speech = "You'll probably need a bucket";
        }
        
      }
    
    else if (testQuest.step.equals("HasWater")) {
      speech = "You already have water";
      }
    
    else {
      speech = "You don't need the well for anything now";
      }
    
    gui.showAlert(name, speech);
      
    }
    
  }
