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

        


  
        JButton SaveButton = new JButton("Save");
        JButton LoadButton = new JButton("Load");
        JButton ClearButton = new JButton("Clear");
        JButton CopyButton = new JButton("Copy");
        JButton AppendButton = new JButton("Append");
        JButton MergeButton = new JButton("Merge");
            
            
        JButton PlayButton =new JButton("Play");
        JButton PauseButton=new JButton("Pause");
        JButton BacktrackButon=new JButton("Backtrack");
        JButton FastfrowardButton=new JButton("Fast Forward");
        JButton NexttrackButon=new JButton("Next Track");



        //
        // Add buttons to panel
        //

        getNorthProgramPanel().add(SaveButton);
        getNorthProgramPanel().add(LoadButton);
        getNorthProgramPanel().add(ClearButton);
        getNorthProgramPanel().add(CopyButton);
        getNorthProgramPanel().add(AppendButton);
        getNorthProgramPanel().add(MergeButton);

        //
        // Add panel to frame
        //

        add(getNorthProgramPanel(), "North");

        //
        //
        getCenterProgramPanel().add(PlayButton);
        getCenterProgramPanel().add(PauseButton);
        getCenterProgramPanel().add(BacktrackButon);
        getCenterProgramPanel().add(FastfrowardButton);
        getCenterProgramPanel().add(NexttrackButon);

        JButton NormalizeBox = new JButton("Normalize");
        JButton ClipBox = new JButton("Clip");
        JButton ReverseBox = new JButton("Reverse");
        JButton ResampleBox = new JButton("Resample");
        getCenterProgramPanel().add(NormalizeBox);
        getCenterProgramPanel().add(ClipBox);
        getCenterProgramPanel().add(ReverseBox);
        getCenterProgramPanel().add(ResampleBox);

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
    
   }
}
