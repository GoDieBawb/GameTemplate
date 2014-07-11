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
    
    if (testQuest == null) {
      testQuest = new TestQuest(stateManager, player);
      player.questList.add(testQuest);
      testQuest.step = "Start";
      }
    
    String step = testQuest.step;
    
    if (step.equals("Start")) {
      gui.showAlert(name, "You shouldn't go messing around with someone's well");
      }
    
    else if (step.equals("GetWater")) {
      if (player.inventory.contains("Bucket")) {
        gui.showAlert(holder.getName(), "You fill the bucket with water");
        testQuest.step = "HasWater";
        }
      
      else {
        gui.showAlert(holder.getName(), "You'll probably need a bucket");
        }
        
      }
    
    else if (step.equals("HasWater")) {
      gui.showAlert(name, "You already have water");
      }
    
    else {
      gui.showAlert(name, "You don't need the well for anything now");
      }
      
    }
    
  }
