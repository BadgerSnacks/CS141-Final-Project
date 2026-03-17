package com.badgersnacks.flashcards;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

// This is the main class that builds the GUI and most of the application
public class FlashCards extends Application {
    // All the fields declared here that can be used between the different methods.
    private ListView<FlashCard> deckList = new ListView<>(); //an array list used by the GUI to display the contents of a deck
    private Deck currentDeck = new Deck(); // the field that is used to hold the current deck
    private Label deckName = new Label("Deck: " + currentDeck.getDeckName()); // the name of the deck that is currently being used
    private FlashCard currentCard = new FlashCard("No card loaded", "No card loaded"); // a place-holder field for the current card
    private FlashCard previousCard = new FlashCard("No card loaded", "No card loaded"); // a place-holder field for the previous card
    private TextField promptText; // create new field called promptText
    private TextArea descriptionText; // create new text area called descriptionText
    private Label frontLabel = new Label(); // the label that displays the contents of the front of the card
    private Label backLabel = new Label(); // the label that displays the contents of the back of the card
    private int currentIndex = -1; // an index that is used when cycling through the deckList to refrence the card in the current deck
    private Stage Stage = new Stage(); // the stage field that is used to build the staage

    @Override
    public void start(Stage stage){
        // method and entry point for the Window.
        this.Stage = stage;

        MenuBar menuBar = createMenuBar(); // creates a MenuBar variable with the createMenuBar method

        SplitPane splitPane = createSplitPane(); // Creates the split pane window with the createSplitPane method

        BorderPane root = new BorderPane(); // Creates a borderpane that lives inside of stage and holds all the things like windows, menus, and buttons.
        root.setTop(menuBar); // adds menuBar to the BorderPane layout inside the stage
        root.setCenter(splitPane); // adds splitPane to the BorderPane layout inside the stage
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5;"); // Styling for the root window that holds the VBox's

        Scene scene = new Scene(root, 1200, 700); // creates the window and assigns root as its layout, sets the windows size
        stage.setTitle("Flash Cards"); // Title that shows on the window
        stage.setScene(scene); // Bakes our "cake" the scene with all the items up to now
        stage.show(); // Presents our "cake" to the user.

    }

    private SplitPane createSplitPane(){ // Create the resizeable panes for my window using VBox and SplitPane

        // These regions are used as spacers to help with the layout of the window.
        Region leftSpacer = new Region();
        VBox.setVgrow(leftSpacer, Priority.ALWAYS); // expands the spacer to fill gaps
        Region centerSpacer = new Region();
        VBox.setVgrow(centerSpacer, Priority.ALWAYS); // expands the spacer to fill gaps
        Region topCenterSpacer = new Region();
        Region topRightSpacer = new Region();

        Label frontLabelHeader = new Label("Front of current card:"); // creates the label that holds the header and sets the text for the front of the card
        frontLabelHeader.setStyle( // this is the styling for the label using CSS
                "-fx-text-fill: black;" +
                "-fx-font-size: 16px;"
        );

        Label backLabelHeader = new Label("Back of previous card: "); // creates the label that holds the head and sets the text for the back of the card
        backLabelHeader.setStyle( // this is the styling for the label using css
                "-fx-text-fill: black;" +
                "-fx-font-size: 16px;"
        );

        frontLabel.setStyle( // This is the styling for the frontLabel field I.E. the front of our card
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10;" +
                "-fx-background-color: #8C8786;" +
                "-fx-pref-height: 250px;" +
                "-fx-pref-width: 350px;" +
                "-fx-alignment: center;" +
                "-fx-background-radius: 8;"
        );

        backLabel.setStyle( // this is the styling for the backLabel field I.E. the back of our card
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10;" +
                "-fx-background-color: #8C8786;" +
                "-fx-pref-height: 250px;" +
                "-fx-pref-width: 350px;" +
                "-fx-alignment: center;" +
                "-fx-background-radius: 8;" +
                "-fx-wrap-text: true;"
        );

        VBox leftPane = new VBox(deckName, deckList(), leftSpacer, createTextFieldLeft(), createButtonLeft()); // Creates my left pane used in my split pane setup
        leftPane.setStyle("-fx-padding: 5;"); // styling for the left pane

        VBox centerPane = new VBox(frontLabelHeader, topCenterSpacer, frontLabel, centerSpacer, createButtonCenter()); // Creates my center pane used in my split pane setup
        centerPane.setStyle("-fx-padding: 5;"); // styling for the center pane
        centerPane.setAlignment(Pos.CENTER); // more styling that sets the alignment of the pane

        VBox rightPane = new VBox(backLabelHeader, topRightSpacer, backLabel); // Creates my right pane used in my split pane setup
        rightPane.setStyle("-fx-padding: 5;"); // styling for the center pane
        rightPane.setAlignment(Pos.TOP_CENTER); // more styling that set the alignment of the pane

        topCenterSpacer.prefHeightProperty().bind(centerPane.heightProperty().multiply(0.30)); // These two lines need to appear centerPane and rightPane
        topRightSpacer.prefHeightProperty().bind(rightPane.heightProperty().multiply(0.30)); // sets the size of the region to 30% of the height of the pane's

        SplitPane splitPane = new SplitPane(leftPane,centerPane,rightPane); // Builds the 3 VBox's into a single pane with resizeable windows.
        splitPane.setOrientation(Orientation.HORIZONTAL); // more styling to set the orientation in the root window

        splitPane.setDividerPositions(0.25, 0.625); // Sets the default location of the window dividers

        deckListListener(); // calls the deckListListener function on creation to make sure the window is populated with the cards when created.

        return splitPane; // returns the splitPane we created up to the start method
    }

    private MenuBar createMenuBar(){ // Method to create and edit the menu bar

        // creates the MenuItem's and assigns the Text associated with them
        MenuItem newMenuItem = new MenuItem("New Deck");
        MenuItem loadMenuItem = new MenuItem("Load Deck");
        MenuItem saveMenuItem = new MenuItem("Save Deck");

        Menu menu1 = // creates the first parent menu
                new Menu("File", // Option for the MenuBar
                    null, // This is to set the graphics node to null since we are not using graphics
                    newMenuItem, // Set of MenuItems that are nested within the "file" option of the MenuBar
                    loadMenuItem,
                    saveMenuItem
        );

        // this is the block of code that is run when "New Deck" is selected
        newMenuItem.setOnAction(e -> { // this is the on action listener
           TextInputDialog dialog = new TextInputDialog(); // Constructor that creates a TextInputDialog, it's a pop-up window
           dialog.setTitle("New Deck"); // sets the title of the pop-up window
           dialog.setHeaderText("Enter new Deck name"); // set the header text
           currentDeck = new Deck(); // clears the currentDeck and creates a new empty one.

           Optional<String> input = dialog.showAndWait(); // this waits for the user input, uses Optional in case the user closes or enters an empty string

            if (input.isPresent() && !input.get().trim().isEmpty()) { // loop that checks to see if the user entered an input or not
                currentDeck.setDeckName(input.get().trim()); // trims the user input and sets it to the current deck name
                deckList.getItems().clear(); // clears the deckList in the GUI
                deckName.setText("Deck: " + currentDeck.getDeckName()); // updates the currently displayed deck name in the GUI
            }

        });

        // this is the block of code that is run when "load deck" is selected
        loadMenuItem.setOnAction(e -> { // this is the on action listener

            FileChooser fileChooser = new FileChooser(); // creates the file chooser object
            fileChooser.setTitle("Open Deck"); // the title that is displayed on the file choose
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // sets the directory the file chooser opens in

            File file = fileChooser.showOpenDialog(Stage); // creates the actual file choose and ties it to the main Stage window, this is why stage needs to be a class field
            try {
                currentDeck = (DeckFileManager.loadDeck(file)); // calls the DeckFileManager class and the loadDeck method, passing the file in as a parameter
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex); // throws an error if the file is not real
            }
            deckName.setText("Deck: " + currentDeck.getDeckName()); // changes the name of the deck in the GUI
            deckList(); // calls the DeckList method again to rebuild the list based on the cards.

        });

        // this is the block of code that is run whe "save deck" is selected
        saveMenuItem.setOnAction(e -> { // this is the on action listener
            try { // try method used to handle file errors if the file cannot be created
                if (currentDeck.getDeckName() == null || currentDeck.getDeckName().isEmpty()) { // checks if the current deck has a name and runs if it does not.
                    TextInputDialog dialog = new TextInputDialog(); // constructor for new TextInputDialog
                    dialog.setTitle("Save Deck"); // title for text input dialog
                    dialog.setHeaderText("No deck name set"); // header for text input dialog
                    dialog.setContentText("Please enter a deck name"); // content text for text input dialog

                    Optional<String> input = dialog.showAndWait(); // waits for user input and assign it to input variable uses optional if field is empty

                    if (input.isPresent() && !input.get().trim().isEmpty()) { // if user enters an input this code is run
                        currentDeck.setDeckName(input.get().trim()); // trims the input and sets the current decks name to user input
                    } else {
                        return; // returns nothing is user input is empty
                    }
                }

                DeckFileManager.saveDeck(currentDeck.getDeckName(), currentDeck); // calls the DeckFileManager.saveDeck passing in the current name we set along with current deck

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex); // error handling
            }
        });

        return new MenuBar(menu1); // finally returns the menu back to the start method to be built
    }

    private HBox createButtonCenter(){ // Creates the buttons that will be used on the center pane

        Button previousButton = new Button("Previous"); // Creates new button variable's
        Button nextButton = new Button("Next");


        previousButton.setOnAction(e -> { // On action listener for the previous button
            deckList.getSelectionModel().selectPrevious(); // uses selectPrevious from ListView class to select previous item
        });
        nextButton.setOnAction(e -> { // on action listener for the next button
            deckList.getSelectionModel().selectNext(); // uses selectNext from ListView class to select next item
        });

        HBox buttonCenterPane = new HBox(previousButton, nextButton); // Places the buttons in an HBox so they can be placed side by side.
        buttonCenterPane.setSpacing(15); // spacing for the HBox
        buttonCenterPane.setAlignment(Pos.CENTER); // alignment for the HBox

        return buttonCenterPane; // returns the Button's back to the split pane method to be built
    }

    private HBox createButtonLeft(){ // method to build the buttons on the left

        Button addButton = new Button("Add"); // Creates new button variable's
        Button removeButton = new Button("Remove");

        // adds an action to the addButton to add a new card based on the promptText and descriptionText fields
        addButton.setOnAction(e -> { // on action listener
            // error handling if the use try's to use empty fields
            if (promptText.getText().isEmpty() || descriptionText.getText().isEmpty()){ // checks if field is empty
                IO.println("Prompt or Description empty"); // prints to the command line the Prompt is empty
            } else {
                // if fields are not empty adds a card to the current deck using promptText and descriptionText
                currentDeck.addCard(promptText.getText(), descriptionText.getText()); // adds a new card to the deck based on the input of the prompt and description
                deckList(); // refreshes the deck list
                promptText.clear(); // clears the prompt text field
                descriptionText.clear(); // clears the description text filed
            }
        });
        removeButton.setOnAction(e -> { // on action listener
            currentDeck.removeCard(currentIndex); // removes a card from the deck based on the current index
            deckList(); // refreshes the deck list
        });

        HBox buttonLeftPane = new HBox(addButton, removeButton); // Places the buttons in an HBox so they can be placed side by side.
        buttonLeftPane.setSpacing(15); // styling for the HBox
        buttonLeftPane.setAlignment(Pos.CENTER); // styling for the HBox

        return buttonLeftPane; // returns the button's to the split pane
    }

    // method to create the text fields for the prompt and description
    private VBox createTextFieldLeft(){

        Label promptLabel = new Label("Prompt:"); // creates a new label for the prompt header
        Label descriptionLabel = new Label("Description:"); // creates a new label for the description header

        promptText = new TextField(); // reassigns the promptText field as a new TextField
        promptText.setEditable(true); // makes the TextField editable

        descriptionText = new TextArea(); // reassigns the descriptionText with a new TextArea
        descriptionText.setEditable(true); // makes the TextArea editable
        descriptionText.setWrapText(true); // adds word wrap to the text area

        return new VBox(promptLabel, promptText, descriptionLabel, descriptionText); // returns a VBox with the text field and text area to the split pane
    }

    // method to create a list that holds the cards
    private ListView<FlashCard> deckList(){
        deckList.getItems().clear(); // clears the list everytime it is called

        for(int i = 0; i < currentDeck.getSize() ; i++){ // loops through the current deck based on its size
            deckList.getItems().add(currentDeck.getCard(i)); // adds the card from the current deck to the ListeView based on the current count
        } // NOTE, the deckList will use the toString method by default from the flashCard class

        return deckList; // returns the decklist to be used in split plane
    }

    // this method listens for changes in the list to update the current and previous card's selected.
    private void deckListListener(){
        deckList.getSelectionModel().selectedItemProperty().addListener((observable, oldCard, newCard) -> { // on action event that listens for the currently selected and previously selected item in the deck list
            if (newCard != null) {  // checks if the current card exsists and is not null
                currentCard = newCard; // assigns the currently selected item in the deck list to the current card field
                previousCard = oldCard; // assigns the previously selected card to the previous card field
                currentIndex = deckList.getSelectionModel().getSelectedIndex(); // changes the currentIndex field to match the index count of the item in the deck list
                frontLabel.setText(currentCard.getFront()); // sets the frontLabel field to match the currently selected card

                if (previousCard != null) { // checks to see if there is a previous card or not
                    backLabel.setText(previousCard.getBack()); // assigns the backLabel field based on the previous card selected
                } else {
                    backLabel.setText(""); // sets the backLabel field to empty if there is not previously card selected
                }
            }
        });
    }

    // the main method that launches the javafx stage
    public static void main(String[] args){
        launch(); // the launch command
    }
}

// Class to handle the individual flash cards.
class FlashCard {
    // private fields to hold the front and back strings of the cards
    private String frontOfCard; // field to hold the string for the front of the card
    private String backOfCard; // field to hold the string for the back of the card

    // Constructor to build the flashCard object and assign the strings based on the incoming parameters
    public FlashCard(String frontOfCard, String backOfCard){
        this.frontOfCard = frontOfCard;
        this.backOfCard = backOfCard;
    }

    // Simple toString method to return the strings separated by an easy to identify symbol
    public String toString(){
        // Currently using only front of card to fix ListView errors
        return frontOfCard;
    }

    // method used to change the string that is assigned to the front of the card
    public void setFront(String front){
        frontOfCard = front;
    }

    // method used to change the string this is assigned to the back of the card
    public void setBack(String back){
        backOfCard = back;
    }

    // method to return just the front the card as a string
    public String getFront(){
        return frontOfCard;
    }

    // method to return just the back of the card as a string
    public String getBack(){
        return backOfCard;
    }

}

class ImageFlashCard extends FlashCard{
    private String imageLocation;

    public ImageFlashCard(String frontOfCard, String backOfCard, String imageLocation){
        super(frontOfCard, backOfCard);
        this.imageLocation = imageLocation;
    }

    public void setImageLocation(String imageLocation){
        this.imageLocation = imageLocation;
    }

    public String getImageLocation(){
        return imageLocation;
    }

}

// The class used to handle the deck objects
class Deck{
    // fields used in the deck object to handle the deck name and the array that holds the cards
    private String deckName;
    private ArrayList<FlashCard> cards;

    // constructor to build the deck object
    public Deck(){
        cards = new ArrayList<>();
    }

    // method to change the name of the deck
    public void setDeckName(String deckName){
        this.deckName = deckName;
    }

    // method to get the name of the deck
    public String getDeckName(){
        return deckName;
    }

    // method to add card objects to the deck array list
    public void addCard(String frontOfCard, String backOfCard){
        cards.add(new FlashCard(frontOfCard,backOfCard));
    }

    // method to remove card objects from the deck array list based on the index provided
    public void removeCard(int index){
        cards.remove(index);
    }

    // method to get the card object
    public FlashCard getCard(int index){
        return cards.get(index);
    }

    // method to get the size of the card array IE the deck size
    public int getSize(){
        return cards.size();
    }
}

// class to handle the IO functions of my program
class DeckFileManager{

    // method to load in a deck from a .dek file
    public static Deck loadDeck(File file) throws FileNotFoundException {
        Deck deck = new Deck(); // creates a new empty deck object
        String fileName = file.getName(); // assigns a string variable the name of the file using java IO file methods
        if (fileName.endsWith(".dek")) { // checks to see if the file ends in the .dek format
            deck.setDeckName(fileName.substring(0, fileName.length() - 4)); // names the new deck based on the file name removing the .dek file exstention
        }

        Scanner input = new Scanner(file); // constructor to create a new scanner
        while (input.hasNextLine()){ // while loop to check for next line in the loaded file
            String line = input.nextLine(); // assigns the current line to a string variable
            String[] parts = line.split("##"); // splits the line variable into two parts based on the separator defined here.

            if (parts.length == 2){ // checks to make sure the splint line contains only two parts
                deck.addCard(parts[0], parts[1]); // adds a new card to the deck assigning the first part to the front and the second part to the back
            }
        }

        return deck; // returns the newly created deck
    }

    // method to save the current deck.
    public static void saveDeck(String deckName, Deck currentDeck) throws FileNotFoundException {
        if (deckName == null){ // checks if there is a deck name set
            deckName = "New Deck"; // if there is no name set defaults to New Deck
        }
        if (!deckName.endsWith(".dek")) { // checks to see if the deck name ends with the file format
            deckName += ".dek"; // adds the file format before saving the deck
        }

        PrintStream output = new PrintStream(deckName); // creates a new PrintStream object passing the deckName into as the file name

        for (int i = 0; i < currentDeck.getSize(); i++){ // loop that cycles through the current deck
            FlashCard card = currentDeck.getCard(i); // as it iterates through the deck it creates a new copy of each flash card
            output.println(card.getFront() + "##" + card.getBack()); // outputs the front and back of the card with the ## to the print stream
        }

        output.close(); // closes the output stream
    }
}