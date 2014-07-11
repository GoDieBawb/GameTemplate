/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import java.util.ArrayList;

/**
*
* @author Bob
*/
public class QuestList extends ArrayList {
  
  private Player player;
    
  public QuestList(Player player) {
    this.player = player;
    }
  
  public Quest getQuest(String questName) {
    
    
    for (int i = 0; i < player.questList.size(); i++){
  
      Quest currentQuest = (Quest) player.questList.get(i);
      
      if (currentQuest.name.equals(questName)){
        return currentQuest;
        }
      
      }

    return null;
    
    }
    
  }
