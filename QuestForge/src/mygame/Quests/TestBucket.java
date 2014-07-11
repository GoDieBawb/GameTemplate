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
    
    if (testQuest == null) {
      testQuest = new TestQuest(stateManager, player);
      player.questList.add(testQuest);
      testQuest.step = "Start";
      }
    
    String step = testQuest.step;
    
    if (step.equals("Start")) {
      gui.showAlert(holder.getName(), "You shouldn't go messing around with someone's bucket");
      }
    
    else if (step.equals("GetWater")) {
      holder.removeFromParent();
      player.inventory.add(holder.getName());
      gui.showAlert(holder.getName(), "You pick up the bucket");
      }
      
    }
    
  }

