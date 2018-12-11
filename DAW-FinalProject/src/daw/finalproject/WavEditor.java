
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

    public static AudioInputStream reverse(AudioInputStream oldInputStream) {
	int frameSize = oldInputStream.getFormat().getFrameSize();
	long frameLength = oldInputStream.getFrameLength();
	byte oldByteArray[] = new byte[frameSize * (int) frameLength];
	byte newByteArray[] = new byte[frameSize * (int) frameLength];

	try {
	    oldInputStream.read(oldByteArray);

	    int currentPos = 0;
	    for (int framePos = (int) frameLength - 1; framePos >= 0; --framePos) {
		for (int bytePos = 0; bytePos < frameSize; ++bytePos) {
		    newByteArray[currentPos] 
			= oldByteArray[framePos * frameSize + bytePos];
		    ++currentPos;
		}
	    }

	    ByteArrayInputStream byteArrayInputStream
		= new ByteArrayInputStream(newByteArray);
	    AudioInputStream newInputStream 
		= new AudioInputStream(
			byteArrayInputStream,
			oldInputStream.getFormat(),
			frameLength
			);	
	    return newInputStream;
	}
	catch (Exception ex) {
	    return oldInputStream;
	}
    }
}

