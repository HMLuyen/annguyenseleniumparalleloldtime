package com.common;

/**
 * Command Prompt - this class contains method to run windows and mac commands
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {

    static Process p;
    ProcessBuilder builder;

    /**
     * This method run command on windows and mac
     *
     * @param strings to run
     */
    public String runCommand(String command) throws InterruptedException, IOException {
        p = Runtime.getRuntime().exec(command);
        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
/*			if (line.isEmpty()) {
                break;
			}*/
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug") && line.contains("Complete"))
                break;
            i++;
        }
        return allLine;

    }

//    public String runCommandWithBuilder(String... command) {
//        String allLine = "";
//        try {
//            builder = new ProcessBuilder(command);
//            builder.directory(new File(AutomationConfig.IMGK_PATH));
//            builder.redirectErrorStream(true);
//            p = builder.start();
//            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = "";
//
//            int i = 1;
//            while ((line = r.readLine()) != null) {
///*			if (line.isEmpty()) {
//                break;
//			}*/
//                allLine = allLine + "" + line + "\n";
//                if (line.contains("Console LogLevel: debug") && line.contains("Complete"))
//                    break;
//                i++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return allLine;
//    }


}