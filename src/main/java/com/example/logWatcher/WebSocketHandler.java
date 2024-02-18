package com.example.logWatcher;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final String LOG_FILE_PATH = "src/main/sample_log.txt";
    private static final int LINES_TO_DISPLAY = 10;
    private long latestLineCount = 0;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        // Not handling; out of scope
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sendLastLines(session);
    }

    private void sendLastLines(WebSocketSession session){
        int i=0;
        while(i<2){
            try {
                long linesCount = Files.lines(Paths.get(LOG_FILE_PATH)).count();
                if (linesCount > latestLineCount){
                    latestLineCount = linesCount;
                    long linesToSkip = Math.max(0, linesCount - LINES_TO_DISPLAY);

                    Files.lines(Paths.get(LOG_FILE_PATH))
                            .skip(linesToSkip)
                            .forEach(line -> {
                                try {
                                    session.sendMessage(new TextMessage(line));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    TimeUnit.SECONDS.sleep(3);
                }
                i++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
