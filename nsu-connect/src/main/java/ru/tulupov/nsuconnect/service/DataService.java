package ru.tulupov.nsuconnect.service;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.helper.SearchSettingHelper;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.helper.SoundHelper;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Command;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.model.Status;
import ru.tulupov.nsuconnect.model.Success;
import ru.tulupov.nsuconnect.model.Uid;
import ru.tulupov.nsuconnect.model.User;
import ru.tulupov.nsuconnect.request.CommandRequest;
import ru.tulupov.nsuconnect.request.Constants;
import ru.tulupov.nsuconnect.request.GetUidRequest;
import ru.tulupov.nsuconnect.request.SendMessageRequest;
import ru.tulupov.nsuconnect.request.SetSearchParametersRequest;
import ru.tulupov.nsuconnect.request.StartSearchRequest;
import ru.tulupov.nsuconnect.request.StartTypingRequest;
import ru.tulupov.nsuconnect.request.StopTypingRequest;


public class DataService extends Service {
    public static final String ACTION_SEND_MESSAGE = "send_message";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_START_TYPING = "start_typing";
    public static final String ACTION_STOP_TYPING = "stop_typing";
    public static final String EXTRA_MESSAGE = "message";
    private static final String TAG = DataService.class.getSimpleName();

    public IBinder onBind(Intent intent) {
        return null;
    }

    private Session session = new Session();

    private RequestQueue queue;
    private Chat chat;
    private User myUser;
    private User anonymousUser;
    private User systemUser;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);


        try {
            chat = HelperFactory.getHelper().getChatDao().getLast();

            SettingsHelper.setChat(getApplicationContext(), chat);
        } catch (SQLException e) {
            Log.e(TAG, "cannot create chat entity", e);
        }


        myUser = new User();
        myUser.setName("Вы");
        myUser.setType(User.TYPE_YOUR);
        anonymousUser = new User();
        anonymousUser.setName("Аноним");
        anonymousUser.setType(User.TYPE_OTHER);

        systemUser = new User();
        systemUser.setName("Система");
        systemUser.setType(User.TYPE_SYSTEM);
        try {
            HelperFactory.getHelper().getUserDao().create(myUser);
            HelperFactory.getHelper().getUserDao().create(anonymousUser);
            HelperFactory.getHelper().getUserDao().create(systemUser);

        } catch (SQLException e) {
            Log.e(TAG, "cannot create chat entity", e);
        }

        getContentResolver().registerContentObserver(ContentUriHelper.getConversationUri(chat.getId()), false, messageContentObserver);
        sendMessage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getContentResolver().unregisterContentObserver(messageContentObserver);
    }

    private ContentObserver messageContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {

            if (sendEnabled) {
                sendMessage();
            } else {
                wasUpdated = true;
            }
        }
    };
    boolean sendEnabled = true;
    boolean wasUpdated = false;

    private void sendMessage() {
        try {
            sendEnabled = false;
            List<Message> messages = HelperFactory.getHelper().getMessageDao().getUnsetMessagesByChat(chat);
            sendMessage(messages);

        } catch (SQLException e) {
            Log.e(TAG, "error", e);
        }
    }

    private void sendMessage(final List<Message> messages) {
        if (messages.isEmpty()) {
            sendEnabled = true;
            if (wasUpdated) {
                wasUpdated = false;

                sendMessage();
            }

            return;
        }


        final Message messageToSend = messages.remove(0);
        SendMessageRequest sendMessageRequest = new SendMessageRequest(session, messageToSend.getMessage(), new Response.Listener<Message>() {
            @Override
            public void onResponse(Message message) {
                Log.e("xxx", message.toString());
                messageToSend.setSentFlag(true);
                try {
                    HelperFactory.getHelper().getMessageDao().update(messageToSend);
                    ContentUriHelper.notifyChange(getApplicationContext(), ContentUriHelper.getConversationUri(chat.getId()));

                } catch (SQLException e) {
                    Log.e(TAG, "error", e);
                }

                sendMessage(messages);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                createErrorListener().onErrorResponse(volleyError);
                sendEnabled = true;
            }
        });
        queue.add(sendMessageRequest);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();

            if (action.equals(ACTION_START_TYPING)) {
                StartTypingRequest startTypingRequest = new StartTypingRequest(session, createErrorListener());
                queue.add(startTypingRequest);
            } else if (action.equals(ACTION_STOP_TYPING)) {
                StopTypingRequest stopTypingRequest = new StopTypingRequest(session, createErrorListener());
                queue.add(stopTypingRequest);
            } else if (action.equals(ACTION_SEND_MESSAGE)) {
                String text = intent.getStringExtra(EXTRA_MESSAGE);

                Message message = new Message();
                message.setMessage(text);
                message.setDate(new Date());
                message.setChat(chat);
                message.setUser(myUser);
                try {
                    HelperFactory.getHelper().getMessageDao().create(message);
                    ContentUriHelper.notifyChange(getApplicationContext(), ContentUriHelper.getConversationUri(chat.getId()));
                } catch (SQLException e) {
                    Log.e(TAG, "cannot create message entity", e);
                }


//                SendMessageRequest sendMessageRequest = new SendMessageRequest(session, text, new Response.Listener<Message>() {
//                    @Override
//                    public void onResponse(Message message) {
//                        Log.e("xxx", message.toString());
//                    }
//                }, createErrorListener());
//                queue.add(sendMessageRequest);
//                Log.e("xxx", "add SendMessageRequest");
            } else if (action.equals(ACTION_LOGIN)) {
                Settings settings = SettingsHelper.getSettings(getApplicationContext());

                session.setSearch(SearchSettingHelper.generate(settings.getSearchParameters()));

                Log.e("xxx", "set settings =" + session.getSearch());
                GetUidRequest getUidRequest = new GetUidRequest(session, createGetUidListener(), createErrorListener());
                queue.add(getUidRequest);

//                SetSearchParametersRequest setSearchParametersRequest = new SetSearchParametersRequest(session, settings.getSearchParameters(), createSearchParametersListener(), createErrorListener());
//                queue.add(setSearchParametersRequest);

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

                processCommand(status);
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

                        // TODO fix commands id, now its ignore
//                        if (command.getIds() != null) {
//                            for (Map.Entry<String, String> entry : command.getIds().entrySet()) {
//                                session.setLastId(String.format("%s:%s", entry.getValue(), entry.getKey()));
//                            }
//                        }

                        session.setLastId(session.getUid().getUid());

                        processCommand(command.getStatus());
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

    private void processCommand(Status status) {
        if (status != null) {


            if (status.getStatus().equals(Constants.STATUS_MESSAGE)) {
                SoundHelper.beep();
                sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));
                Message message = new Message();
                message.setMessage(status.getMsg());
                message.setDate(new Date());
                message.setChat(chat);
                message.setUser(anonymousUser);
                message.setSentFlag(true);
                try {
                    HelperFactory.getHelper().getMessageDao().create(message);
                    ContentUriHelper.notifyChange(getApplicationContext(), ContentUriHelper.getConversationUri(chat.getId()));
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

            if (status.getStatus().equals(Constants.STATUS_WAITING)) {
                SoundHelper.beep();
                writeSystem("Ожидание подключения");
            }
            if (status.getStatus().equals(Constants.STATUS_CONNECTED)) {
                SoundHelper.beep();
                writeSystem("Подключено");
            }
            if (status.getStatus().equals(Constants.STATUS_DISCONNECTED)) {
                SoundHelper.beep();
                writeSystem("Отключено");
                sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_TYPING_STATUS).putExtra(DatabaseConstants.EXTRA_IS_TYPING, false));
            }
        }
    }

    private void writeSystem(String text) {
        Message message = new Message();
        message.setMessage(text);
        message.setDate(new Date());
        message.setChat(chat);
        message.setUser(systemUser);
        message.setSentFlag(true);
        try {
            HelperFactory.getHelper().getMessageDao().create(message);
            ContentUriHelper.notifyChange(getApplicationContext(), ContentUriHelper.getConversationUri(chat.getId()));

        } catch (SQLException e) {
            Log.e(TAG, "cannot create message entity", e);
        }
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
