package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URLEncoder;
import java.net.URL;
import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.time.LocalTime;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;

/**
 * Represents an iTunes GalleryApp.
 */
public class GalleryApp extends Application {

    Scene scene;

    MenuBar menuBar;
    ToolBar toolbar;
    ProgressBar progressbar;

    Button pauseplay;
    Text searchQuery;
    TextField field;
    Button update;

    Timeline timeline;
    KeyFrame keyFrame;

    Alert alert;
    TilePane tile;
    ImageView[][] array;

    List<String> completeList;
    List<String> distinctList;
    List<Image> imageList;
    List<Image> storedList;

    VBox vbox;

    /**
     * This method consructs the elements in the GUI, as well as internal elements.
     *
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {
        // sets up the menu bar
        final Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        // an EventHandler which exits the application
        exitMenuItem.setOnAction(e -> {
            Thread exit = new Thread(() -> Platform.exit());
            exit.setDaemon(true);
            exit.start();
        });
        // sets up the toolbar
        toolbar = new ToolBar();
        pauseplay = new Button("Pause");
        searchQuery = new Text("Search Query:");
        field = new TextField("rock");
        update = new Button("Update Images");
        toolbar.getItems().addAll(pauseplay, new Separator(), searchQuery, field, update);

        completeList = new ArrayList<String>(); // a list to hold all the URLs as Strings
        distinctList = new ArrayList<String>(); // a new list without any dupilate images
        imageList = new ArrayList<Image>(); // a list to hold all the images, with no duplicate URLs
        storedList = new ArrayList<Image>(); // a list to hold the image not displayed in the GUI
        timeline = new Timeline(); // instantiates the Timeline
        keyFrame = new KeyFrame(Duration.seconds(2), randImgHandler(storedList));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        pauseplay.setOnAction(checkStatus(timeline)); // the EventHandler for the pauseplay button

        progressbar = new ProgressBar(0); // sets up the progress bar

        // sets up the TilePane
        tile = new TilePane();
        tile.setPrefColumns(5);
        array = new ImageView[4][5]; // instantiantes the array for internal storage of ImageViews

        // the EventHandler for the "Update Images" button
        update.setOnAction(e -> {
            Thread loadTask = new Thread(() -> loadImages());
            loadTask.setDaemon(true);
            loadTask.start();
        });

        // add the hbox and image objects to the containing vbox
        vbox = new VBox();
        vbox.getChildren().addAll(menuBar, toolbar, tile, progressbar);
        stage.sizeToScene();
        scene = new Scene(vbox, 500, 488); // set the vbox to be the root of the scene
        stage.setResizable(false);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        // load initial images from default search query
        Thread initialTask = new Thread(() -> loadImages());
        initialTask.setDaemon(true);
        initialTask.start();
    } // start

    /**
     * This method loads artwork images gathered via querying the iTunes Search API, displaying
     * a collection of 20.
     */
    private void loadImages() {
        // clears the lists
        completeList.clear();
        distinctList.clear();
        imageList.clear();

        // queries the iTunes Search API
        iTunesQuery();

        // a new list without any dupilate String URLs
        distinctList = completeList.stream()
            .distinct()
            .collect(Collectors.toList());

        // if there are not enough images from the search query, an ERROR alert appears
        if (distinctList.size() < 21) {
            errorAlert();
        } else {

            // resets the ProgressBar
            Platform.runLater(() -> progressbar.setProgress(0));

            // turns the String URLs into Images and stores them in a list
            for (int i = 0; i < distinctList.size(); i++) {
                imageList.add(new Image(
                                  distinctList.get(i),
                                  100, 100,
                                  false, false));

                // updates ProgressBar
                double step = i;
                Platform.runLater(() -> progressbar.setProgress(step / distinctList.size()));
            } // for

            // resets the TilePane
            Platform.runLater(() -> tile.getChildren().clear());

            // load the ImageView objects onto the TilePane and fill the 2D array
            for (int i = 0; i < 20; i++) {
                // calculates the coordiates for the object in the 2D array
                int r = i / 5;
                int c = i % 5;

                // creates an ImageView object and adds it to the GUI
                Image img = imageList.get(i);
                Platform.runLater(() -> {
                    ImageView imgView = new ImageView(img);
                    array[r][c] = imgView; // stores the ImageView in the 2D array
                    tile.getChildren().add(imgView); // adds the ImageView to the TilePane
                });
            }

            storedList.clear();
            for (int i = 20; i < imageList.size(); i++) {
                storedList.add(imageList.get(i));
            } // for
            timeline.play();

        } // if-else
    } // loadImages

    /**
     * This method holds an {@code EventHandler} which checks the status of the
     * {@code Button} "Play/Pause" and changes the state of the {@code Timeline} accordingly.
     *
     * @param tl The {@code Timeline} whose status will be changed.
     * @return The {@code EventHandler} for the "Play/Pause" {@code Button}.
     */
    private EventHandler<ActionEvent> checkStatus(Timeline tl) {

        EventHandler<ActionEvent> buttonStatus = event -> {
            if (pauseplay.getText().equals("Pause")) {
                tl.pause();
                pauseplay.setText("Play");
            } else {
                tl.play();
                pauseplay.setText("Pause");
            }
        };
        return buttonStatus;

    } // checkStatus

    /**
     * This method contains an {@code EventHandler} which chooses a random location
     * on the {@code TilePane} and replaces the current image with a new image
     * from the search query, which is not already currently being displayed.
     *
     * @param stored The list of {@code Images} not currently being displayed in the GUI.
     * @return An {@code EventHandler} for the randomly displaying a new, unique image..
     */
    private EventHandler<ActionEvent> randImgHandler(List<Image> stored) {

        EventHandler<ActionEvent> handler = event -> {
            Random rand = new Random();

            // gets a random image from the GUI
            int randR = rand.nextInt(4);
            int randC = rand.nextInt(5);
            Image displayedImage = array[randR][randC].getImage();

            // gets a random image from the stored images list
            int indexStor = rand.nextInt(stored.size());
            Image storedImg = stored.get(indexStor);

            array[randR][randC].setImage(storedImg); // updates the scene with the new image
            stored.set(indexStor, displayedImage); // stores the image previously on display

        };
        return handler;

    } // randImgHandler

    /**
     * This method instantiates an {@code Alert} object, which presents an ERROR message.
     */
    private void errorAlert() {

        Platform.runLater(() -> {
            // sets up the error alert
            alert = new Alert(
                Alert.AlertType.ERROR,
                "There are not enough images related to" + "\n"
                + "your search to appropriately fill the scene." + "\n"
                + "Please click \"OK\" to continue.",
                ButtonType.OK);
            alert.setResizable(true);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        });

    } // errorAlert

    /**
     * This method properly queries the iTunes Search API by URL-encoding the search
     * term, downloading the JSON result for a query, and parsing the the JSON result for
     * a query. All the queried artwork image URLs are stored as {@code Strings} in a list.
     */
    private void iTunesQuery() {

        try {

            // encodes the URL query String and creates a URL String
            String encoded =  URLEncoder.encode(field.getText(), "UTF-8");
            String sURL = "https://itunes.apple.com/search?term=" + encoded + "&media=music";

            URL url = new URL(sURL); // creates a URL with the provided String
            InputStreamReader reader = new InputStreamReader(url.openStream());

            JsonElement je = JsonParser.parseReader(reader); // parses the JSON response
            JsonObject root = je.getAsJsonObject(); // root of response
            JsonArray results = root.getAsJsonArray("results"); // "results" array
            int numResults = results.size(); // "results" array size

            // retrieves the artworkUrl100 attribute and adds it to the list
            for (int i = 0; i < numResults; i++) {
                JsonObject result = results.get(i).getAsJsonObject();
                JsonElement artworkUrl100 = result.get("artworkUrl100");
                completeList.add(artworkUrl100.getAsString());
            } // for

        } catch (UnsupportedEncodingException uee) {
            System.err.println("The named encoding is not supported.");
        } catch (MalformedURLException mue) {
            System.err.println("The URL could not be created. No protocol was specified, or " +
                               "an unkown protocol was found, or the String to parse was null.");
        } catch (IOException ioe) {
            System.err.println("An I/O exception has occurred. The input stream could not " +
                               "be returned.");
        } // try-catch

    } // iTunesQuery

} // GalleryApp
