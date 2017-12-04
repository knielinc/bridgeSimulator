package sample;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
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

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    public static int DELAY = 1000/60;

    GraphicsContext gc;
    boolean gameStarted = false;
    long startTime = System.currentTimeMillis();
    int counterForFps;
    double fps = 0;
    final int UPDATE_FPS_COUNTER_STEPS = 100;

    @Override
    public void start(Stage theStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bridges");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();*/

        theStage.setTitle( "Bridges" );

        Group root = new Group();
        Scene theScene = new Scene( root );

        theScene.setOnKeyPressed((KeyEvent keyEvent) ->
                {
                        System.out.println("Es wurde folgende Taste gedrückt:\t" + keyEvent.getCode());
                        if(keyEvent.getCode().isWhitespaceKey() && !gameStarted){
                            startGame();
                        }
                }
        );

        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 800, 450 );
        root.getChildren().add( canvas );

        gc = canvas.getGraphicsContext2D();

        theStage.show();
    }

    public void startGame(){
        gameStarted = true;
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        GameScene myGame = new GameScene();

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
            gc.clearRect(0, 0, 1600,900);
            //testBridge.draw(gc);
            myGame.update(0.1);
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




    public static void main(String[] args) {
        launch(args);
    }
}
