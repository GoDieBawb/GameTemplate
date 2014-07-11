package mygame.Quests;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import mygame.Interactable;
import mygame.Npc;
import mygame.Quest;

/**
 *
 * @author Bob
 */
public class QuestAssigner {
  
  private AppStateManager stateManager;
    
  public QuestAssigner(AppStateManager stateManager) {
    this.stateManager = stateManager;
    }  
    
  public Quest assignQuest(Node holder) {
    
    Quest quest;
    String name;
    
    try {
      name = ((Interactable) holder).model.getUserData("Name");
      }
    
    catch (ClassCastException e) {
      name = ((Npc) holder).model.getUserData("Name");  
      }
    
    if (name.equals("Tester")) {
      quest = new TestQuest(stateManager, holder);
      }
    
    else if (name.equals("TestWell")) {
      quest = new TestWell(stateManager, holder);  
      ((Interactable) holder).contactMessage = "Looks like an old well";
      }

    else if (name.equals("TestBucket")) {
      quest = new TestBucket(stateManager, holder);
      ((Interactable) holder).contactMessage = "Looks like an empty bucket";
      }
    
    else {
      return null;
      }
    
    return quest;
    }  
    
  }
