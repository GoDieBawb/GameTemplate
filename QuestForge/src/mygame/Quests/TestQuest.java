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
public class TestQuest extends Quest {
    
  public TestQuest(AppStateManager stateManager, Node holder) {
    super(stateManager, holder);
    name = "TestQuest";
    }
  
  @Override
  public void act() {
      
    Quest testQuest = player.questList.getQuest("TestQuest");
    String speech;
    
    if (testQuest == null) {
      testQuest = new TestQuest(stateManager, player);
      player.questList.add(testQuest);
      testQuest.step = "Start";
      }
    
    String step = testQuest.step;
    
    if (testQuest.step.equals("Start")) {
      speech = "Hey can you get me a bucket of water from the well";
      testQuest.step = "GetWater";
      }
    
    else if (testQuest.step.equals("GetWater")) {
      speech = "I thought I told you to get me some water";
      }
    
    else if (testQuest.step.equals("HasWater")) {
      speech = "Wow that's exactly what I needed! Thannks for the water";
      testQuest.step = "Finished";
      }
    
    else {
      speech = "Thanks for bringing me that water";
      }
 
    gui.showAlert(name, speech);
      
    }
    
  }
