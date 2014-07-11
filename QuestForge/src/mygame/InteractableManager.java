/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import mygame.Quests.QuestAssigner;

/**
 *
 * @author Bob
 */
public class InteractableManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Node              interactableNode;
  private Player            player;
  private QuestAssigner     questAssigner;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    interactableNode  = new Node();
    player            = stateManager.getState(PlayerManager.class).player;
    questAssigner     = stateManager.getState(PlayerManager.class).questAssigner;
    }
  
  public void initInteractables(Node scene) {
     
    interactableNode = (Node) scene.getChild("InteractableNode");
    
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
      
      Node currentInteractable = (Node) interactableNode.getChild(i);  
      
      try {
        Interactable testInteractable = (Interactable) currentInteractable;  
        }
      
      catch (ClassCastException e) {
        
        Interactable interactable = new Interactable(currentInteractable);
        interactable.quest        = questAssigner.assignQuest(interactable);
        interactableNode.attachChild(interactable);
        
        }
      
      }
    
    interactNodeClean();
      
    }
  
  private void interactNodeClean(){
      
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
      Node currentInteractable = (Node) interactableNode.getChild(i);  
      
      try {
        Interactable testInteractable = (Interactable) currentInteractable;
        testInteractable.attachChild(testInteractable.model);
        }
      
      catch (ClassCastException e) {
        currentInteractable.removeFromParent();  
        }
      
      }
    
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
      Interactable bla = (Interactable) interactableNode.getChild(i);
      bla.attachChild(bla.model);
      }
      
    }
  
  
  @Override
  public void update(float tpf) {
      
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
        
      Interactable currentInt = (Interactable) interactableNode.getChild(i);
      float distance = currentInt.model.getWorldTranslation().distance(player.model.getWorldTranslation());
     
      if (distance < 3) {
          
        if (System.currentTimeMillis() / 1000 - currentInt.lastAlert > 5 && !currentInt.quest.gui.alertTitle.equals(currentInt.getName())) {
          currentInt.lastAlert = System.currentTimeMillis() / 1000;
          currentInt.quest.gui.showAlert(currentInt.getName(), currentInt.contactMessage);
          }
        
        if (player.intCheck) {
          currentInt.quest.act();
          }
          
        } 
      
      }
    
    player.intCheck = false;
      
    }
  
  }
