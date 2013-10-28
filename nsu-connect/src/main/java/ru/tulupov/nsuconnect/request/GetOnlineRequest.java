package ru.tulupov.nsuconnect.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Online;
import ru.tulupov.nsuconnect.model.RequestSession;

public class GetOnlineRequest extends BaseRequest<Online> {
    public GetOnlineRequest( Response.Listener<Online> listener) {
        super(Method.POST, Config.AJAX_ENDPOINT, null, Online.class, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ACTION, Constants.ACTION_GET_ONLINE);
        return params;
    }
}
