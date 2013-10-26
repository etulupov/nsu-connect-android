package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.RequestSession;

public class StartTypingRequest extends BaseRequest {
    public StartTypingRequest(RequestSession session, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, null, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_START_TYPING);
        params.put(Constants.UID, session.getUid().getUid());
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        // ignore
    }
}
