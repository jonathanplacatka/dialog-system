package dataproviders;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import eventsystem.EventDispatcher;

public class MicAudioProvider extends DataProvider {
    private int sampleRate;
    private int bufferSize;

    public MicAudioProvider(EventDispatcher dispatcher, int sampleRate, int bufferSize) {
        super(dispatcher);
        this.sampleRate = sampleRate;
        this.bufferSize = bufferSize;
    } 

    @Override
    public void run() {
        try {
            AudioFormat audioFormat = new AudioFormat(this.sampleRate, 16, 1, true, false);
            DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            TargetDataLine dataline = (TargetDataLine)AudioSystem.getLine(lineInfo);
            AudioInputStream audioStream = new AudioInputStream(dataline);

            dataline.open(audioFormat);
            dataline.start();
    
            byte[] buffer = new byte[this.bufferSize];
            double[] samples = new double[this.bufferSize / 2];

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

            while(audioStream.read(buffer) >= 0) {
                System.out.println("new audio");
                byteBuffer.position(0);
                for(int i = 0; i < this.bufferSize/2; i++) {
                   samples[i] = (double)byteBuffer.getShort();
                }
                this.notifyListeners(samples);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
