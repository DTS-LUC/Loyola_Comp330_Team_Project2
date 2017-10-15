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
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import noteapp.Model.Notes;

public class NoteAppGUIController {
// Notes
		private Notes notes;
// Lists
		private ObservableList<String> sorts = FXCollections.observableArrayList("Name","@", "#", "!", "*");
		private List<String> noteList = new ArrayList<>();
		private List<String> keywordList = new ArrayList<>();
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
		private JFXButton findBtn;
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
// TODO Favorite Toggle functionality

		@FXML
		public void search()throws NullPointerException{
			// Get sort command from ComboBox
			String cmd = sortBox.getValue();
			// Get search value
			String search = searchField.getText();
			if (cmd == null) {
				cmd = "Name";
			}
			//List for storing keywordss
			ArrayList<String> keywords = new ArrayList<>();
			//Get list of note keywords from "Notes"
			if (!"".equals(search)) {
				// Return all by sort if search bar is null
				switch (cmd) {
					case "@" :
						keywords.addAll(notes.matchMentions(search));
						break;
					case "#" :
						keywords.addAll(notes.matchTopics(search));
						break;
					case "!" :
						keywords.add(notes.matchId(search));
						break;
					case "*" :
						keywords.addAll(notes.allFavs());
						break;
					default :
						displayNote(search);
						return;
				}
			}else {
				switch (cmd) {
					case "@" :
						keywords.addAll(notes.allMentions());
						break;
					case "#" :
						keywords.addAll(notes.allTopics());
						break;
					case "!" :
						keywords.addAll(notes.allIDs());
						break;
					case "*" :
						keywords.addAll(notes.allFavs());
						break;
					default :
						updateNoteList();
						return;
				}
			}
			// Set list of keywords from "Notes"
			keywordList = FXCollections.observableArrayList(keywords);
			noteListView.getItems().clear();
			// Add Items noteListView
			noteListView.getItems().addAll(keywordList);
		}
    @FXML
    private void removeNote(ActionEvent e) throws IOException{
			// TODO Confirmation box
      // Get noteName from 'noteTitle'
			String noteName = noteTitle.getText();
			// Sends noteName to removeNote in Notes
			notes.removeNote(noteName);
			//Refresh note and noteListView
			newNote();
			updateNoteList();
    }

    @FXML
    private void newNote(){
			// Clear text
      noteTitle.setText(null);
			noteTitle.setPromptText("Untitled Note");
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
			// TODO check to see if favorite
			// notes.checkFav(noteName)
			// Check to see if searching for keyword or note
			char cmd = noteName.charAt(0);
			// If displaying keyword info
			if (!Character.isLetter(cmd) && cmd != '*') {
					// Hide buttons
					favToggle.setVisible(false);
					saveBtn.setVisible(false);
	        newBtn.setVisible(false);
	        removeBtn.setVisible(false);
					//If unique ID display note
					if (cmd == '!') {
						noteTitle.setText(notes.findByID(noteName));
					}
					// Else display keyword matches
					else{
						noteTitle.setText(noteName);
					}
					notePad.setText(notes.getKeywordInfo(noteName));
					// Set so user cannont edit results
					noteTitle.setEditable(false);
					notePad.setEditable(false);
					// Exit method
					return;
				}
			// If displaying note
			// Make sure buttons are visible
			favToggle.setVisible(true);
			saveBtn.setVisible(true);
			newBtn.setVisible(true);
			removeBtn.setVisible(true);
			// Display title and text
			noteTitle.setText(noteName);
			notePad.setText(notes.getNote(noteName));
			// Allow user to edit
			noteTitle.setEditable(true);
			notePad.setEditable(true);
		}

		public void updateNoteList(){
	    // Get noteName from 'noteTitle'
			String noteName = noteTitle.getText();
			// Get content from 'notePad'
			String content = notePad.getText();
			// Get list of note names from "Notes"
			noteList = FXCollections.observableArrayList(notes.allNames());
			// Clear previous list
			noteListView.getItems().clear();
			// Add Items noteListView
			noteListView.getItems().addAll(noteList);
      // Reset notes value
      noteTitle.setText(noteName);
      notePad.setText(content);
		}


    public void initialize() throws IOException {
        // Open directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder");
        File selectedDirectory = null;
				// Loop to make sure directory is chosen
				while(selectedDirectory == null){
					selectedDirectory = directoryChooser.showDialog(null);
				}
        // Create notes - send directory path to notes
        this.notes = new Notes(selectedDirectory.getAbsolutePath());
        // Set List
				updateNoteList();
				// Add event listeners to list
				noteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> ov, String old_val, String selected) {
		          displayNote(selected);
				  }
			  });
				// Set values for sortBox
        sortBox.setItems(sorts);
    }
}
