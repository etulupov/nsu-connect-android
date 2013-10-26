package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.RequestSession;

public class SendMessageRequest extends BaseRequest<Message> {
    private String message;
    public SendMessageRequest(RequestSession session, String message, Response.Listener<Message> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, Message.class, listener, errorListener);
        this.message=message;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_SEND_MESSAGE);
        params.put(Constants.UID, session.getUid().getUid());
        params.put(Constants.MESSAGE, message);
        return params;
    }
}
