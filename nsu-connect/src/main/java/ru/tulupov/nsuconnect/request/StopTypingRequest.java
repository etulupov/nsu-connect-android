package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Session;

public class StopTypingRequest extends BaseRequest {
    public StopTypingRequest(Session session, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, null, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_STOP_TYPING);
        params.put(Constants.UID, session.getUid().getUid());
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        // ignore
    }
}
