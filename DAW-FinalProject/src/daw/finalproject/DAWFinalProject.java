/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daw.finalproject;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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

