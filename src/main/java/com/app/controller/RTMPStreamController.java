package com.app.controller;

import org.bytedeco.javacpp.*;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.http.*;
import java.io.*;
import org.apache.commons.io.*;


@RestController
public class RTMPStreamController {

    @GetMapping("/")
    public String sample(){
        return "Hello world";
    }

@RestController
public class RtmpController {

    @GetMapping("/stream-from-file")
    public ResponseEntity<String> streamFromFile() throws Exception {

        // 1. Get Absolute File Path (Replace with your actual path)
        String absoluteFilePath = "/home/ubuntu/workspace/rtmp-server/video/sample.mp4"; 
        File file = new File(absoluteFilePath);

        // 2. Create Input Stream
        InputStream inputStream = new FileInputStream(file);

        // 3. Construct FFmpeg Command 
        String nginxRtmpUrl = "rtmp://your-nginx-server-address/live/mystream"; 
        String ffmpegCommand = "ffmpeg -re -i - -c copy -f flv " + nginxRtmpUrl;

        // 4. Start FFmpeg Process
        Process process = new ProcessBuilder(ffmpegCommand.split(" ")).start();

        // 5. Pipe File Data to FFmpeg
        try (OutputStream outputStream = process.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }

        // 6. Wait for FFmpeg to Finish
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // Handle error (log, return error response, etc.)
            return ResponseEntity.status(500).body("FFmpeg process failed");
        }

        return ResponseEntity.ok("Streaming from file completed!");
    }
}

}

