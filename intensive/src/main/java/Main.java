import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

class Main {
    public static void main(String[] args) throws java.lang.Exception {

        String ACCESS_TOKEN = "q0taxLbntDAAAAAAAAAADjIqYYFyOcnTBH828rxgoVLLDFcJgOjEAFP6Qksv_w-g";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();

        MyThread thread = new MyThread();
        thread.client = new DbxClientV2(config, ACCESS_TOKEN);
        thread.numberOfShots = 5;
        thread.start();
    }
}


