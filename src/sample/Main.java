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

public class Main extends Application {
    GraphicsContext gc;
    boolean gameStarted = false;
    long startTime = System.currentTimeMillis();
    int counterForFps;
    double fps = 0;

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


        Bridge testBridge = new Bridge();
        testBridge.createTestBridge();

        counterForFps = 10;
        final long timeStart = System.currentTimeMillis();
        KeyFrame kf = new KeyFrame(Duration.seconds(0.015) // 60hz
                ,(ActionEvent) -> {
            // Clear the canvas
            gc.clearRect(0, 0, 1600,900);
            //testBridge.draw(gc);
            testBridge.computeTimeStepImplicit(0.5,.2);
            //testBridge.computeTimeStepExplicit(0.5,.01);

            testBridge.draw(gc);

            if(counterForFps == 0){
                fps = ((double)10000.0/ (double)(System.currentTimeMillis() - startTime));
                startTime = System.currentTimeMillis();
                counterForFps = 10;
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

    public void startGame2(){
        gameStarted = true;
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        GameScene myScene = new GameScene();
        KeyFrame kf = new KeyFrame(Duration.seconds(0.017) // 60hz
                ,(ActionEvent) -> {
            double t = (System.currentTimeMillis() - timeStart) / 1000.0;

            // Clear the canvas
            gc.clearRect(0, 0, 1600,900);

            myScene.draw(gc);
            myScene.updateRigidBodies(0.017);
            // background image clears canvas

        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
