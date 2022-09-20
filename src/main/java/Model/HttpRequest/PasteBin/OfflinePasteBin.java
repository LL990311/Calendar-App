package Model.HttpRequest.PasteBin;

public class OfflinePasteBin implements PasteBin{

    public static String curReport = "";

    @Override
    public String SendReport(String res) throws InterruptedException {
        curReport = res;
        Thread.sleep(1000);
        return res;
    }

    @Override
    public String SendLongReport(String res) {
        curReport = res;
        return res;
    }

    @Override
    public String getCurReport() throws InterruptedException {
        Thread.sleep(1000);
        return curReport;
    }
}
