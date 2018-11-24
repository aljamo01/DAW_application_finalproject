/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daw.finalproject;

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
    private static final int OUR_DEFAULT_FRAME_WIDTH = 600;
    private static final int OUR_DEFAULT_FRAME_HEIGHT = 600;
    private static final int OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH = 25;

    private JPanel northProgramPanel;
    private JPanel centerProgramPanel;
    private JPanel southProgramPanel;

    private Playback playback;


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

    private Rectangle2D myCurrentSquare;        

    
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

        


  
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton clearButton = new JButton("Clear");
        JButton copyButton = new JButton("Copy");
        JButton appendButton = new JButton("Append");
        JButton mergeButton = new JButton("Merge");
                 
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton backtrackButton = new JButton("Backtrack");
        JButton fastForwardButton = new JButton("Fast Forward");
        JButton nextTrackButton = new JButton("Next Track");



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

        JButton normalizeButton = new JButton("Normalize");
        JButton clipButton = new JButton("Clip");
        JButton reverseButton = new JButton("Reverse");
        JButton resampleButton = new JButton("Resample");

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
        JProgressBar progressBarTrack1 = new JProgressBar(0);
        JProgressBar progressBarTrack2 = new JProgressBar(0);
        
        
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
	
	// wav file courtesy of https://freewavesamples.com
	playback = new Playback("C4.wav");
	playButton.addActionListener(playback.getPlayListener());
	pauseButton.addActionListener(playback.getPauseListener());
	backtrackButton.addActionListener(playback.getBacktrackListener());
	fastForwardButton.addActionListener(
	    playback.getFastForwardListener()
	);
	
	
    
   }
}
