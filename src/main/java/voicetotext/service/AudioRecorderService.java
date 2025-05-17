package voicetotext.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AudioRecorderService {

    private final AudioFormat audioFormat;
    private final TranscriptionService transcriptionService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public AudioRecorderService(AudioFormat audioFormat, TranscriptionService transcriptionService) {
        this.audioFormat = audioFormat;
        this.transcriptionService = transcriptionService;
    }

    public void startRecording() {
        running.set(true);
        new Thread(() -> {
            try {
                TargetDataLine line = AudioSystem.getTargetDataLine(audioFormat);
                line.open(audioFormat);
                byte[] buffer = new byte[line.getBufferSize()];
                line.start();

                transcriptionService.connect();

                while (running.get()) {
                    line.read(buffer, 0, buffer.length);
                    transcriptionService.sendAudio(buffer);
                }

                line.stop();
                line.close();
                transcriptionService.close();

            } catch (LineUnavailableException e) {
                throw new RuntimeException("Audio line unavailable", e);
            }
        }).start();
    }

    public void stopRecording() {
        running.set(false);
    }
}
