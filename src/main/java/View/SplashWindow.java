package View;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;

public class SplashWindow {
    private Pane splashPane;
    private ProgressBar progressBar;
    private Label loadingText;
    private Stage stage;
    private Scene scene;
    private static SplashWindow splashWindow;
    public static MediaPlayer mediaPlayer;

    private SplashWindow(){
        setUp();
        stage = new Stage();
        scene = new Scene(splashPane,602,732);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        music();

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                    Platform.runLater(() -> {
                        MainWindow.getInstance();
                        scene.getWindow().hide();
                    });
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    private void music() {
        //this 8misSong was made by myself ^ ^
        //here's the soundcloud link: https://soundcloud.com/zx-l-480338326/8minssong
        File file1 = new File("src/main/resources/8minsSong.wav");
        Media media = new Media(file1.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static SplashWindow getSplashWindow() {
        if (splashWindow == null) {
            splashWindow = new SplashWindow();
        }
        return splashWindow;
    }

    private void setUp() {
        splashPane = new StackPane();
        File file = new File("src/main/resources/wtKAEvajRNPSUYX.jpeg");
        Image image = new Image(file.toURI().toString());
        ImageView splashImage = new ImageView();
        splashImage.setImage(image);
        splashImage.setCache(true);
        splashImage.setFitHeight(732);
        splashImage.setFitWidth(602);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(500);

        loadingText = new Label("Photo by Manasvita S on Unsplash");

        splashPane.getChildren().addAll(splashImage,progressBar,loadingText);
        splashPane.setPadding(new Insets(10));
        StackPane.setAlignment(loadingText,Pos.TOP_RIGHT);
        StackPane.setAlignment(progressBar,Pos.BOTTOM_CENTER);

    }
}
