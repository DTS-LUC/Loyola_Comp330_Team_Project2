/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author don
 */
package noteapp.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import noteapp.Model.Notes;

public class NoteAppGUIController {
Notes notes;
// Lists;
ObservableList<String> sorts = FXCollections.observableArrayList(" ","@", "#", "^");
// JFX Objects
		@FXML
		private JFXTextField searchField;
		@FXML
		private JFXComboBox<String> sortBox;
		@FXML
		private JFXTextField noteTitle;
		@FXML
		private JFXToggleButton favToggle;
                @FXML
		private JFXButton folderBtn;
		@FXML
		private JFXButton updateBtn;
		@FXML
		private JFXButton newBtn;
		@FXML
		private JFXButton removeBtn;
		@FXML
		private JFXListView<?> noteListView;
		@FXML
		private JFXTextArea notePad;

		//TODO Create global models so they can be called by controller methods
		// private Notes notes;

// ActionEvent Methods
    @FXML
    private void removeNote(ActionEvent e){
        System.out.println("Remove button");
        // TODO create a function that deletes files
        // Needs to get noteName from 'noteTitle'
				// String noteName = title.value()
				// Sends noteName to removeNote in Notes
				// notes.removeNote(noteName)
				// Removes note from list
    }

    @FXML
    private void newNote(ActionEvent e){
        System.out.println("New button");
	// TODO create a function that creates new files
        // Asks user to confirm save
        // Brings up a blank note
        // Needs to take value from 'notePad' textArea
        // Also saves the filename from 'noteTitle'
    }

    @FXML // Setup within the FXML file. Called on button click
    private void updateNote(ActionEvent e){
        //Once button is clicked method begins
        System.out.println("Update button");
        // To get text values call the name of the component
        //  with '.getValue()'
        System.out.println(notePad.getText());
        // Program will then need to invoke the model class to update the values
    }

    @FXML
    private void toggleFav(ActionEvent e){

        System.out.println("HI");
				// TODO toggles favorite value on note data.
        // Need to svae this somewhere in the file
    }

    public void initialize() {
        // Open directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder");
        File selectedDirectory = directoryChooser.showDialog(null);

        if(selectedDirectory != null){
            System.out.println(selectedDirectory.getAbsolutePath());
        }else{

        }
        // Create notes - send directory path
        this.notes = new Notes(selectedDirectory.getAbsolutePath());
        // Set List View Items

	// Set values for sortBox
        sortBox.getItems().addAll(sorts);

    }
}
