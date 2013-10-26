package ru.tulupov.nsuconnect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ru.tulupov.nsuconnect.model.Status;
import ru.tulupov.nsuconnect.protocol.Session;
import ru.tulupov.nsuconnect.protocol.SessionManager;


public class DataService extends Service {
    public static final String ACTION_SEND_MESSAGE = "send_message";

    public static final String ACTION_START_TYPING = "start_typing";
    public static final String ACTION_STOP_TYPING = "stop_typing";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_FILE = "file";
    private static final String TAG = DataService.class.getSimpleName();
    public static final String EXTRA_ID = "id";
    public static final String ACTION_CREATE_SESSION = "create_session";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sessionManager.onDestroy();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();


            int id = intent.getIntExtra(EXTRA_ID, -1);
            if (id != -1) {

                if (action.equals(ACTION_CREATE_SESSION)) {
                    sessionManager.init(id);
                } else {
                    Session session = sessionManager.get(id);
                    if (session != null) {
                        Status status = new Status();
                        status.setStatus(action);
                        status.setUrl(intent.getStringExtra(EXTRA_FILE));
                        status.setMsg(intent.getStringExtra(EXTRA_MESSAGE));
                        session.processCommand(status);
                    }
                }
            }


        }
        return super.onStartCommand(intent, flags, startId);
    }


}
