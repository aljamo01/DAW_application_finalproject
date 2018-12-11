
package daw.finalproject;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.sound.sampled.*;

import daw.finalproject.WavEditor;

class ProgramFrame extends JFrame
{
    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    currentAction = event.getSource();
	    if (currentAction == playButton) {
		if (currentPlayback != null)
		    currentPlayback.play();
	    } else if (currentAction == pauseButton) {
		if (currentPlayback != null)
		    currentPlayback.pause();
	    } else if (currentAction == backtrackButton) {
		if (currentPlayback != null)
		    currentPlayback.backtrack();
	    } else if (currentAction == fastForwardButton) {
		if (currentPlayback != null)
		    currentPlayback.fastForward();
	    } else
		// Pass event handling to MenuItemListener
		trackChooser.show((Component) event.getSource(), 0, 0);
	}
    }

    private class MenuItemListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    int trackNum = event.getSource() == track1MenuItem ? 1 : 2;
	    Playback playback = (trackNum == 1 ? playback1 : playback2);
	    File tempSave = (trackNum == 1 ? save1 : save2);
	    if (currentAction == loadButton)
		load(trackNum);
	    if (currentAction == clearButton)
		clear(trackNum);

	    if (currentAction == nextTrackButton)
		changePlayback(trackNum);
	    if (currentAction == reverseButton) {
		if (playback != null){ try {
		    AudioInputStream audioInputStream =
			WavEditor.reverse(
			    AudioSystem.getAudioInputStream(
				playback.getFile()
				));
		    AudioSystem.write(
			audioInputStream,
			AudioFileFormat.Type.WAVE,
			tempSave);
		    playback.changeFile(tempSave);
		}
		// do not care about exceptions.
		catch(Exception e) {}
		if (trackNum == currentTrack)
		    changePlayback(trackNum);
		}
	    }

	    if (currentAction == resampleButton) {
		if (playback != null){ try {
		    AudioInputStream audioInputStream =
			WavEditor.resample(
			    11025.0f, 
			    AudioSystem.getAudioInputStream(
				playback.getFile()));
		    AudioSystem.write(
			audioInputStream, 
			AudioFileFormat.Type.WAVE, 
			tempSave);
		}
		catch(Exception ex) {
		    System.out.println("idk what");
		}
		playback.changeFile(tempSave);
		if (trackNum == currentTrack)
		    changePlayback(trackNum);
		}
	    }

	    if (currentAction == normalizeButton) {
		if (playback != null){ try {
		    AudioInputStream audioInputStream =
			WavEditor.normalize(
			    AudioSystem.getAudioInputStream(
				playback.getFile()
				));
		    AudioSystem.write(
			audioInputStream,
			AudioFileFormat.Type.WAVE,
			tempSave);
		    playback.changeFile(tempSave);
		}
		// do not care about exceptions.
		catch(Exception e) {}
		if (trackNum == currentTrack)
		    changePlayback(trackNum);
		}
	    }

	    if (currentAction == mergeButton) {
		if (playback1 != null && playback2 != null){ try {
		    AudioInputStream audioInputStream =
			WavEditor.merge(
			    AudioSystem.getAudioInputStream(
				playback1.getFile()
				),
			    AudioSystem.getAudioInputStream(
				playback2.getFile()));
		    AudioSystem.write(
			audioInputStream,
			AudioFileFormat.Type.WAVE,
			tempSave);
		    playback.changeFile(tempSave);
		}
		// do not care about exceptions.
		catch(Exception e) {}
		if (trackNum == currentTrack)
		    changePlayback(trackNum);
		}
	    }
	    // TODO: add more.
	}
    }

    public class ProgressListener implements LineListener {
	public void update(LineEvent event) {
	    Clip clip = (Clip) event.getLine();
	    if (event.getType() == LineEvent.Type.START)
		while (clip.isRunning()) {
		    currentProgressBar.setValue(
			(clip.getFramePosition() * 100) 
			/ clip.getFrameLength()
		    );
		}
	    else if (event.getType() == LineEvent.Type.STOP)
		currentProgressBar.setValue(
		    (clip.getFramePosition() * 100) / clip.getFrameLength()
		);
	    else if (event.getType() == LineEvent.Type.CLOSE)
		currentProgressBar.setValue(0);
	}
    }


    private static final int OUR_DEFAULT_FRAME_WIDTH = 600;
    private static final int OUR_DEFAULT_FRAME_HEIGHT = 600;
    private static final int OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH = 25;

    private JPanel northProgramPanel;
    private JPanel centerProgramPanel;
    private JPanel southProgramPanel;

    private JPopupMenu trackChooser;
    private JMenuItem track1MenuItem;
    private JMenuItem track2MenuItem;
    private Object currentAction;

    private JFileChooser fileChooser;
    private File track1File;
    private File track2File;

    private File save1;
    private File save2;
    
    private Playback playback1;
    private Playback playback2;
    private Playback currentPlayback;
    private int currentTrack = 1;

    private JButton saveButton;
    private JButton loadButton;
    private JButton clearButton;
    private JButton copyButton;
    private JButton appendButton;
    private JButton mergeButton;
	     
    private JButton playButton;
    private JButton pauseButton;
    private JButton backtrackButton;
    private JButton fastForwardButton;
    private JButton nextTrackButton;

    private JButton normalizeButton;
    private JButton clipButton;
    private JButton reverseButton;
    private JButton resampleButton;

    private JProgressBar progressBarTrack1;
    private JProgressBar progressBarTrack2;
    private JProgressBar currentProgressBar;

    private ProgressListener progressListener;


    private JPanel getNorthProgramPanel()
    {

        return northProgramPanel;

    }

    private JPanel getCenterProgramPanel()
    {

	return centerProgramPanel;

    }
    
    private JPanel getSouthProgramPanel()
    {
	return southProgramPanel;
    }

    private void setNorthProgramPanel(JPanel other)
    {

        northProgramPanel = other;

    }

    private void setCenterProgramPanel(JPanel other)
    {

        centerProgramPanel = other;

    }

    private void setSouthProgramPanel(JPanel other)
    {
	southProgramPanel = other;
    }

    private void load(int trackNum) {
	int approveVal = fileChooser.showOpenDialog(loadButton);
	if (approveVal == JFileChooser.APPROVE_OPTION) {
	    if (trackNum == 1) {
		track1File = fileChooser.getSelectedFile();
		if (playback1 == null)
		    playback1 = new Playback(track1File);
		else
		    playback1.changeFile(track1File);
		try {
		    AudioSystem.write(
			AudioSystem.getAudioInputStream(track1File),
			AudioFileFormat.Type.WAVE, 
			save1);
		}
		catch (Exception ex) {}
	    } else {
		track2File = fileChooser.getSelectedFile();
		if (playback2 == null)
		    playback2 = new Playback(track2File);
		else
		    playback2.changeFile(track2File);
		try {
		    AudioSystem.write(
			AudioSystem.getAudioInputStream(track2File),
			AudioFileFormat.Type.WAVE, 
			save2);
		}
		catch (Exception ex) {}
	    }
	    if (trackNum == currentTrack)
		changePlayback(trackNum);
	}
    }

    private void clear(int trackNum) {
	if (currentTrack == trackNum)
	    currentPlayback = null;

	if (trackNum == 1) {
	    playback1 = null;
	    progressBarTrack1.setValue(0);
	    save1.delete();
	}
	else {
	    playback2 = null;
	    progressBarTrack2.setValue(0);
	    save2.delete();
	}
    }

    private void changePlayback(int trackNum) {
	Playback newPlayback = trackNum == 1 ? playback1 : playback2;
	Playback oldPlayback = currentTrack == 1 ? playback1 : playback2;

	if (trackNum == 1)
	    currentProgressBar = progressBarTrack1;
	else
	    currentProgressBar = progressBarTrack2;

	if (oldPlayback != null)
	    oldPlayback.removeLineListener(progressListener);
	if (newPlayback != null)
	    newPlayback.addLineListener(progressListener);

	currentPlayback = newPlayback;
	currentTrack = trackNum;
    }

    
    public ProgramFrame()
    {

        setTitle("DAW");
        setSize(OUR_DEFAULT_FRAME_WIDTH, OUR_DEFAULT_FRAME_HEIGHT);



        //
        // Create panel to hold all components
        //

        setNorthProgramPanel(new JPanel());
	setCenterProgramPanel(new JPanel());
	setSouthProgramPanel(new JPanel());

          
	//
	// Initialize buttons.
	//
	
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        clearButton = new JButton("Clear");
        copyButton = new JButton("Copy");
        appendButton = new JButton("Append");
        mergeButton = new JButton("Merge");
                 
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        backtrackButton = new JButton("Backtrack");
        fastForwardButton = new JButton("Fast Forward");
        nextTrackButton = new JButton("Next Track");



        //
        // Add buttons to panel
        //

        getNorthProgramPanel().add(saveButton);
        getNorthProgramPanel().add(loadButton);
        getNorthProgramPanel().add(clearButton);
        getNorthProgramPanel().add(copyButton);
        getNorthProgramPanel().add(appendButton);
        getNorthProgramPanel().add(mergeButton);

        //
        // Add panel to frame
        //

        add(getNorthProgramPanel(), "North");

        //
        //
        getCenterProgramPanel().add(playButton);
        getCenterProgramPanel().add(pauseButton);
        getCenterProgramPanel().add(backtrackButton);
        getCenterProgramPanel().add(fastForwardButton);
        getCenterProgramPanel().add(nextTrackButton);

	// Initialize buttons.
        normalizeButton = new JButton("Normalize");
        clipButton = new JButton("Clip");
        reverseButton = new JButton("Reverse");
        resampleButton = new JButton("Resample");

        getCenterProgramPanel().add(normalizeButton);
        getCenterProgramPanel().add(clipButton);
        getCenterProgramPanel().add(reverseButton);
        getCenterProgramPanel().add(resampleButton);

	add(getCenterProgramPanel(), "Center");
     
        
        JSlider amplitudeSlider = new JSlider(JSlider.VERTICAL, 0 /* min */, 100 /* max */, 10 /* default */);
        amplitudeSlider.setMajorTickSpacing(10);
        amplitudeSlider.setPaintTicks(true);
        getSouthProgramPanel().add(amplitudeSlider);
        

	//Progress bar for each track: Track1 and Track2..
        progressBarTrack1 = new JProgressBar(0);
	progressBarTrack1.setMinimum(0);
	progressBarTrack1.setMaximum(100);

        progressBarTrack2 = new JProgressBar(0);
	progressBarTrack2.setMinimum(0);
	progressBarTrack2.setMaximum(100);


	progressListener = new ProgressListener();
	currentProgressBar = progressBarTrack1;
        
        
        JLabel TracksLabel = new JLabel("Track1- ");
        getSouthProgramPanel().add(TracksLabel);
        progressBarTrack1.setValue(0);
        progressBarTrack1.setStringPainted(true);
        getSouthProgramPanel().add(progressBarTrack1);
        
        JLabel TracksLabe2 = new JLabel("Track2- ");
        getSouthProgramPanel().add(TracksLabe2);
        progressBarTrack2.setValue(0);
        progressBarTrack2.setStringPainted(true);
        getSouthProgramPanel().add(progressBarTrack2);

	add(getSouthProgramPanel(), "South");


	// Actually do stuff
	
	// wav files courtesy of https://freewavesamples.com

	fileChooser = new JFileChooser();

	trackChooser = new JPopupMenu();
	track1MenuItem = new JMenuItem("Track 1");
	track2MenuItem = new JMenuItem("Track 2");
	trackChooser.add(track1MenuItem);
	trackChooser.add(track2MenuItem);

	//
	// Set up listeners.
	//

	MenuItemListener menuItemListener = new MenuItemListener();
	track1MenuItem.addActionListener(menuItemListener);
	track2MenuItem.addActionListener(menuItemListener);

	ButtonListener buttonListener = new ButtonListener();
	loadButton.addActionListener(buttonListener);
	clearButton.addActionListener(buttonListener);

	playButton.addActionListener(buttonListener);
	pauseButton.addActionListener(buttonListener);
	fastForwardButton.addActionListener(buttonListener);
	backtrackButton.addActionListener(buttonListener);
	nextTrackButton.addActionListener(buttonListener);

	reverseButton.addActionListener(buttonListener);
	resampleButton.addActionListener(buttonListener);
	normalizeButton.addActionListener(buttonListener);
	mergeButton.addActionListener(buttonListener);

	save1 = new File("." + File.separator + "save1.wav");
	save2 = new File("." + File.separator + "save2.wav");

	if (save1.exists())
	    playback1 = new Playback(save1);
	if (save2.exists())
	    playback2 = new Playback(save2);
	
   }
}
