package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.font.BitmapFont;
import com.jme3.font.LineWrapMode;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.android.Joystick;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.core.Screen;
import tonegod.gui.effects.Effect;

/**
*
* @author Bob
*/
public class GuiManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager stateManager;
  private ArrayList<ButtonAdapter> buttonList;
  private AssetManager  assetManager;
  public  AlertBox      alert;
  private Screen        screen;
  private Joystick      stick;
  private Player        player;
  private ButtonAdapter interactButton;
  private String        delayedMessage;
  private String        delayedTitle;
  private int           alertDelay;
  private long          delayStart;
  private boolean       hasAlert;
  private TextElement   title;
  private BitmapFont    font;
  private boolean       isAndroid;
  public  String        alertTitle;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    screen            = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    font              = assetManager.loadFont("Interface/Impact.fnt");
    buttonList        = new ArrayList();
    alertTitle        = "";
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    this.app.getInputManager().setSimulateMouse(true);
    initInteractButton();
    initAlertBox();
    isAndroid  = "Dalvik".equals(System.getProperty("java.vm.name"));
    if (isAndroid)
    initJoyStick();
    else
    player.speedMult = 1;
    
    initTitle();
    initMenu();
    }

  private void initTitle() {     
      
    title = new TextElement(screen, "Title", Vector2f.ZERO, new Vector2f(300,50), font) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    title.setIsResizable(false);
    title.setIsMovable(false);
    title.setTextWrap(LineWrapMode.NoWrap);
    title.setTextVAlign(BitmapFont.VAlign.Center);
    title.setTextAlign(BitmapFont.Align.Center);
    title.setFontSize(font.getPreferredSize());
    title.setFontColor(ColorRGBA.Orange);
    
    Effect slideIn1 = new Effect(Effect.EffectType.SlideIn, Effect.EffectEvent.Show, 1f);
    Effect slideOut1 = new Effect(Effect.EffectType.SlideOut, Effect.EffectEvent.Hide, 1f);
    
    title.addEffect(slideIn1);
    title.addEffect(slideOut1);
    
    title.setFontSize(screen.getHeight()/7);
    title.setText("Game Template");
    
    screen.addElement(title);
    title.setPosition(screen.getWidth()/2 - title.getWidth()/2, screen.getHeight() - title.getHeight());
    
    
    }

  private void initMenu() {
    
    for (int i = 0; i < 9; i++) {
        
      final int buttonNum  = i + 1;
      
      ButtonAdapter currentButton = new ButtonAdapter(screen, "Button" + i, new Vector2f(12,12)) {
        @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {

          String missionNum   = String.valueOf(buttonNum);
          String path         = "Scenes/Mission" + missionNum + ".j3o";
          hideMenu();
          stateManager.getState(SceneManager.class).initScene(path);
          }
      
        };
      
      currentButton.setFont("Interface/Impact.fnt");
      currentButton.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
      currentButton.setText("" + buttonNum);
      screen.addElement(currentButton);
      buttonList.add(currentButton);
      currentButton.hide();        
      }
 
    //Centered around button 4
    buttonList.get(4).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(0).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(1).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(2).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 - buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(3).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(5).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(6).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(7).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(8).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    showMenu();
    
    }
  
  private void initInteractButton(){
    interactButton = new ButtonAdapter( screen, "InteractButton", new Vector2f(15, 15) ) {
    
    @Override
      public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        player.swing(this.app.getStateManager());
        }
      };
    
    interactButton.setDimensions(screen.getWidth()/8, screen.getHeight()/10);
    interactButton.setPosition(screen.getWidth() / 1.1f - interactButton.getHeight(), screen.getHeight() / 1.1f - interactButton.getHeight());
    interactButton.setFont("Interface/Impact.fnt");
    interactButton.setText("Check");
    screen.addElement(interactButton);
    interactButton.hide();
    }
  
  private void initAlertBox(){
    alert = new AlertBox(screen, Vector2f.ZERO) {
    @Override
    public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
      hideWithEffect();
      }
    };
    
    alert.setWindowTitle("Welcome");
    alert.setMsg("Welcome to Townyville.");
    alert.setButtonOkText("Ok");
    alert.setLockToParentBounds(true);
    alert.setClippingLayer(alert);
    alert.setMinDimensions(new Vector2f(150,180));
    //alert.setDimensions(new Vector2f(150,180));
    alert.setIsResizable(true);
    screen.addElement(alert);
    alert.hide();
    }
  
  public void showAlert(String speaker, String text){
    alertTitle = speaker;
    alert.showWithEffect();
    alert.setWindowTitle(speaker);
    alert.setMsg(text);
    }
  
  public void delayAlert(String speaker, String text, int delay){
    hasAlert = true;
    delayStart = System.currentTimeMillis() / 1000;
    alertDelay = delay;
    delayedTitle = speaker;
    delayedMessage = text;
    }

  private void initJoyStick(){
    stick = new Joystick(screen, Vector2f.ZERO, (int)(screen.getWidth()/6)) {
    
    @Override
    public void onUpdate(float tpf, float deltaX, float deltaY) {
        float dzVal = .2f; // Dead zone threshold
            if (deltaX < -dzVal) {
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).left = true;
                stateManager.getState(InteractionManager.class).right = false;
            } else if (deltaX > dzVal) {
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = true;
            } else if (deltaY < -dzVal) {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).down = true;
                stateManager.getState(InteractionManager.class).up = false;
            } else if (deltaY > dzVal) {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).up = true;
            } else {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
            }
            
          player.speedMult = FastMath.abs(deltaY);
          }
        };
      // getGUIRegion returns region info “x=0|y=0|w=50|h=50″, etc
      TextureKey key = new TextureKey("Textures/barrel/D.png", false);
      Texture tex = assetManager.loadTexture(key);
      stick.setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      stick.getThumb().setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      screen.addElement(stick, true);
      stick.setPosition(screen.getWidth()/10 - stick.getWidth()/2, screen.getHeight() / 10f - interactButton.getHeight()/5);
      // Add some fancy effects
      Effect fxIn = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show,.5f);
      stick.addEffect(fxIn);
      Effect fxOut = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide,.5f);
      stick.addEffect(fxOut);
      stick.show();
      }
  
  private void hideMenu() {
    
    for (int i = 0; i < buttonList.size(); i++) {
      if (i ==9) break;
      buttonList.get(i).hide();
      }
    
    title.hideWithEffect();
    interactButton.show();
    
    if (isAndroid)
    stick.show();
    
    }
  
  public void showMenu() {
    
    for (int i = 0; i < 8 + 1; i++) {
      if (i ==9) break;  
      buttonList.get(i).show();
      }
    
    title.show();
    interactButton.hide();
    
    if (isAndroid)
    stick.hide();
      
    }
  
  @Override
  public void update(float tpf){
    
    if (hasAlert)
    if (System.currentTimeMillis()/1000 - delayStart > alertDelay){
      showAlert(delayedTitle, delayedMessage);
      hasAlert = false;
      }
    }
  
  }