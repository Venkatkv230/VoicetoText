package voicetotext.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import voicetotext.service.AudioRecorderService;

import java.util.Scanner;

@Component
public class TranscriptionRunner implements CommandLineRunner {

    private final AudioRecorderService recorderService;

    public TranscriptionRunner(AudioRecorderService recorderService) {
        this.recorderService = recorderService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting real-time transcription. Press Enter to stop...");
        recorderService.startRecording();

        new Scanner(System.in).nextLine();  // Wait for Enter
        recorderService.stopRecording();
        System.out.println("Transcription stopped.");
    }
}
