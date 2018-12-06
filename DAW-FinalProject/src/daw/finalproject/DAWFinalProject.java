/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daw.finalproject;

import java.io.*;

import java.awt.EventQueue;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.Scanner;
import javax.swing.filechooser.*;
import javax.sound.sampled.*;


public class DAWFinalProject
{

    public static void main(String [] commandLineArguments)
    {

        EventQueue.invokeLater(
            new Runnable()
            {

                public void run()
                {

                    ProgramFrame frame = new ProgramFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);

                }

            }
        );

    }

}

class ProgramFrame extends JFrame
{
    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    trackChooser.show((Component) event.getSource(), 0, 0);
	    currentAction = event.getSource();
	}
    }

    private class MenuItemListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    int trackNum = event.getSource() == track1MenuItem ? 1 : 2;
	    if (currentAction == loadButton)
		load(trackNum);
	    if (currentAction == clearButton)
		clear(trackNum);

	    if (currentAction == nextTrackButton)
		changePlayback(trackNum);
	    // TODO: add more.
	}
    }

    public class ProgressListener implements LineListener {
	public void update(LineEvent event) {
	    if (event.getType() == LineEvent.Type.START) {
		Clip clip = (Clip) event.getLine();
		while (clip.isRunning()) {
		    currentProgressBar.setValue(
			(clip.getFramePosition() * 100) 
			/ clip.getFrameLength()
		    );
		}
	    }
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
    
    private Playback playback1;
    private Playback playback2;
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
	    } else {
		track2File = fileChooser.getSelectedFile();
		if (playback2 == null)
		    playback2 = new Playback(track2File);
		else
		    playback2.changeFile(track2File);
	    }
	    if (trackNum == currentTrack)
		changePlayback(trackNum);
	}
    }

    private void clear(int trackNum) {
	if (currentTrack == trackNum)
	    removePlaybackListeners(trackNum == 1 ? playback1 : playback2);

	if (trackNum == 1)
	    playback1 = null;
	else
	    playback2 = null;
    }

    private void changePlayback(int trackNum) {
	Playback newPlayback = trackNum == 1 ? playback1 : playback2;
	Playback oldPlayback = currentTrack == 1 ? playback1 : playback2;
	if (trackNum != currentTrack)
	    removePlaybackListeners(oldPlayback);

	if (newPlayback != null) {
	    playButton.addActionListener(
		newPlayback.getPlayListener()
	    );
	    pauseButton.addActionListener(
		newPlayback.getPauseListener()
	    );
	    fastForwardButton.addActionListener(
		newPlayback.getFastForwardListener()
	    );
	    backtrackButton.addActionListener(
		newPlayback.getBacktrackListener()
	    );
	}
	if (trackNum == 1)
	    currentProgressBar = progressBarTrack1;
	else
	    currentProgressBar = progressBarTrack2;

	oldPlayback.removeProgressListener(progressListener);
	newPlayback.addProgressListener(progressListener);


	currentTrack = trackNum;
    }

    // Helper function to remove action listeners regarding a playback.
    private void removePlaybackListeners(Playback oldPlayback) {
	if (oldPlayback != null) {
	    playButton.removeActionListener(
		oldPlayback.getPlayListener()
	    );
	    pauseButton.removeActionListener(
		oldPlayback.getPlayListener()
	    );
	    fastForwardButton.removeActionListener(
		oldPlayback.getFastForwardListener()
	    );
	    backtrackButton.removeActionListener(
		oldPlayback.getBacktrackListener()
	    );
	}
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

	MenuItemListener menuItemListener = new MenuItemListener();
	track1MenuItem.addActionListener(menuItemListener);
	track2MenuItem.addActionListener(menuItemListener);

	ButtonListener buttonListener = new ButtonListener();
	loadButton.addActionListener(buttonListener);
	clearButton.addActionListener(buttonListener);

	nextTrackButton.addActionListener(buttonListener);
	
	
    
   }
}
