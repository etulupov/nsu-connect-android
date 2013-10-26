package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.RequestSession;

public class DisconnectRequest extends BaseRequest {
    public DisconnectRequest(RequestSession session, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, null, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_DISCONNECT);
        params.put(Constants.UID, session.getUid().getUid());
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        // ignore
    }
}
