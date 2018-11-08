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
    private static final int OUR_DEFAULT_FRAME_WIDTH = 450;
    private static final int OUR_DEFAULT_FRAME_HEIGHT = 300;
    private static final int OUR_DEFAULT_COMPONENT_FIELD_AND_AREA_WIDTH = 25;

    private JPanel myProgramPanel;
    private JPanel programPanel()
    {

        return myProgramPanel;

        }

    private void setProgramPanel(JPanel other)
    {

        myProgramPanel = other;

        }
    private Rectangle2D myCurrentSquare;        

    
    public ProgramFrame()
    {

        setTitle("DAW");
        setSize(OUR_DEFAULT_FRAME_WIDTH, OUR_DEFAULT_FRAME_HEIGHT);



        //
        // Create panel to hold all components
        //

           setProgramPanel(new JPanel());

        


  
            JButton SaveButton = new JButton("Save");
            JButton LoadButton = new JButton("Load");
            JButton ClearButton = new JButton("Clear");
            JButton CopyButton = new JButton("Copy");
            JButton AppendButton = new JButton("Append");
            JButton MergeButton = new JButton("Merge");
            
            
            JButton PlayButton =new JButton("Play");
            JButton PauseButton=new JButton("pause");
            JButton BacktrackButon=new JButton("Backtrack");
            JButton FastfrowardButton=new JButton("Fast Forward");
            JButton NexttrackButon=new JButton("Next Track");



        //
        // Add buttons to panel
        //

            programPanel().add(SaveButton);
            programPanel().add(LoadButton);
            programPanel().add(ClearButton);
            programPanel().add(CopyButton);
            programPanel().add(AppendButton);
            programPanel().add(MergeButton);
        //
        // Add panel to frame
        //

            add(programPanel());

        //
        //
        JButton NormalizeBox = new JButton("Normalize");
        JButton ClipBox = new JButton("Clip");
        JButton ReverseBox = new JButton("Reverse");
        JButton ResampleBox = new JButton("Resample");
        programPanel().add(NormalizeBox);
        programPanel().setVisible(true);
        programPanel().add(ClipBox);
        programPanel().setVisible(true);
        programPanel().add(ReverseBox);
        programPanel().setVisible(true);
        programPanel().add(ResampleBox);
        programPanel().setVisible(true);
    
        
        
     
        programPanel().add(PlayButton);
        programPanel().add(PauseButton);
        programPanel().add(BacktrackButon);
        programPanel().add(FastfrowardButton);
        programPanel().add(NexttrackButon);
        
        JSlider volumeSlider = new JSlider(JSlider.VERTICAL, 0 /* min */, 100 /* max */, 10 /* default */);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        programPanel().add(volumeSlider);
        
        
        
        

//Progress bar for each track: Track1 and Track2..
        JProgressBar progressBarTrack1 = new JProgressBar(0);
        JProgressBar progressBarTrack2 = new JProgressBar(0);
        
        
        JLabel TracksLabel = new JLabel("Track1- ");
        programPanel().add(TracksLabel);
        progressBarTrack1.setValue(0);
        progressBarTrack1.setStringPainted(true);
        programPanel().add(progressBarTrack1);
        
        JLabel TracksLabe2 = new JLabel("Track2- ");
        programPanel().add(TracksLabe2);
        progressBarTrack2.setValue(0);
        progressBarTrack2.setStringPainted(true);
        programPanel().add(progressBarTrack2);
    
   }
}