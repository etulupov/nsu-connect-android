package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Uid;

public class GetUidRequest extends BaseRequest<Uid> {
    public GetUidRequest( Session session, Response.Listener<Uid> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, Uid.class, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_GET_UID);
        return params;
    }
}
