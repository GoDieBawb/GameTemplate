package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.Quests.QuestAssigner;

/**
 *
 * @author Bob
 */
public class NpcManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Node              npcNode;
  private QuestAssigner     questAssigner;
  private Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    npcNode           = new Node();
    questAssigner     = stateManager.getState(PlayerManager.class).questAssigner;
    player            = stateManager.getState(PlayerManager.class).player;
    }
  
  public void initNpcs(Node scene) {
   
    npcNode = (Node) scene.getChild("NpcNode");
    
    for (int i = 0; i < npcNode.getQuantity(); i++) {
      
      Node currentNpc = (Node) npcNode.getChild(i);
      
      try {
        Npc bla = (Npc) currentNpc;
        }
      
      catch(ClassCastException e) {
        Npc npc   = new Npc(currentNpc);
        npc.quest = questAssigner.assignQuest(npc);
        stateManager.getState(PlayerManager.class).physics.getPhysicsSpace().add(npc.phys);
        npcNode.attachChild(npc);
        }
        
      }
    
    npcNodeCleaner();
      
    }
  
  private void npcNodeCleaner() {
      
    for (int i = 0; i < npcNode.getQuantity(); i++) {
      Node currentNpc = (Node) npcNode.getChild(i);  
      
      try {
        Npc testNpc = (Npc) currentNpc;
        testNpc.attachChild(testNpc.model);
        }
      
      catch (ClassCastException e) {
        currentNpc.removeFromParent();  
        }
      
      }
    
    for (int i = 0; i < npcNode.getQuantity(); i++) {
      Npc bla = (Npc) npcNode.getChild(i);
      bla.attachChild(bla.model);
      }    
      
    }
  
  @Override
  public void update(float tpf) {
      
    for (int i = 0; i < npcNode.getQuantity(); i++) {
      
      Npc   currentNpc   = (Npc) npcNode.getChild(i);
      float distance     = currentNpc.model.getWorldTranslation().distance(player.model.getWorldTranslation());
      Vector3f playerDir = player.model.getWorldTranslation().subtract(currentNpc.model.getWorldTranslation());
      
      if (distance < 5) {
       
        currentNpc.phys.setViewDirection(playerDir);  
          
        if (System.currentTimeMillis() / 1000 - currentNpc.lastSpoke > 5) {
          currentNpc.quest.act();
          currentNpc.lastSpoke = System.currentTimeMillis() / 1000;
          }
        
        }
        
      }
      
    }

  }
