package com.darrylsite.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyLogger
{
    private static Logger log = null;
    static private String flog="nabplayer.log";
    private MyLogger()
    {}

    public static Logger getLogger()
    {
     if(log==null)
     {
            try
            {
                log = Logger.getLogger("singame");
                Handler h = new FileHandler(flog);
                h.setFormatter(new java.util.logging.SimpleFormatter());
                log.addHandler(h);
                log.setLevel(Level.ALL);
            }
            catch (IOException ex)
            {
                Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
     }

     return log;
    }

}