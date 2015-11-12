package com.github.p4535992.util.log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class for print a personal log file.
 * href : http://www.codeproject.com/Tips/315892/A-quick-and-easy-way-to-direct-Java-System-out-to
 * href: http://users.csc.calpoly.edu/~jdalbey/SWE/Tools/LogFile.java.
 * @author 4535992.
 * @version 2015-07-14.
 * @param <T> the generic type.
 */
@SuppressWarnings("unused")
public class SystemLog<T> extends OutputStream{

    /*SystemLog.t = getClass().getGenericSuperclass();
      SystemLog.pt = (java.lang.reflect.ParameterizedType) t;
      SystemLog.cl = (Class) pt.getActualTypeArguments()[0];
      SystemLog.clName = cl.getSimpleName();*/

    private static java.lang.reflect.Type t;
    private static java.lang.reflect.ParameterizedType pt;
    private static Class<?> cl ;
    private static String clName ;
    private static PrintLog printLog;

    /** {@code org.slf4j.Logger} */
    private static final org.slf4j.Logger SLF4JLogger = 
            org.slf4j.LoggerFactory.getLogger(SystemLog.class);
    private static final org.apache.log4j.Logger LOG4JLogger = 
            org.apache.log4j.Logger.getLogger(SystemLog.class);
    private static final java.util.logging.Logger UTILLogger = 
            java.util.logging.Logger.getLogger(SystemLog.class.getName());

    private static org.slf4j.Logger slf4j ;
    private static org.apache.log4j.Logger log4j;
    private static java.util.logging.Logger logUtil;

    private static Level level;

    private static SimpleDateFormat logTimestamp = new SimpleDateFormat("[HH:mm:ss]");
    private static DateFormat df;
    // private static SimpleDateFormat logTimesAndDateStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    // private static logWriter = new PrintWriter(new BufferedWriter(new FileWriter(logfile.getAbsolutePath(), true)));
    private static File logfile;

    private static Integer LEVEL;
    private static boolean isDEBUG=false;
    private static boolean isERROR;
    private static boolean isPRINT = false;
    private static boolean isInline = false;

    private static boolean isLogOff = false;
    private static boolean isLogUtil = false;
    private static boolean isLog4j = false;
    private static boolean isSlf4j = false;
    private static boolean logging = false;

    private static PrintWriter logWriter;
    private static String separator = ": ";

    OutputStream[] outputStreams;

    public static boolean isERROR() {return isERROR;}

    public static void setIsERROR(boolean isERROR) {SystemLog.isERROR = isERROR;}

    public static boolean isLogOff() {return isLogOff;}

    public static void setIsLogOff(boolean isLogOff) {SystemLog.isLogOff = isLogOff;}

    public static boolean isPRINT() {
        return isPRINT;
    }

    public static void setIsPRINT(boolean isPRINT) {
        SystemLog.isPRINT = isPRINT;
    }

    public static boolean isDEBUG() {
        return isDEBUG;
    }

    public static void setIsDEBUG(boolean isDEBUG) {
        SystemLog.isDEBUG = isDEBUG;
    }

    public static boolean isLog4j() {return isLog4j;}

    public static void setIsLog4j(boolean isLog4j) {SystemLog.isLog4j = isLog4j;}

    public static boolean isSlf4j() {return isSlf4j;}

    public static void setIsSlf4j(boolean isSlf4j) {SystemLog.isSlf4j = isSlf4j;}

    public static boolean isInline() { return isInline; }

    public static void setIsInline(boolean isInline) { SystemLog.isInline = isInline; }


    public SystemLog(){
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            SystemLog.logging = true;
            PrintLog.start();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SystemLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public SystemLog(String LOGNAME, String SUFFIX){
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            SystemLog.logfile =
                    new File(System.getProperty("user.dir")+File.separator
                            + LOGNAME + "_" +  timeStamp + "." + SUFFIX);
            printLog = new PrintLog(logfile);
            printLog.start();
            //PrintLog.start(logfile.getAbsolutePath());
        }catch (Exception ex) {
            java.util.logging.Logger.getLogger(SystemLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public SystemLog(String LOGNAME, String SUFFIX,String PATHFILE){
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            SystemLog.logfile = new File(PATHFILE + File.separator + LOGNAME + "_" + timeStamp + "." + SUFFIX);
            printLog = new PrintLog(logfile);
            printLog.start();
            //PrintLog.start(logfile.getAbsolutePath());
        }catch (Exception ex) {
            java.util.logging.Logger.getLogger(SystemLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public SystemLog(OutputStream... outputStreams) {
       this.outputStreams= outputStreams;
    }


   /**
     * Now all data written to System.out should be redirected into the file
     * "c:\\data\\system.out.txt". Keep in mind though, that you should make
     * sure to flush System.out and close the file before the JVM shuts down,
     * to be sure that all data written to System.out is actually flushed to the file.
     */
   /*
    private void setStream(){
        try{
            if(isPRINT) {
                //With Separated File
                //FileOutputStream fout = new FileOutputStream(logfile);
                //FileOutputStream ferr = new FileOutputStream(logfile);
                //Redirect stream to the SystemLog....
                //multiOut = new SystemLog(System.out, fout);
                //multiErr = new SystemLog(System.err, ferr);
                // Create/Open logfile.
                logStreamFile = new PrintLog(new BufferedOutputStream(new FileOutputStream(logfile)));
                multiOut = new SystemLog(System.out, logStreamFile);
                multiErr = new SystemLog(System.err, logStreamFile);

            }else{
                multiOut = new SystemLog(System.out);
                multiErr = new SystemLog(System.err);
            }
            //Write ont the File...
            //PrintStream around it to support the println/printf methods.
            *//*PrintStream stdout= new PrintStream(multiOut);
            PrintStream stderr= new PrintStream(multiErr);*//*


            // redirect standard output stream to the TextAreaOutputStream
            // redirect standard error stream to the TextAreaOutputStream
            //System.setErr(new PrintStream(new  SystemLog(System.err)));
            PrintLog stdout= new PrintLog(multiOut);
            PrintLog stderr= new PrintLog(multiErr);
            System.setOut(stdout);
            System.setErr(stderr);
        }
        catch (FileNotFoundException ex){
            //Could not create/open the file
        }
    }

    private void setStream(String fileName) throws FileNotFoundException {
        // First save the current standard output and standard error print streams.
        // These print streams will be restored when stop() is called.
        // Next, the log file is opened. If the log file does not exist, it's created.
        // Otherwise, the log file is emptied.
        // Finally, System.setOut() and System.setErr() are called to replace the
        // standard output and standard error print streams with LogFile print streams.

        // Create/Open logfile.
        logStreamFile = new PrintLog(
                new BufferedOutputStream(
                        new FileOutputStream(fileName)));

        multiOut = new SystemLog(System.out, logStreamFile);
        multiErr = new SystemLog(System.err, logStreamFile);
        PrintLog.setLogStreamFile(logStreamFile);

        // Indicate that output is to be redirected.
        PrintLog stdout= new PrintLog(multiOut);
        PrintLog stderr= new PrintLog(multiErr);
        System.setOut(stdout);
        System.setErr(stderr);

    }*/




    @Override
    public void close() throws IOException {
        // Restore the original standard output and standard error.
        // Then close the log file.
        /*System.setOut(oldStdout);
        System.setErr(oldStderr);
        if(logStreamFile!=null) logStreamFile.close();*/
        if(outputStreams != null) {
            for (OutputStream out : outputStreams) {
                if(out!=null)out.close();
            }
        }
        printLog.stop();
    }

    @Override
    public void flush() throws IOException{
        for (OutputStream out: outputStreams) {
            if (out != null) out.flush();
        }
    }

    @Override
    public void write(int b) throws IOException{
        for (OutputStream out : outputStreams) {
            if(out!=null)out.write(b);
        }
    }

    @Override
    public void write(byte[] b) throws IOException{
        for (OutputStream out : outputStreams) {
            if(out!=null)out.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException{
        for (OutputStream out : outputStreams) {
            if(out!=null)out.write(b, off,len);
        }
    }

        /*public static void logStackTrace(Exception e, org.slf4j.Logger logger) {
        logger.debug(e.getMessage());
        for (StackTraceElement stackTrace : e.getStackTrace()) {
            logger.error(stackTrace.toString());
        }
    }

    public static void logException(Exception e, org.slf4j.Logger logger) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logger.error(sw.toString());
    }*/

    /**
     * Created by 4535992 on 05/11/2015.
     */
    public static class PrintLog extends PrintStream {

        private static SimpleDateFormat logTimestamp = new SimpleDateFormat("[HH:mm:ss]");

        //-----------------------------------------------
        //Constructor
        //-----------------------------------------------

        public PrintLog(PrintStream out) {
            super(out);
        }

        public PrintLog(OutputStream out) {
            super(out, false);
        }

        public PrintLog(OutputStream out, boolean autoFlush) {
            super(out, autoFlush);
        }

        public PrintLog(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
            super(out,autoFlush,encoding);
        }

        public PrintLog(String fileName) throws FileNotFoundException {
            this(new FileOutputStream(fileName),false);
        }

        public PrintLog(String fileName,String csn) throws FileNotFoundException, UnsupportedEncodingException {
            super(new FileOutputStream(fileName), false, csn);
        }

        public PrintLog(File file) throws FileNotFoundException {
            super(new FileOutputStream(file), false);
        }

        public PrintLog(File file, String csn)throws FileNotFoundException, UnsupportedEncodingException {
            super(new FileOutputStream(file), false, csn );
        }

        //------------------------------------
        // SETTER
        //------------------------------------

        public static File setFile(String fileName){
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            return new File(System.getProperty("user.dir")+File.separator +  fileName + "_" +  timeStamp + ".log");
        }

        public static String setName(String fileName){
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            return System.getProperty("user.dir")+File.separator +  fileName + "_" +  timeStamp + ".log";
        }

    /*private OutputStream setStream(String fileName) throws FileNotFoundException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return new FileOutputStream(
                new File(System.getProperty("user.dir")+File.separator +  fileName + "_" +  timeStamp + ".log"));
    }*/

        //------------------------------------
        //Methods
        //------------------------------------

        @Override
        public void flush() {super.flush();}
        @Override
        public void close() {
            // Restore the original standard output and standard error.
            // Then close the log file.
            System.setOut(oldStdout);
            System.setErr(oldStderr);
            try {
                logStreamFile.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            super.close();
        }
        @Override
        public boolean checkError() {return super.checkError();}

        @Override
        public void write(int b) {
            if(logStreamFile!=null) {
                try {
                    logStreamFile.write(b);
                }
                catch (Exception e){
                    e.printStackTrace();
                    setError();
                }
            }
            super.write(b);
        }

        @Override
        public void write(byte buf[], int off, int len) {
            if(logStreamFile!=null) {
                try {
                    logStreamFile.write(buf, off, len);
                } catch (Exception e) {
                    e.printStackTrace();
                    setError();
                }
            }
            super.write(buf,off,len);
        }

        //---------------------------------------------------
        // Methods that do not terminate lines
        //---------------------------------------------------

        @Override
        public void print(boolean b) {
            super.print(b);
        }
        @Override
        public void print(char c) {
            super.print(c);
        }
        @Override
        public void print(int i) {
            super.print(i);
        }
        @Override
        public void print(long l) {
            super.print(l);
        }
        @Override
        public void print(float f) {
            super.print(f);
        }
        @Override
        public void print(double d) {super.print(d);}
        @Override
        public void print(char s[]) {super.print(s);}
        @Override
        public void print(String s) { super.print(s);}
        @Override
        public void print(Object obj) {
            super.print(obj);
        }

        //---------------------------------------------------
    /* Methods that do terminate lines */
        //---------------------------------------------------

        @Override
        public void println() {super.println();}
        @Override
        public void println(boolean x) {super.println(x);}
        @Override
        public void println(char x) { super.println(x);}
        @Override
        public void println(int x) {super.println(x);}
        @Override
        public void println(long x) {super.println(x); }
        @Override
        public void println(float x) {super.println(x); }
        @Override
        public void println(double x) {super.println(x); }
        @Override
        public void println(char x[]) {super.println(x); }
        @Override
        public void println(String x) {super.println(x); }
        @Override
        public void println(Object x) { super.println(x); }

        @Override
        public PrintLog printf(String format, Object ... args) {
            return (PrintLog) super.printf(format, args);
        }
        @Override
        public PrintLog printf(Locale l, String format, Object ... args) {
            return (PrintLog) super.printf(l,format, args);
        }
        @Override
        public PrintLog format(String format, Object ... args) {
            return (PrintLog) super.format(format, args);
        }
        @Override
        public PrintLog format(Locale l, String format, Object ... args) {
            return (PrintLog) super.format(l,format, args);
        }
        @Override
        public PrintLog append(CharSequence csq) {return (PrintLog) super.append(csq);}
        @Override
        public PrintLog append(CharSequence csq, int start, int end) {return (PrintLog) super.append(csq,start,end);}
        @Override
        public PrintLog append(char c) {return (PrintLog) super.append(c); }

        //------------------------------------------------------------------------
        //New Methods
        //------------------------------------------------------------------------

        static PrintStream oldStdout;
        static PrintStream oldStderr;
        static OutputStream logStreamFile;
        static File logFile;

        public static OutputStream getLogStreamFile() {
            return logStreamFile;
        }

        public static void setLogStreamFile(OutputStream logStreamFile) {
            PrintLog.logStreamFile = logStreamFile;
        }

        /**
         * Now all data written to System.out should be redirected into the file
         * "c:\\data\\system.out.txt". Keep in mind though, that you should make
         * sure to flush System.out and close the file before the JVM shuts down,
         * to be sure that all data written to System.out is actually flushed to the file.
         */
        @SuppressWarnings("rawtypes")
        public static void start(){
            // Save current settings for later restoring.
            oldStdout = System.out;
            oldStderr = System.err;
            SystemLog multiOut = new SystemLog(System.out);
            SystemLog multiErr = new SystemLog(System.err);
            PrintLog stdout= new PrintLog(multiOut);
            PrintLog stderr= new PrintLog(multiErr);
            System.setOut(stdout);
            System.setErr(stderr);
        }

        @SuppressWarnings("rawtypes")
        public static void start(String fileName) throws FileNotFoundException {
            oldStdout = System.out;
            oldStderr = System.err;
            logFile = new File(fileName);
            logStreamFile = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName)));
            SystemLog multiOut = new SystemLog(System.out,logStreamFile);
            SystemLog multiErr = new SystemLog(System.err,logStreamFile);
            PrintLog stdout= new PrintLog(multiOut);
            PrintLog stderr= new PrintLog(multiErr);
            System.setOut(stdout);
            System.setErr(stderr);
        }

        @SuppressWarnings("rawtypes")
        public void start(File file) throws FileNotFoundException {
            // Save current settings for later restoring.
            oldStdout = System.out;
            oldStderr = System.err;
            logFile = file;
            logStreamFile = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            SystemLog multiOut = new SystemLog(System.out,logStreamFile);
            SystemLog multiErr = new SystemLog(System.err,logStreamFile);
            PrintLog stdout= new PrintLog(multiOut);
            PrintLog stderr= new PrintLog(multiErr);
            System.setOut(stdout);
            System.setErr(stderr);
        }

        /**
         *  Ceases logging and restores the original settings.
         */
        public void stop(){
            // Restore the original standard output and standard error.
            // Then close the log file.
            System.setOut(oldStdout);
            System.setErr(oldStderr);
            try {
                if (logStreamFile != null) {
                    logStreamFile.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // Logging Methods
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Writes a message to the log.
     * @param logEntry message to write as a log entry
     */
    @SuppressWarnings("rawtypes")
    protected static void write(String logEntry) {
        try {
            if (logEntry != null) {
                if (isLogOff) {
                    if(isInline){
                        if (isERROR) System.err.print(logEntry);
                        else System.out.print(logEntry);
                    }else {
                        if (isERROR) System.err.println(logEntry);
                        else System.out.println(logEntry);
                    }
                } else {
                    if (logfile == null || !logfile.exists()) new SystemLog();
                    //if(!logging){ log = new SystemLog();}
                    StringBuilder sb = new StringBuilder();
                    if (logTimestamp != null)
                        sb.append(logTimestamp.format(new Date()));
                    else {
                        if (df == null)
                            df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
                        sb.append(df.format(logTimestamp));
                    }

                    if (isDEBUG) {
                        sb.append(Level.DEBUG);
                    }
                    sb.append(level.toString());
                    sb.append(logEntry);

                    if(isInline){
                        if (isERROR) System.err.print(sb.toString());
                        else System.out.print(sb.toString());
                    }else {
                        if (isERROR) System.err.println(sb.toString());
                        else System.out.println(sb.toString());
                    }

                    if (isPRINT) {
                        try (PrintWriter pWriter = new PrintWriter(new BufferedWriter(new FileWriter(logfile.getAbsolutePath(), true)))) {
                            //try{
                            pWriter.print(sb.toString() + System.getProperty("line.separator"));
                            pWriter.flush();
                            //logWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }//if logOff...
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            isERROR=false;
        }
    }

    public static void console(String logEntry){console(logEntry,null);}
    public static void console(String logEntry,Class<?> thisClass){
        level = Level.VOID;
        if(thisClass!=null) {
            if(isLog4j){ log4j = org.apache.log4j.Logger.getLogger(thisClass); log4j.info(logEntry);}
            if(isSlf4j){
                //String confidentialMarkerText = "CONFIDENTIAL";
                //org.slf4j.Marker confidentialMarker = org.slf4j.MarkerFactory.getMarker(confidentialMarkerText);
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass); slf4j.info(logEntry);}
            else System.out.println(logEntry);
        }
        else System.out.println(logEntry);
    }

    public static void messageInline(String logEntry){isInline=true; message(logEntry, null);}
    public static void message(String logEntry){message(logEntry,null);}
    public static void message(String logEntry,Class<?> thisClass){
        level = Level.OUT;
        if(thisClass!=null) {
            if(isLog4j){ log4j = org.apache.log4j.Logger.getLogger(thisClass); log4j.info(logEntry);}
            if(isSlf4j){ slf4j = org.slf4j.LoggerFactory.getLogger(thisClass); slf4j.info(logEntry);}
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void error(String logEntry){error(logEntry, new Throwable(logEntry), null);}
    public static void error(String logEntry,Exception ex){error(logEntry+"->"+ex.getMessage(),null,null);}
    public static void error(String logEntry,Exception ex,Class<?> thisClass){error(logEntry, new Throwable(ex.getCause()),thisClass);}
    public static void error(String logEntry,Throwable th){error(logEntry,th,null);}
    public static void error(String logEntry,Throwable th,Class<?> thisClass){
        level = Level.ERR;
        isERROR=true;
        if(thisClass!=null) {
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                if(th!=null)log4j.info(logEntry,th);
                else log4j.info(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                if (th != null) slf4j.info(logEntry, th);
                else slf4j.info(logEntry);
            }
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void warning(String logEntry){warning(logEntry, null, null);}
    public static void warning(String logEntry,Class<?> thisClass){warning(logEntry,null,thisClass);}
    public static void warning(Throwable th){warning(th.getMessage() + "," + th.getLocalizedMessage(), th, null);}
    public static void warning(Throwable th,Class<?> thisClass){warning(th.getMessage() + "," + th.getLocalizedMessage(), th, thisClass);}
    public static void warning(String logEntry,Throwable th,Class<?> thisClass){
        level = Level.WARN;
        isERROR=true;
        if(thisClass!=null) {
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                if(th!=null)log4j.info(logEntry,th);
                else log4j.info(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                if (th != null) slf4j.info(logEntry, th);
                else slf4j.info(logEntry);
            }
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void hibernate(String logEntry) {hibernate(logEntry, null);}
    public static void hibernate(String logEntry,Class<?> thisClass) {
        level = Level.HIBERNATE;
        if(thisClass!=null) {
            if(isLog4j){ log4j = org.apache.log4j.Logger.getLogger(thisClass); log4j.info(logEntry);}
            if(isSlf4j){ slf4j = org.slf4j.LoggerFactory.getLogger(thisClass); slf4j.info(logEntry);}
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void sparql(String logEntry) {sparql(logEntry, null, null);}
    public static void sparql(String logEntry,Class<?> clazz) {sparql(logEntry,null,clazz);}
    public static void sparql(String logEntry,Exception e) {sparql(logEntry,new Throwable(e.getCause()),null);}
    //public static void sparql(String logEntry,Exception e,Class<?> clazz) {sparql(logEntry,new Throwable(e.getCause()),clazz);}
    public static void sparql(Exception e) {sparql(e.getMessage(),new Throwable(e.getCause()),null);}
    public static void sparql(Exception e,Class<?> clazz) {sparql(e.getMessage(),new Throwable(e.getCause()),clazz);}
    public static void sparql(String logEntry,Throwable th,Class<?> thisClass){
        level = Level.SPARQL;
        if(thisClass!=null) {
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                if(th!=null)log4j.info(logEntry,th);
                else log4j.info(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                if (th != null) slf4j.info(logEntry, th);
                else slf4j.info(logEntry);
            }
            else write(logEntry);
        }
        else write(logEntry);
    }


    public static void query(String logEntry) { query(logEntry, null);}
    public static void query(String logEntry,Class<?> thisClass){
        level = Level.QUERY;
        if(thisClass!=null) {
            if(isLog4j){ log4j = org.apache.log4j.Logger.getLogger(thisClass); log4j.info(logEntry);}
            if(isSlf4j){ slf4j = org.slf4j.LoggerFactory.getLogger(thisClass); slf4j.info(logEntry);}
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void attention(String logEntry) {attention(logEntry, null);}
    public static void attention(String logEntry,Class<?> thisClass) {
        level = Level.ATTENTION;
        if(thisClass!=null) {
            if(isLog4j){ log4j = org.apache.log4j.Logger.getLogger(thisClass); log4j.info(logEntry);}
            if(isSlf4j){ slf4j = org.slf4j.LoggerFactory.getLogger(thisClass); slf4j.info(logEntry);}
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void abort(int rc){System.exit(rc);}
    public static void abort(int rc,String logEntry){abort(rc, logEntry, null);}
    public static void abort(int rc, String logEntry,Class<?> thisClass) {
        level = Level.ABORT;
        isERROR=true;
        if(thisClass!=null) {
            if(isLogUtil){
                logUtil = Logger.getLogger(thisClass.getName());
                logUtil.log(java.util.logging.Level.SEVERE, logEntry);
            }
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                log4j.fatal(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                slf4j.error(logEntry);
            }
            else  write(logEntry);;
        }
        else  write(logEntry);
        System.exit(rc);
    }

    public static void throwException(Throwable th){
        throwException( th.getClass().getName() + ": " + th.getMessage(), new Throwable(th.getCause()), th.getClass());}
    public static void throwException(Exception e){
        throwException( e.getClass().getName() + ": " + e.getMessage(), new Throwable(e.getCause()), e.getClass());}
    public static void throwException(String logEntry,Throwable th,Class<?> thisClass){
        level = Level.THROW;
        isERROR=true;
        if(thisClass!=null) {
            if(isLogUtil){
                logUtil = Logger.getLogger(thisClass.getName());
                if(th!=null)logUtil.log(java.util.logging.Level.SEVERE, logEntry, th);
                else logUtil.log(java.util.logging.Level.SEVERE, logEntry);
            }
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                if(th!=null)log4j.fatal(logEntry, th);
                else log4j.fatal(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                if (th != null) slf4j.error(logEntry, th);
                else slf4j.error(logEntry);
            }
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void exception(Exception e){ exception(
            e.getClass().getName() + ": " + e.getMessage(), new Throwable(e.getCause()), e.getClass());}
    public static void exception(Exception e,Class<?> clazz){ exception(
            e.getClass().getName() + ": " + e.getMessage(), new Throwable(e.getCause()), clazz);}
    public static void exception(String logEntry){ exception(logEntry, new Throwable(logEntry), null);}
    public static void exception(String logEntry,Class<?> clazz){ exception(logEntry, new Throwable(logEntry), clazz);}
    public static void exception(String logEntry,Throwable th,Class<?> thisClass){
        level = Level.EXCEP;
        isERROR=true;
        if(thisClass!=null) {
            if(isLogUtil){
                logUtil = Logger.getLogger(thisClass.getName());
                if(th!=null)logUtil.log(java.util.logging.Level.SEVERE, logEntry, th);
                else logUtil.log(java.util.logging.Level.SEVERE, logEntry);
            }
            if(isLog4j){
                log4j = org.apache.log4j.Logger.getLogger(thisClass);
                if(th!=null)log4j.fatal(logEntry,th);
                else log4j.fatal(logEntry);
            }
            if(isSlf4j) {
                slf4j = org.slf4j.LoggerFactory.getLogger(thisClass);
                if (th != null) slf4j.error(logEntry, th);
                else slf4j.error(logEntry);
            }
            else write(logEntry);
        }
        else write(logEntry);
    }

    public static void exceptionAndAbort(Exception e){
        exception(e);
        abort(0);
    }

    public enum Level {
        VOID(0), OUT(1), WARN(2),ERR(3),ABORT(4),HIBERNATE(5),SPARQL(6),QUERY(7),THROW(8),EXCEP(9),ATTENTION(10),DEBUG(11);
        private final Integer value;
        Level(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            String prefix="";
            String suffix ="";
            switch (this) {
                case ERR: prefix = "[ERROR] -> "; break;
                case WARN: prefix ="[WARNING] -> "; break;
                case ABORT: prefix ="[EXIT] -> "; break;
                case HIBERNATE: prefix = "[HIBERNATE] -> "; break;
                case SPARQL: prefix ="[SPARQL] -> "; break;
                case QUERY: prefix ="[QUERY] -> "; break;
                case ATTENTION: prefix ="=====[ATTENTION]===== -> "; break;
                case EXCEP: prefix ="[EXCEPTION] ->"; break;
                case DEBUG: prefix = "<!-- [debug] ";  break;
            }
            return prefix;
        }
    }
}


