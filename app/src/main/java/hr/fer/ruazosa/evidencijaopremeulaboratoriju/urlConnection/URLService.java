package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Dorotea on 07/07/2017.
 */

public class URLService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        JavaHttpUrlConnectionReader r = new JavaHttpUrlConnectionReader();

        return null;
    }




    protected void onHandleIntent(Intent request) {
        JavaHttpUrlConnectionReader r= new JavaHttpUrlConnectionReader();
    }
}
