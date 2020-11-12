import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Model {
    Controller controller;

    // isSelected
    private boolean isJacketSelected = false;
    private boolean isTShirtSelected = false;
    private boolean isTShirtNeckSelected = false;
    private boolean isLeftLapelSelected = false;
    private boolean isRightLapelSelected = false;

    private boolean isHairSelected = false;
    private boolean isEyebrowsSelected = false;
    private boolean isEyesSelected = false;

    // Color property
    private Color jacketColor;
    private Color tShirtColor;
    private Color tShirtNectColor;
    private Color leftLapelColor;
    private Color rightLapelColor;
    private Color hairColor;

    private int eyeBrowsOffset = 0;
    private double eyeScale = 1;

    // storing the path
    public String currentHair;
    public String currentEyes;
    public String currentEyebrows;
    public String currentMouth;
    public String currentSkin;

    Model(Controller c){
        controller = c;
        currentHair="resources/hair/hair_curly.svg";
        currentEyes="resources/eyes/eyes_default.png";
        currentEyebrows="resources/brows/brows_default.png";
        currentMouth="resources/mouth/mouth_default.png";
        currentSkin="resources/skin/skin_light.png";
    }

    // add controller
    public void addController(Controller controller) {
        this.controller = controller;
        this.controller.addModel(this);
    }

    // update hair/eyebrows/eyes/mouth/skin
    public void updateHair(String newHair){
        currentHair = newHair;
        controller.updateHairView(newHair);
    }

    public void updateEyebrows(String newEyebrows){
        currentEyebrows = newEyebrows;
        controller.updateBrowsView(currentEyebrows);
    }

    public void updateEyes(String newEyes){
        currentEyes = newEyes;
        controller.updateEyesView(currentEyes);
    }

    public void updateMouth(String newMouth){
        currentMouth = newMouth;
        controller.updateMouthView(currentMouth);
    }

    public void updateSkin(String newSkin){
        currentSkin = newSkin;
        controller.updateSkinView(currentSkin);
    }

    // update color
    public void updateJacketColor(Color color){
        jacketColor = color;
    }

    public void updateTShirtColor(Color color){
        tShirtColor = color;
    }

    public void updateTShirtNeckColor(Color color){
        tShirtNectColor = color;
    }

    public void updateLeftLapelColor(Color color){
        leftLapelColor = color;
    }

    public void updateRightLapelColor(Color color){
        rightLapelColor = color;
    }

    public void updateHairColor(Color color){
        hairColor = color;
    }

    // isSelected
    public void updateIsJacketSelected(Boolean b){
        isJacketSelected = b;
    }

    public void updateIsTShirtSelected(Boolean b){
        isTShirtSelected = b;
    }

    public void updateIsTShirtNeckSelected(Boolean b){
        isTShirtNeckSelected = b;
    }

    public void updateIsLeftLapelSelected(Boolean b){
        isLeftLapelSelected = b;
    }

    public void updateIsRightLapelSelected(Boolean b){
        isRightLapelSelected = b;
    }

    public void updateIsHairSelected(Boolean b){
        isHairSelected = b;
        controller.updateHairShadow();
    }

    public void updateIsEyebrowsSelected(Boolean b){
        isEyebrowsSelected = b;
        controller.updateEyebrowsShadow();
    }

    public void updateIsEyesSelected(Boolean b){
        isEyesSelected = b;
        controller.updateEyesShadow();
    }

    // increment eyebrows pos and eyes scale
    public void setOffset(int newOffset){
        eyeBrowsOffset = newOffset;
        controller.updateEyebrowsOffset(eyeBrowsOffset);
    }

    public void setScale(double newScale){
        eyeScale = newScale;
        controller.updateEyesScale(eyeScale);
    }

    public int getOffset(){
        return eyeBrowsOffset;
    }

    public double getScale(){
        return eyeScale;
    }

    public void increaseEyeScale(){
        ++eyeScale;
    }

}