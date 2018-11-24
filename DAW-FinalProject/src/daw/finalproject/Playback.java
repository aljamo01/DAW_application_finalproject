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

    private class PlayListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    if (getClip().getFrameLength() == getClip().getFramePosition())
		getClip().setFramePosition(0);	
	    getClip().start();
	}

    }

    private class PauseListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    getClip().stop();
	}
    }

    private class BacktrackListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    final long x = getClip().getMicrosecondPosition()
		- JUMP_MICROSEC;
	    final long y = 0L;
	    getClip().setMicrosecondPosition(
		x > y ? x : y
	    );
	}
    }

    private class FastForwardListener implements ActionListener {
	public void actionPerformed(ActionEvent event){
	    final long x = getClip().getMicrosecondPosition() 
		+ JUMP_MICROSEC;
	    final long y = getClip().getMicrosecondLength();
	    getClip().setMicrosecondPosition(
		x < y ? x : y
	    );
	}
    }

    private static final long JUMP_MICROSEC = 5000000L;

    private File audioFile;
    private AudioInputStream audioInputStream;
    private Clip clip;

    private PlayListener playListener;
    private PauseListener pauseListener;
    private BacktrackListener backtrackListener;
    private FastForwardListener fastForwardListener;


    public PlayListener getPlayListener(){
	return playListener;
    }

    public PauseListener getPauseListener(){
	return pauseListener;
    }

    public BacktrackListener getBacktrackListener(){
	return backtrackListener;
    }

    public FastForwardListener getFastForwardListener(){
	return fastForwardListener;
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
	backtrackListener = new BacktrackListener();
	fastForwardListener = new FastForwardListener();
        

    }
}
