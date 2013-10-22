package ru.tulupov.nsuconnect;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zheka on 21.10.13.
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest {
    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>(super.getHeaders());
        headers.put("Cookie", "nsu_department=0; search_nsu_department=0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0; age=0; gender=1; search_age=0%2C0%2C0%2C0%2C0; search_gender=0%2C0;");
        headers.put("Referer", "http://inctalk.net/chat.php?noheader=1&provider=nsu&local=1&basecss=http://nsu-connect.ru/chatcss/base.css&chatcss=http://nsu-connect.ru/chatcss/chat.css&settingscss=http://nsu-connect.ru/chatcss/settings.css");

        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action","get_uid");
        return params;
    }
}
