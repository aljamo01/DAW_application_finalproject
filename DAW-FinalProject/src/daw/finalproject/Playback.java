package daw.finalproject;

import java.io.File; 
import java.io.IOException; 
import java.awt.event.*;

import javax.sound.sampled.*;


public class Playback { 


    private static final long JUMP_MICROSEC = 5000000L;

    private File audioFile;
    private AudioInputStream audioInputStream;
    private Clip clip;


    private Clip getClip() {
	return clip;
    }

    //
    // Public methods
    // Preconditions: clip must be initialized.
    //

    public void play() {
	if (getClip().getFrameLength() == getClip().getFramePosition())
	    getClip().setFramePosition(0);	
	getClip().start();
    }

    public void pause() {
	getClip().stop();
    }

    public void backtrack() {
	final boolean wasRunning = getClip().isRunning();
	final long position = getClip().getMicrosecondPosition()
	    - JUMP_MICROSEC;
	final long minPosition = 0L;
	getClip().setMicrosecondPosition(
	    position > minPosition ? position : minPosition
	);
	// most painful workaround to send updates to line listeners
	if (!wasRunning) {
	    getClip().start();
	    getClip().stop();
	}
    }

    public void fastForward() {
	final boolean wasRunning = getClip().isRunning();
	final long position = getClip().getMicrosecondPosition() 
	    + JUMP_MICROSEC;
	final long maxPosition = getClip().getMicrosecondLength();
	getClip().setMicrosecondPosition(
	    position < maxPosition ? position : maxPosition
	);
	// most painful workaround to send updates to line listeners
	if (!wasRunning) {
	    getClip().start();
	    getClip().stop();
	}
    }

    public void addLineListener(LineListener lineListener) {
	getClip().addLineListener(lineListener);
    }

    public void removeLineListener(LineListener lineListener) {
	getClip().removeLineListener(lineListener);
    }

    public File getFile() {
	return audioFile;
    }

    public void changeFile(File myFile) {
	getClip().close();
	try {
	    audioFile = myFile; 
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
        

    }

    public Playback(File myFile) {
       
	try {
	    audioFile = myFile; 
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
        

    }

}
