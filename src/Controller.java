
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Controller{

    Model model;

    @FXML
    private Group hair;

    @FXML
    private Group clothes;

    @FXML
    private Rectangle background;

    @FXML
    private ImageView skin;

    @FXML
    private ImageView eyebrows;

    @FXML
    private ImageView eyes;

    @FXML
    private ImageView nose;

    @FXML
    private ImageView mouth;

    @FXML
    private Pane commandBar;

    @FXML
    private Pane avatarView;

    @FXML
    private Accordion accordion;

    @FXML
    private HBox HBox;

    @FXML
    private Button save;

    private SVGPath svgHair;

    private ArrayList<SVGPath> clothesList;

    private ImageView selectedElement = null;

    private SVGPath selectedElement2 = null;

    private Rectangle selectedElement3 = null;

    public void initialize() {
        // TODO

        // dymamic resize
        skin.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        skin.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        background.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        background.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        eyebrows.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        eyebrows.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        eyes.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        eyes.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        nose.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        nose.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        mouth.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        mouth.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        hair.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        hair.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        clothes.layoutXProperty().bind((avatarView.prefWidthProperty().divide(2).subtract(100)));
        clothes.layoutYProperty().bind((avatarView.prefHeightProperty().divide(2).subtract(100)));
        save.layoutXProperty().bind(commandBar.prefWidthProperty().multiply(8).divide(50));
        save.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(5).divide(8));


        clothesList = new ArrayList<>();
        model = new Model(this);
        hair.getChildren().add(new SVGLoader().loadSVG("resources/hair/hair_curly.svg"));
        clothes.getChildren().add(new SVGLoader().loadSVG("resources/clothes.svg"));

        for (Node child : ((Group)(hair.getChildren().get(0))).getChildren()){
            if (child instanceof SVGPath) {
                svgHair = (SVGPath) child;
            }
        }

        for (Node child : ((Group)(clothes.getChildren().get(0))).getChildren()){
            if (child instanceof SVGPath) {
                SVGPath c = (SVGPath) child;
                clothesList.add(c);
                c.setOnMouseClicked(evt -> {
                    handleClothesColor(evt, c);
                });
            }
        }

        svgHair.setOnMouseClicked(evt -> {
            handleHairSelected(evt);
        });

        // default
        skin.setImage(new Image("resources/skin/skin_light.png"));
        eyebrows.setImage(new Image("resources/brows/brows_default.png"));
        eyes.setImage(new Image("resources/eyes/eyes_default.png"));
        mouth.setImage(new Image("resources/mouth/mouth_default.png"));
    }

    public void addSaveButton(Stage stage){

        save.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(stage);
            Image img = avatarView.snapshot(new SnapshotParameters(), null);

            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(img,
                            null), "png", file);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

    }

    public void handleResizeEvent(Stage stage){
        accordion.prefHeightProperty().bind(stage.heightProperty());
        avatarView.prefHeightProperty().bind(stage.heightProperty());
        commandBar.prefHeightProperty().bind(stage.heightProperty());
        accordion.prefWidthProperty().bind(stage.widthProperty().multiply(4).divide(16));
        avatarView.prefWidthProperty().bind(stage.widthProperty().multiply(7).divide(16));
        commandBar.prefWidthProperty().bind(stage.widthProperty().multiply(5).divide(16));
    }

    public void addModel(Model model){
        this.model = model;
    }

    public Model getModel(){
        return model;
    }

    // handle skin change
    @FXML
    public void handleBrownSkin(Event event) {
        model.updateSkin("resources/skin/skin_brown.png");
    }

    @FXML
    public void handleLightSkin(Event event){
        model.updateSkin("resources/skin/skin_light.png");
    }

    @FXML
    public void handleLighterSkin(Event event){
        model.updateSkin("resources/skin/skin_lighter.png");
    }

    public void updateSkinView(String newSkin){
        skin.setImage(new Image(newSkin));
    }

    // handle eyebrows change
    @FXML
    public void handleAngryBrows(Event event) {
        model.updateEyebrows("resources/brows/brows_angry.png");
    }

    @FXML
    public void handleDefaultBrows(Event event){
        model.updateEyebrows("resources/brows/brows_default.png");
    }

    @FXML
    public void handleSadBrows(Event event){
        model.updateEyebrows("resources/brows/brows_sad.png");
    }

    public void updateBrowsView(String newBrows){
        eyebrows.setImage(new Image(newBrows));
    }

    // handle eyes change
    @FXML
    public void handleClosedEyes(Event event) {
        model.updateEyes("resources/eyes/eyes_closed.png");
    }

    @FXML
    public void handleDefaultEyes(Event event){
        model.updateEyes("resources/eyes/eyes_default.png");
    }

    @FXML
    public void handleWideEyes(Event event){
        model.updateEyes("resources/eyes/eyes_wide.png");
    }

    public void updateEyesView(String newEyes){
        eyes.setImage(new Image(newEyes));
    }

    // handle mouth change
    @FXML
    public void handleDefaultMouth(Event event) {
        model.updateMouth("resources/mouth/mouth_default.png");
    }

    @FXML
    public void handleSadMouth(Event event){
        model.updateMouth("resources/mouth/mouth_sad.png");
    }

    @FXML
    public void handleSeriousMouth(Event event){
        model.updateMouth("resources/mouth/mouth_serious.png");
    }

    public void updateMouthView(String newMouth){
        mouth.setImage(new Image(newMouth));
    }

    // handle hair change
    @FXML
    public void handleCurlyHair(Event event) {
        model.updateHair("resources/hair/hair_curly.svg");
    }

    @FXML
    public void handleLongHair(Event event){
        model.updateHair("resources/hair/hair_long.svg");
    }

    @FXML
    public void handleShortHair(Event event){
        model.updateHair("resources/hair/hair_short.svg");
    }

    @FXML
    public void handleWavyHair(Event event){
        model.updateHair("resources/hair/hair_wavy.svg");
    }

    public void updateHairView(String newHair){
        hair.getChildren().removeAll(hair.getChildren());
        hair.getChildren().add(new SVGLoader().loadSVG(newHair));
        for (Node child : ((Group)(hair.getChildren().get(0))).getChildren()){
            if (child instanceof SVGPath) {
                svgHair = (SVGPath) child;
            }
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        svgHair.setOnMouseClicked(evt -> {
            handleHairSelected(evt);
        });
    }


    // handle isSelected
    @FXML
    public void handleBubblingPhaseClick(Event event){
        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
            selectedElement = null;
        }

        if(selectedElement2!= null){
            selectedElement2.setEffect(null);
            selectedElement2 = null;
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }
    }

    // eyebrows
    @FXML
    public void handleEyebrowsSelected(Event event) {
        model.updateIsEyebrowsSelected(true);
        event.consume();
    }

    public void updateEyebrowsShadow(){

        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
        }

        if(selectedElement2!=null){
            // deselect the previous one
            selectedElement2.setEffect(null);
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        DropShadow ds = new DropShadow(5, Color.NAVY);
        eyebrows.setEffect(ds);
        selectedElement = eyebrows;

        // offset
        Spinner<Integer> offset = new Spinner<>(-8, 8, model.getOffset());

        GridPane g = new GridPane();
        g.add(new Label("Offset:"), 0, 0);
        offset.setEditable(true);
        g.add(offset, 1, 0);
        g.setHgap(20);
        g.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(3).divide(8));

        offset.valueProperty().addListener((obs, oldValue, newValue) ->{
            model.setOffset(newValue);
        });

        commandBar.getChildren().add(g);
    }

    public void updateEyebrowsOffset(int offset){
        eyebrows.setLayoutY(300+offset);
    }

    // eyes
    @FXML
    public void handleEyesSelected(Event event){
        model.updateIsEyesSelected(true);
        event.consume();
    }

    public void updateEyesShadow(){
        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
        }

        if(selectedElement2!=null){
            // deselect the previous one
            selectedElement2.setEffect(null);
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        DropShadow ds = new DropShadow(5, Color.NAVY);
        eyes.setEffect(ds);
        selectedElement = eyes;

        // scale
        Spinner<Double> scale = new Spinner<>(0.5, 1.5, model.getScale(), 0.1);

        GridPane g = new GridPane();
        g.add(new Label("Scale:"), 0, 0);
        scale.setEditable(true);
        g.add(scale, 1, 0);
        g.setHgap(20);
        g.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(3).divide(8));

        scale.valueProperty().addListener((obs, oldValue, newValue) ->{
            model.setScale(newValue);
        });

        commandBar.getChildren().add(g);
    }

    public void updateEyesScale(double scale){
        eyes.setScaleX(scale);
        eyes.setScaleY(scale);
    }

    // hairs
    public void handleHairSelected(Event event){
        model.updateIsHairSelected(true);
        event.consume();
    }

    public void updateHairShadow(){
        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
        }

        if(selectedElement2!=null){
            // deselect the previous one
            selectedElement2.setEffect(null);
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        DropShadow ds = new DropShadow(2, Color.NAVY);
        svgHair.setEffect(ds);
        selectedElement2 = svgHair;

        // add color picker
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) svgHair.getFill());

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                svgHair.setFill(colorPicker.getValue());
            }
        });

        colorPicker.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(3).divide(8));

        commandBar.getChildren().add(colorPicker);
    }

    // clothes
    public void handleClothesColor(Event event, SVGPath c){
        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
        }

        if(selectedElement2!=null){
            // deselect the previous one
            selectedElement2.setEffect(null);
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        DropShadow ds = new DropShadow(5, Color.NAVY);
        c.setEffect(ds);
        selectedElement2 = c;

        // add color picker
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) c.getFill());

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                c.setFill(colorPicker.getValue());
            }
        });

        colorPicker.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(3).divide(8));

        commandBar.getChildren().add(colorPicker);

        event.consume();
    }

    //background
    public void handleBackgroundSelected(Event event){
        if(selectedElement!=null){
            // deselect the previous one
            selectedElement.setEffect(null);
        }

        if(selectedElement2!=null){
            // deselect the previous one
            selectedElement2.setEffect(null);
        }

        if(selectedElement3!= null){
            selectedElement3.setEffect(null);
            selectedElement3 = null;
        }

        if(commandBar.getChildren().size() > 1) {
            commandBar.getChildren().remove(commandBar.getChildren().size() - 1);
        }

        DropShadow ds = new DropShadow(5, Color.NAVY);
        background.setEffect(ds);
        selectedElement3 = background;

        // add color picker
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) background.getFill());

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                background.setFill(colorPicker.getValue());
            }
        });

        colorPicker.layoutYProperty().bind(commandBar.prefHeightProperty().multiply(3).divide(8));

        commandBar.getChildren().add(colorPicker);

        event.consume();
    }

}