package Model.HttpRequest.PasteBin;

public interface PasteBin {

    /*
    This is aiming for send a short report
     */
    String SendReport(String res) throws InterruptedException;

    /*
    This is aiming for send a long report
     */
    String SendLongReport(String res);

    String getCurReport() throws InterruptedException;
}
