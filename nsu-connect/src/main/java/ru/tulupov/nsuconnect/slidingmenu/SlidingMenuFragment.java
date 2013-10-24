package ru.tulupov.nsuconnect.slidingmenu;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import ru.tulupov.nsuconnect.R;


public class SlidingMenuFragment extends ListFragment {

    private SlidingMenuAdapter adapter;

    public interface OnItemClickListener {
        void onClick(int id);
    }

    private OnItemClickListener onItemClickListener;
    private List<SlidingMenuItem> menuItems;

    public void setOnItemClickListener(SlidingMenuFragment.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new SlidingMenuAdapter();
        setListAdapter(adapter);

        parseXml(R.menu.main);
        adapter.updateList(menuItems);
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(menuItems.get(position).id);
        }
    }




    private void parseXml(int menu) {

        menuItems = new ArrayList<SlidingMenuItem>();

        try {
            XmlResourceParser xpp = getResources().getXml(menu);

            xpp.next();
            int eventType = xpp.getEventType();

            boolean isChild = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    String elemName = xpp.getName();

                    if (elemName.equals("item")) {
                        String textId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
                        String iconId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "icon");
                        String resId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "id");


                        SlidingMenuItem item = new SlidingMenuItem();
                        if (!TextUtils.isEmpty(resId))
                            item.id = Integer.valueOf(resId.replace("@", ""));

                        item.text = resourceIdToString(textId);
                        item.icon = Integer.valueOf(iconId.replace("@", ""));

                        menuItems.add(item);
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String resourceIdToString(String text) {

        if (!text.contains("@")) {
            return text;
        } else {

            String id = text.replace("@", "");

            return getResources().getString(Integer.valueOf(id));

        }

    }


}
