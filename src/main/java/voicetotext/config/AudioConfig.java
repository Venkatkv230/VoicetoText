package voicetotext.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFormat;

@Configuration
public class AudioConfig {

    @Bean
    public AudioFormat audioFormat() {
        return new AudioFormat(16000, 16, 1, true, false);
    }
}
