<h1>CS&141 (Winter 2026) Final Project</h1>

By Sean (BadgerSnacks) Seames.

This project is a virtual flash card application built in java. It is designed to help students study without having to purchase physical flash cards.
It supports Saving, Loading, and Editing flash cards in each deck.

<h2>Requirements</h2>
- Apache Maven https://maven.apache.org/install.html
- Java 25.0.1 https://www.oracle.com/java/technologies/downloads/

<h2>How to launch</h2>

Use the provided "Start FlashCards" batch file to launch the program.

OR...

Navigate to the root folder of the project CS141-Final-Project and use the following maven command via command line or terminal

`mvn clean javafx:run`


<h2>How to use</h2>
Under file you have 3 options:

**New Deck** - Clear the currently loaded deck (if there is one) and set the name of the new empty deck based on user input.

**Load Deck** - Clear the currently loaded deck (if there is one) and populate the deck list based on the supplied .dek file

**Save Deck** - Saves the currently loaded deck as a .dek file to be used later, name of the file will reflect the name of the deck.

<h3>Adding new cards</h3>
At any point you can edit the current deck by adding new cards with the Prompt and Description fields. Prompt will represent the front of the card and description will represent the back of the card.
When both Prompt and Description are provided with valid inputs use the "Add" button located on the bottom of the screen to add the card to the current deck.

<h3>Removing cards</h3>
The remove button at the bottom of the screen will remove the currently selected card from the deck list on the left.

<h3>Cycling through the deck</h3>
The next and previous buttons will cycle through the current deck progressing forward and backward, each time you change cards the description of the last card will be displayed on the right card.
Cards can also manually be selected from the deck list on the left side of the screen.

<h3>~Source Code~</h3>
- https://www.youtube.com/watch?v=GH-3YRAuHs0 JavaFX pane or GUI containers.
- https://download.java.net/java/GA/javafx25/docs/api/index.html JavaFX Documentation
- https://www.w3schools.com/java/java_map.asp Learning Map
- https://openjfx.io/javadoc/25/javafx.controls/javafx/scene/control/TextArea.html How to use JavaFX text field
- https://jenkov.com/tutorials/javafx/listview.html Listview tutorial
- https://stackoverflow.com/questions/30458972/how-to-set-only-a-top-padding-in-javafx Padding tips

<h3>LICENSE</h3>
GNU AFFERO GENERAL PUBLIC LICENSE Version 3