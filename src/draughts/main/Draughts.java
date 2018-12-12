package draughts.main;
import javafx.application.Application;
import javafx.stage.Stage;
import draughts.windows.*;

public class Draughts extends Application {

	public static void main(String[] args) {
		launch(args);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {	
		MainWindow window = new MainWindow(stage, this.getParameters().getRaw());
		window.Show();
	}

}