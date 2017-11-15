package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    GraphicsContext gc;
    boolean gameStarted = false;
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

        final long timeStart = System.currentTimeMillis();

        Bridge testBridge = new Bridge();
        testBridge.createTestBridge();

        KeyFrame kf = new KeyFrame(Duration.seconds(0.003) // 60hz
                ,(ActionEvent) -> {
            double t = (System.currentTimeMillis() - timeStart) / 1000.0;

            double x = 232 + 128 * Math.cos(t);
            double y = 232 + 128 * Math.sin(t);

            // Clear the canvas
            gc.clearRect(0, 0, 1600,900);

            testBridge.draw(gc);
            testBridge.computeTimeStep(0.9,0.01);

            // background image clears canvas

        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
