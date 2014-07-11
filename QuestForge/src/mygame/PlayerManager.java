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
import com.jme3.bullet.BulletAppState;
import mygame.Quests.QuestAssigner;

/**
 *
 * @author Bob
 */
public class PlayerManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  public  BulletAppState    physics;
  public  Player            player;
  public  QuestAssigner     questAssigner;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    physics           = new BulletAppState();
    player            = new Player(stateManager);
    questAssigner     = new QuestAssigner(stateManager);
    }
  
  @Override
  public void update(float tpf) {
    
    if (player.hasSwung) {
      if (System.currentTimeMillis() / 1000 - player.lastSwing > 1)
      player.hasSwung = false;    
      }
      
    }
  
  }
