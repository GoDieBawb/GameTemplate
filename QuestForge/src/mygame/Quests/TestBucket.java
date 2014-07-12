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
public class TestBucket extends Quest {
    
  public TestBucket(AppStateManager stateManager, Node holder) {
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
      speech = "You shouldn't go messing around with someone's bucket";
      }
    
    else if (testQuest.step.equals("GetWater")) {
      holder.removeFromParent();
      player.inventory.add(holder.getName());
      speech = "You pick up the bucket";
      }
    
    else {
      speech = "Something went wrong here!";
      }
    
    gui.showAlert(name, speech);
      
    }
    
  }

