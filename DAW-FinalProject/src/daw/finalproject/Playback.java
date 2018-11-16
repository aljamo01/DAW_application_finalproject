package daw.finalproject;

import java.io.File; 
import java.io.IOException; 

import java.awt.event.*;

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 


public class Playback { 

    public class PlayListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    getClip().start();
	}

    }

    public class PauseListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    if (getClip().isRunning())
		getClip().stop();
	}
    }

    private File audioFile;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private PlayListener playListener;
    private PauseListener pauseListener;


    public PlayListener getPlayListener(){
	return playListener;
    }

    public PauseListener getPauseListener(){
	return pauseListener;
    }

    private Clip getClip() {
	return clip;
    }
  
    // TODO: constructors for other types, e.g. File, AudioInputStream
    public Playback(String audioFilePath) {
       
	try {
	    audioFile = new File(audioFilePath); 
	    audioInputStream = AudioSystem.getAudioInputStream(audioFile);
      
	    clip = AudioSystem.getClip();
	    // open the clip 
	    clip.open(audioInputStream);
	}
	// TODO: Non-lazy exception handling.
	catch(Exception ex) { 
	    System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
	} 

	playListener = new PlayListener();
	pauseListener = new PauseListener();
        

    }
}
