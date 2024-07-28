package com.example.demo.domain.entities;


import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Bean
    Tesseract tesseract() {
    Tesseract tesseract = new Tesseract();
    tesseract.setDatapath("src/main/resources/tessdata");
    return tesseract;
    }
}
