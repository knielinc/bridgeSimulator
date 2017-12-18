package sample;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.MatrixType;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import sample.bridge.Bridge;
import sample.bridge.BridgeSupportAnchorPoint;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    public static int DELAY = 1000/60;
    public static boolean DEBUG_MODE_ENABLED = false;
    private static Image background  = new Image("file:res/Background_Grass.png");
    public static void setBackground(int i){
        switch(i) {
            case 0:
                background = new Image("file:res/Background_Grass.png");
                break;
            case 1:
                background = new Image("file:res/Background_Space.png");
                break;
            case 2:
                background = new Image("file:res/Background_City.png");
                break;
            default:
                break;
        }
    }
    GraphicsContext gc;
    boolean gameStarted = false;
    long startTime = System.currentTimeMillis();
    int counterForFps;
    double fps = 0;
    final int UPDATE_FPS_COUNTER_STEPS = 100;
    private GameScene myGame;
    private Timeline gameLoop;

    @Override
    public void start(Stage theStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bridges");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();*/

        theStage.setTitle( "Bridges" );

        Group root = new Group();
        Scene theScene = new Scene( root );

        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 800, 450 );
        root.getChildren().add( canvas );

        gc = canvas.getGraphicsContext2D();

        showStartScreen();

        theStage.show();

        theScene.setOnKeyPressed((KeyEvent keyEvent) ->
                {
                        System.out.println("Es wurde folgende Taste gedrückt:\t" + keyEvent.getCode().getName());
                        if(keyEvent.getCode().isWhitespaceKey() && !gameStarted) {
                            //startGame(0);
                        } else if (keyEvent.getCode().toString().equals("ESCAPE")){
                            stopGame();
                        } else if(keyEvent.getCode().isDigitKey() && !gameStarted){
                            startGame(Integer.parseInt(keyEvent.getCode().getName()));
                        } else if (keyEvent.getCode().toString().equals("D")) {
                            if (DEBUG_MODE_ENABLED){
                                DEBUG_MODE_ENABLED = false;
                            } else {
                                DEBUG_MODE_ENABLED = true;
                            }
                        }
                }
        );


    }

    public void startGame(int i){

        gameStarted = true;
        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        myGame = new GameScene(i);

        counterForFps = UPDATE_FPS_COUNTER_STEPS;
        final long timeStart = System.currentTimeMillis();
        /*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // Clear the canvas
                //testBridge.draw(gc);
                myGame.update(0.1);
                //testBridge.computeTimeStepExplicit(0.5,.001);
                gc.clearRect(0, 0, 1600,900);
                myGame.draw(gc);

                if(counterForFps == 0){
                    fps = ((double) UPDATE_FPS_COUNTER_STEPS * 1000.0/ (double)(System.currentTimeMillis() - startTime));
                    startTime = System.currentTimeMillis();
                    counterForFps = UPDATE_FPS_COUNTER_STEPS;
                } else {
                    counterForFps--;
                }
                gc.setFill(Color.BLACK);
                gc.fillText("Framerate: " + String.valueOf(Math.floor((fps* 100))/100.0),0,20);
                // background image clears canvas
            }
        }, 0, DELAY); */

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017) // 60hz
                ,(ActionEvent) -> {
            // Clear the canvas
            gc.clearRect(0, 0, 800,450);
            gc.drawImage(background,0,0,800,450);

            //testBridge.draw(gc);
            myGame.update(0.03);
            //testBridge.computeTimeStepExplicit(0.5,.001);

            myGame.draw(gc);

            if(counterForFps == 0){
                fps = ((double) UPDATE_FPS_COUNTER_STEPS * 1000.0/ (double)(System.currentTimeMillis() - startTime));
                startTime = System.currentTimeMillis();
                counterForFps = UPDATE_FPS_COUNTER_STEPS;
            } else {
                counterForFps--;
            }
            gc.setFill(Color.BLACK);
            gc.fillText("Framerate: " + String.valueOf(Math.floor((fps* 100))/100.0),0,20);
            // background image clears canvas

        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }

    public void stopGame(){
        gameLoop.stop();
        BridgeSupportAnchorPoint.INDEX = 0;
        gameLoop.getKeyFrames().removeAll();
        showStartScreen();
        //gc.clearRect(0,0,1600,900);
        gameStarted = false;
    }

    private void showStartScreen() {
        gc.clearRect(0,0,800,450);
        setBackground(0);
        gc.drawImage(background,0,0,800,450);

        gc.fillText("Choose a Scene to be played, by pressing a number 0-9\n\n" +
                "1 : 2 rigidbodies colliding with no gravity\n" +
                "2 : simple bridge without breaking\n" +
                "3 : simple bridge with breaking\n" +
                "4 : more sophisticated bridge with a car with breaking enabled\n" +
                "5 : more sophisticated bridge with a truck with breaking enabled\n" +
                "6 : a car and a truck interacting with a seesaw and a freely swinging bridge-element\n" +
                "7 : TODO\n" +
                "8 : TODO\n" +
                "9 : TODO\n\n" +
                "press Esc to go back to this selection screen\n\n" +
                "press d to toggle debug-mode",50,20);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
