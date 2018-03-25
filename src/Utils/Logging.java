package Utils;

public class Logging {

    public static void LogError(String log){
        if(Constants.LOG) {
            System.out.println(Constants.ANSI_RED + log + Constants.ANSI_RESET);
        }
    }

    public static void LogSuccess(String log){
        if(Constants.LOG) {
            System.out.println(Constants.ANSI_GREEN + log + Constants.ANSI_RESET);
        }
    }

    public static void Log(String log){
        if(Constants.LOG) {
            System.out.println(Constants.ANSI_YELLOW + log + Constants.ANSI_RESET);
        }
    }

    public static void FatalErrorLog(String log){
        if(Constants.LOG) {
            System.out.println(Constants.ANSI_RED_BACKGROUND + log + Constants.ANSI_RESET);
        }
    }

    public static void FatalSuccessLog(String log){
        if(Constants.LOG) {
            System.out.println(Constants.ANSI_GREEN_BACKGROUND + log + Constants.ANSI_RESET);
        }
    }

}
