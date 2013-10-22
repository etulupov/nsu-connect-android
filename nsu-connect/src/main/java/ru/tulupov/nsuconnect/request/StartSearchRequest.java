package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Status;


public class StartSearchRequest extends BaseRequest<Status> {
    public StartSearchRequest(Session session, Response.Listener<Status> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, Status.class, listener, errorListener);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_START_SEARCH);
        params.put(Constants.UID, session.getUid().getUid());
        return params;
    }
}
