package ua.com.khpi.database.yegorchevardin.lab07.program.startup;

/**
 * Interface for defining methods program class
 * which stands for displaying GUI interface and handling actions
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface ProgramStartup {
    /**
     * Method for starting the application
     * @param withDump Indicates should program execute pre built dump
     * or not
     */
    void start(boolean withDump);
}
