package voicetotext.service;

import com.assemblyai.api.RealtimeTranscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranscriptionService {

    private final RealtimeTranscriber transcriber;

    public TranscriptionService(@Value("${assemblyai.api-key}") String apiKey,
                                @Value("${assemblyai.sample-rate}") int sampleRate) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key is missing! Set 'assemblyai.api-key' in config.");
        }

        this.transcriber = RealtimeTranscriber.builder()
                .apiKey(apiKey)
                .sampleRate(sampleRate)
                .endUtteranceSilenceThreshold(1000)
                .onSessionBegins(sessionBegins ->
                        System.out.println("Session started: " + sessionBegins.getSessionId()))
                .onFinalTranscript(transcript ->
                        System.out.println("Final: " + transcript.getText()))
                .onError(err ->
                        System.out.println("Error: " + err.getMessage()))
                .build();
    }

    public void connect() {
        transcriber.connect();
    }

    public void sendAudio(byte[] data) {
        transcriber.sendAudio(data);
    }

    public void close() {
        transcriber.close();
    }
}
