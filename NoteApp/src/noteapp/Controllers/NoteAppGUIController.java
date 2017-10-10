 lines (164 sloc)  4.56 KB
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import noteapp.Model.Notes;

public class NoteAppGUIController {
		private Notes notes;
// Lists;
		private List<String> sorts = new ArrayList<>();
		private List<String> noteList = new ArrayList<>();
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
		private JFXButton saveBtn;
		@FXML
		private JFXButton newBtn;
		@FXML
		private JFXButton removeBtn;
		@FXML
		private JFXListView<String> noteListView;
		@FXML
		private JFXTextArea notePad;

// ActionEvent Methods
    @FXML
    private void removeNote(ActionEvent e) throws IOException{
				// TODO Confirmation box
        // Get noteName from 'noteTitle'
				String noteName = noteTitle.getText();
				// Sends noteName to removeNote in Notes
				notes.removeNote(noteName);
				//Refresh noteListView
				newNote();
				updateNoteList();
    }

    @FXML
    private void newNote(){
      System.out.println("New note");
			noteTitle.setText("Note Title");
			notePad.setText(null);
    }

    @FXML
    private void saveNote() throws FileNotFoundException{
				// Get noteName from 'noteTitle'
				String noteName = noteTitle.getText();
				// Get content from 'notePad'
				String content = notePad.getText();
				// Sends noteName to updateNote in Notes
				notes.updateNote(noteName, content);
				// Update list
				updateNoteList();
    }

    @FXML
    private void toggleFav(){
        System.out.println("FAV toggle");
				// TODO toggles favorite value on note data.
        // Need to save this somewhere in the file
    }

		public void displayNote(String noteName){
					noteTitle.setText(noteName);
					notePad.setText(notes.getNote(noteName));
		}

		public void updateNoteList(){
			// Get list of note names from "Notes"
			noteList = FXCollections.observableArrayList(notes.allNames());
			noteListView.getItems().clear();
			// Add Items noteListView
			noteListView.getItems().addAll(noteList);
			// Add event listeners to list
			noteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	      @Override
	      public void changed(ObservableValue<? extends String> ov, String old_val, String selected) {
	          displayNote(selected);
			  }
		  });
		}
		public void displayKeyword(){
			
		}
		public void setKeywordList(char cmd, ArrayList<String> identifiers){
			if (identifiers == null) {
				// Get all
				switch (cmd){

                                        case '&' :                                 // new part
						notes.allWords();
						break;

					case '@' :
						notes.allMentions();
						break;
					case '#' :
						notes.allTopics();
						break;
					case '!' :
						notes.allIDs();
						break;
					default:
						updateNoteList();
				}
			}
			else{
				// Get selectively
				switch (cmd){
                                        case '&' :                               // new part
						notes.findWords();
						break;


					case '@' :
						notes.findMentions();
						break;
					case '#' :
						notes.findTopics();
						break;
					case '!' :
						notes.findIDs();
						break;
					default:
						updateNoteList();
					}
				}
			}

    public void initialize() {
        // Open directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder");
        File selectedDirectory = null;
				// Loop to make sure directory is chosen
				while(selectedDirectory == null){
					selectedDirectory = directoryChooser.showDialog(null);
				}

        // Create notes - send directory path
        this.notes = new Notes(selectedDirectory.getAbsolutePath());
        // Set List View Items
				updateNoteList();

				// Set values for sortBox
				sorts	= FXCollections.observableArrayList("","@", "#", "!");
        sortBox.getItems().addAll(sorts);

    }
}