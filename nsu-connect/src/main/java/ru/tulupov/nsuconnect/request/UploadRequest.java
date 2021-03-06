package ru.tulupov.nsuconnect.request;

import ru.tulupov.nsuconnect.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import ru.tulupov.nsuconnect.model.RequestSession;
import ru.tulupov.nsuconnect.model.Status;

public class UploadRequest extends BaseRequest<Status> {


    private static final String TAG = UploadRequest.class.getSimpleName();
    private MultipartEntity entity;

    public UploadRequest(RequestSession session, String file, Response.Listener<Status> listener, Response.ErrorListener errorListener) {
        super(Method.POST,
                Config.AJAX_ENDPOINT.buildUpon()
                        .appendQueryParameter(Constants.ACTION, Constants.ACTION_UPLOAD)
                        .appendQueryParameter(Constants.UID, session.getUid().getUid())
                        .build(),
                session,
                Status.class,
                listener,
                errorListener);

        entity = new MultipartEntity();
        entity.addPart(Constants.IMAGE_FILE, new FileBody(new File(file), "image/jpeg"));
    }




    @Override
    public String getBodyContentType() {
        Log.e("xxx", entity.getContentType().getValue());
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            Log.e(TAG, "upload error",e);

        }

        return bos.toByteArray();
    }


}
