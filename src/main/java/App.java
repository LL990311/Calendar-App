import Controller.Controller;
import View.SplashWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args){
//        if (args.length != 2) {
//            System.out.println("Please type correctly!\n" +
//                    "Aka 'gradle run --args=\"online offline\"");
//            System.exit(-1);
//        }else {
//            if (args[0].equals("online")) {
//                Controller.setOnlineMode();
//                Controller.setupSqlController();
//                Controller.createDB();
//                Controller.setupDB();
//            }else {
//                Controller.setOfflineMode();
//            }
//
//            if (args[1].equals("online")) {
//                Controller.setOnlinePaste();
//            }else {
//                Controller.setOfflinePaste();
//            }
//            launch(args);
//        }
        Controller.setOnlineMode();
        Controller.setOnlinePaste();
        Controller.setupSqlController();
        Controller.createDB();
        Controller.setupDB();
        launch(args);

    }

    @Override
    public void start(Stage primaryStage){
        SplashWindow.getSplashWindow();
    }
}
