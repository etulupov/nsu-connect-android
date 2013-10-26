package ru.tulupov.nsuconnect.activity;

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

import java.sql.SQLException;
import java.util.Date;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.fragment.BaseFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.service.DataService;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BaseFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chat chat = new Chat();
        chat.setName("test");
        chat.setDate(new Date());
        try {
            HelperFactory.getHelper().getChatDao().create(chat);

            SettingsHelper.setChat(getApplicationContext(), chat);
        } catch (SQLException e) {
            Log.e(TAG, "cannot create chat entity", e);
        }


        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
            baseFragment = WelcomeFragment.newInstance(getApplicationContext());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, baseFragment)
                    .commit();
        }


    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!baseFragment.onBackPressed()) {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
//                    getActivity().startService(new Intent(getActivity(), DataService.class).setAction(DataService.ACTION_LOGIN));
                }
            });
        }
    }

}
