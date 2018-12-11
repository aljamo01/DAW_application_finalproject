
package daw.finalproject;

import java.io.*;
import javax.sound.sampled.*;

public class WavEditor {

    // Takes a sample rate and an AudioInputStream
    // and returns an AudioInputStream resampled
    // to the desired sample rate.
    public static AudioInputStream resample(
	float newSampleRate, 
	AudioInputStream oldInputStream
    ) {
	AudioFormat oldFormat = oldInputStream.getFormat();
	AudioFormat newFormat= new AudioFormat(
	    oldFormat.getEncoding(),
	    newSampleRate,
	    oldFormat.getSampleSizeInBits(),
	    oldFormat.getChannels(),
	    oldFormat.getFrameSize(),
	    oldFormat.getFrameRate()
	     * (newSampleRate / oldFormat.getSampleRate()),
	    oldFormat.isBigEndian()
	);

	AudioInputStream newInputStream 
	    = AudioSystem.getAudioInputStream(newFormat, oldInputStream);
	return newInputStream;
    }

}
