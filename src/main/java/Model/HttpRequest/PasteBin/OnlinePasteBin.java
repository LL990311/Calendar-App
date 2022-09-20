package Model.HttpRequest.PasteBin;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class OnlinePasteBin implements PasteBin{

    private final String PASTEBIN_API_KEY = "0zzuItQoL8vNfWIg-zzaVlN68Brccmoz";
    public static String curReport = "";

    @Override
    public String SendReport(String res) {
        return callPasteBin(res);
    }

    @Override
    public String SendLongReport(String res) {
        return callPasteBin(res);
    }

    //call the PasteBin Api
    private String callPasteBin(String report) {
        HttpResponse<String> response = Unirest.post("https://pastebin.com/api/api_post.php")
                .multiPartContent()
                .field("api_dev_key", PASTEBIN_API_KEY)
                .field("api_option", "paste")
                .field("api_paste_code",report)
                .asString();

        curReport = response.getBody();
        return response.getBody();
    }

    public String getCurReport() {return curReport;}
}
