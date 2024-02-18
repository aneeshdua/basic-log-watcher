package com.example.logWatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LogWatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogWatcherApplication.class,args);
	}
}
