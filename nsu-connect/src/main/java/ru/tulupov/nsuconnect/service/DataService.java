package ru.tulupov.nsuconnect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Command;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.model.Status;
import ru.tulupov.nsuconnect.model.Uid;
import ru.tulupov.nsuconnect.request.CommandRequest;
import ru.tulupov.nsuconnect.request.Constants;
import ru.tulupov.nsuconnect.request.GetUidRequest;
import ru.tulupov.nsuconnect.request.SendMessageRequest;
import ru.tulupov.nsuconnect.request.StartSearchRequest;


public class DataService extends Service {
    public static final String ACTION_SEND_MESSAGE = "send_message";
    public static final String ACTION_LOGIN = "login";
    public static final String EXTRA_MESSAGE = "message";
    private static final String TAG = DataService.class.getSimpleName();

    public IBinder onBind(Intent intent) {
        return null;
    }

    private Session session = new Session();

    private RequestQueue queue;
    private Chat chat;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);

        session.setSearch("nsu_department=0; search_nsu_department=0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0; age=0; gender=1; search_age=0%2C0%2C0%2C0%2C0; search_gender=0%2C0;");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(ACTION_SEND_MESSAGE)) {
            String text = intent.getStringExtra(EXTRA_MESSAGE);
//
            Message message = new Message();
            message.setMessage(text);
            message.setDate(new Date());
            message.setChat(chat);
            try {
                HelperFactory.getHelper().getMessageDao().create(message);
                sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_MESSAGE_LIST));

            } catch (SQLException e) {
                Log.e(TAG, "cannot create message entity", e);
            }


            SendMessageRequest sendMessageRequest = new SendMessageRequest(session, text, new Response.Listener<Message>() {
                @Override
                public void onResponse(Message message) {
                    Log.e("xxx", message.toString());
                }
            }, createErrorListener());
            queue.add(sendMessageRequest);
            Log.e("xxx", "add SendMessageRequest");
        } else if (action.equals(ACTION_LOGIN)) {

            GetUidRequest getUidRequest = new GetUidRequest(session, createGetUidListener(), createErrorListener());
            queue.add(getUidRequest);

            chat = new Chat();
            chat.setName("test");
            chat.setDate(new Date());
            try {
                HelperFactory.getHelper().getChatDao().create(chat);

                SettingsHelper.setChat(getApplicationContext(), chat);
            } catch (SQLException e) {
                Log.e(TAG, "cannot create chat entity", e);
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Response.Listener<Uid> createGetUidListener() {
        return new Response.Listener<Uid>() {
            @Override
            public void onResponse(Uid uid) {
                session.setUid(uid);
                Log.e("xxx", uid.getUid());
                StartSearchRequest startSearchRequest = new StartSearchRequest(session, createStartSearchListener(), createErrorListener());
                queue.add(startSearchRequest);
            }
        };
    }

    private Response.Listener<Status> createStartSearchListener() {
        return new Response.Listener<Status>() {
            @Override
            public void onResponse(Status status) {
                session.setLastId(session.getUid().getUid());
                Log.e("xxx", status.toString());
                queryNextCommand();
            }


        };
    }

    private Response.Listener<Command[]> createCommandListener() {
        return new Response.Listener<Command[]>() {
            @Override
            public void onResponse(Command[] commands) {
                if (commands != null) {
                    for (Command command : commands) {
                        if (command.getIds() != null) {
                            for (Map.Entry<String, String> entry : command.getIds().entrySet()) {
                                session.setLastId(String.format("%s:%s", entry.getValue(), entry.getKey()));
                            }
                        }
                        if (command.getStatus() != null) {
                            Status status = command.getStatus();

                            if (status.getStatus().equals(Constants.STATUS_CONNECTED)) {
//                                SendMessageRequest sendMessageRequest = new SendMessageRequest(session, "Привет :)", new Response.Listener<Message>() {
//                                    @Override
//                                    public void onResponse(Message message) {
//                                        Log.e("xxx", message.toString());
//                                    }
//                                }, createErrorListener());
//                                queue.add(sendMessageRequest);
                                Log.e("xxx", "add SendMessageRequest");
                            }

                            if (status.getStatus().equals(Constants.STATUS_MESSAGE)) {
                                Message message = new Message();
                                message.setMessage(status.getMsg());
                                message.setDate(new Date());
                                message.setChat(chat);
                                try {
                                    HelperFactory.getHelper().getMessageDao().create(message);
                                    sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_MESSAGE_LIST));

                                } catch (SQLException e) {
                                    Log.e(TAG, "cannot create message entity", e);
                                }
                            }

                            if (status.getStatus().equals(Constants.STATUS_START_TYPING)) {
                                sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, true));
                            }
                            if (status.getStatus().equals(Constants.STATUS_STOP_TYPING)) {
                                sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));
                            }

                        }
                        Log.e("xxx", "status=" + command.getStatus().toString());
                        queryNextCommand();
                    }
                } else {
                    queryNextCommand();
                }
            }
        }

                ;
    }

    private void queryNextCommand() {
        CommandRequest commandRequest = new CommandRequest(session, createCommandListener(), createCommandErrorListener());
        queue.add(commandRequest);
        Log.e("xxx", "add CommandRequest");
    }

    private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("xxx", volleyError.toString());
            }
        };
    }

    private Response.ErrorListener createCommandErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("xxx", volleyError.toString());
                if (volleyError.networkResponse != null && volleyError.networkResponse.statusCode == 502) {
                    session.setLastId(session.getUid().getUid());
                    queryNextCommand();
                }

            }
        };
    }
}
