package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Command;
import ru.tulupov.nsuconnect.model.Session;

public class CommandRequest extends BaseRequest<Command[]> {
    public CommandRequest(Session session, Response.Listener<Command[]> listener, Response.ErrorListener errorListener) {
        super(Method.POST,
                Config.MSG_ENDPOINT
                        .buildUpon()
                        .appendQueryParameter(Constants.IDENTIFIER, session.getLastId())
                        .build(),
                session,
                Command[].class,
                listener,
                errorListener);
        setRetryPolicy(new DefaultRetryPolicy(61000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("Referer", "http://msg.inctalk.net/?identifier=IFRAME&HOST=inctalk.net&version=1.32");
        return headers;
    }
}
