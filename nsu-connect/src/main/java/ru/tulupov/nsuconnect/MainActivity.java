package ru.tulupov.nsuconnect;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.sql.SQLException;

import ru.tulupov.nsuconnect.database.DatabaseHelper;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.fragment.ChatFragment;
import ru.tulupov.nsuconnect.fragment.MessagesFragment;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.model.Uid;
import ru.tulupov.nsuconnect.service.DataService;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ChatFragment.newInstance(getApplicationContext()))
                    .commit();
        }

//        Message message = new Message();
//        message.setMessage("fff");
//        try {
//            HelperFactory.getHelper().getMessageDao().create(message);
//
//
//        } catch (SQLException e) {
//             Log.e("xxx", e.getLocalizedMessage());
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final EditText edit = (EditText) view.findViewById(R.id.edit);
            view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startService(new Intent(getActivity(), DataService.class).setAction(DataService.ACTION_SEND_MESSAGE).putExtra(DataService.EXTRA_MESSAGE, edit.getText().toString()));

                }
            });
            view.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startService(new Intent(getActivity(), DataService.class).setAction(DataService.ACTION_LOGIN));
                }
            });
        }
    }

}
