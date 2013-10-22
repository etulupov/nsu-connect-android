package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.adapter.MessageAdapter;
import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.database.loader.MessageLoader;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.util.adapter.AdapterLoaderCallback;


public class ChatFragment extends Fragment {
    public static ChatFragment newInstance(final Context context) {
        return (ChatFragment) ChatFragment.instantiate(context, ChatFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {

            getChildFragmentManager().beginTransaction()
                    .add(R.id.message_container, MessagesFragment.newInstance(getActivity()))
                    .commit();
        }


        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setMessage("fff " + System.currentTimeMillis());
                try {
                    HelperFactory.getHelper().getMessageDao().create(message);

                    getActivity().sendBroadcast(new Intent(DatabaseConstants.ACTION_UPDATE_MESSAGE_LIST));
                } catch (SQLException e) {
                    Log.e("xxx", e.getLocalizedMessage());
                }
            }
        });
    }
}
