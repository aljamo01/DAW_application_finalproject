
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

    public static AudioInputStream normalize(
	    AudioInputStream oldInputStream) {
	if (oldInputStream.getFormat().getFrameSize() != 2)
	    return oldInputStream;

	long frameLength = oldInputStream.getFrameLength();
	byte oldByteArray[] = new byte[2 * (int) frameLength];
	byte newByteArray[] = new byte[(int) frameLength];

	try{
	    oldInputStream.read(oldByteArray);
	    for (int pos = 0; pos < (int) frameLength; ++pos) {
		// Taken from IntShortByteMethodsTests.java
		// from Prof. Zaring
		short dataPoint = ((short) (
		    (2*pos << 8 | 
		     ((int) (2*pos+1)) & 0xff)
                    & 0xffff));
		newByteArray[pos]= (byte) (dataPoint/2);
	    }

	    AudioFormat oldFormat = oldInputStream.getFormat();
	    AudioFormat newFormat= new AudioFormat(
		oldFormat.getEncoding(),
		oldFormat.getSampleRate(),
		oldFormat.getSampleSizeInBits(),
		oldFormat.getChannels(),
		1,
		oldFormat.getFrameRate(),
		oldFormat.isBigEndian()
	    );

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

    public static AudioInputStream merge(
	    AudioInputStream inputStream1,
	    AudioInputStream inputStream2) {

	inputStream1 = normalize(inputStream1);
	inputStream2 = normalize(inputStream2);

	if (inputStream1.getFormat().getSampleRate() 
		> inputStream2.getFormat().getSampleRate())
	    inputStream2 
		= resample(inputStream1.getFormat().getSampleRate(),
			inputStream2);
	if (inputStream2.getFormat().getSampleRate() 
		> inputStream1.getFormat().getSampleRate())
	    inputStream1 
		= resample(inputStream2.getFormat().getSampleRate(),
			inputStream1);

	long frameLength1 = inputStream1.getFrameLength();
	long frameLength2 = inputStream2.getFrameLength();

	long endFrameLength = 
	    frameLength1 > frameLength2 ? frameLength1 : frameLength2;

	byte oldByteArray1[] = new byte[(int) frameLength1];
	byte oldByteArray2[] = new byte[(int) frameLength2];
	byte newByteArray[] = new byte[(int) endFrameLength];

	try {
	    inputStream1.read(oldByteArray1);
	    inputStream2.read(oldByteArray2);
	    
	    for (int i = 0; i < (int) frameLength1; ++i) {
		newByteArray[i] += oldByteArray1[i];
	    }
	    for (int i = 0; i < (int) frameLength2; ++i) {
		newByteArray[i] += oldByteArray2[i];
	    }

	    ByteArrayInputStream byteArrayInputStream
		= new ByteArrayInputStream(newByteArray);
	    AudioInputStream newInputStream 
		= new AudioInputStream(
			byteArrayInputStream,
			inputStream1.getFormat(),
			endFrameLength
			);	
	    return newInputStream;

	}
	catch (Exception ex) {
	    return inputStream1;
	}





    }

}

