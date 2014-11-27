package controller;

import java.io.*;
import java.net.*;
import java.util.*;

import model.order.*;
import sun.java2d.loops.DrawGlyphListAA.*;

public class Main {
	
	private static HashMap <String, String> generateInfo(File file) {
		HashMap <String, String> config = new HashMap<String, String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("=");
                config.put(line[0], line[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return config;
	}

    public static void main(String[] args) throws Exception {
		String workingDir = System.getProperty("user.dir");
		Generator start = new Generator();
		start.generate(generateInfo(new File(workingDir + "/res/config.ini")));    
    }

}
