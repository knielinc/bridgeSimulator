package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
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

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017) // 60hz
                ,(ActionEvent) -> {
            double t = (System.currentTimeMillis() - timeStart) / 1000.0;

            double x = 232 + 128 * Math.cos(t);
            double y = 232 + 128 * Math.sin(t);

            // Clear the canvas
            gc.clearRect(0, 0, 512,512);

            gc.fillOval(x,y,10,10);

            gc.setFill( Color.RED );
            gc.setLineWidth(2);
            Font theFont = Font.font( "Helvetica", FontWeight.LIGHT, 12 );
            gc.setFont( theFont );
            gc.fillText( "x: " + String.valueOf(Math.floor(x)) + " y: " + String.valueOf(Math.floor(y)), x, y );

            // background image clears canvas

        });

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
