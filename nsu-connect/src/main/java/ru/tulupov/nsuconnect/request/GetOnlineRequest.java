package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Online;
import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Uid;

public class GetOnlineRequest extends BaseRequest<Online> {
    public GetOnlineRequest(Session session, Response.Listener<Online> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, Online.class, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_GET_ONLINE);
        return params;
    }
}
