package ru.tulupov.nsuconnect.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Provider;
import ru.tulupov.nsuconnect.model.ProviderResult;

public class GetProviderRequest extends StringRequest {
    public GetProviderRequest(final Response.Listener<ProviderResult> listener, Response.ErrorListener errorListener) {
        super(Config.PROVIDERS_ENDPOINT.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Provider>>() {
                }.getType();

                if (listener != null) {
                    Map<String, Provider> map = gson.fromJson(response, type);
                    ProviderResult result = new ProviderResult();
                    result.setProviders(new ArrayList<Provider>(map.values()));
                    listener.onResponse(result);
                }

            }
        }, errorListener);
    }


}
