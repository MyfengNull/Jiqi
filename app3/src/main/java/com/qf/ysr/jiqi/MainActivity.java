package com.qf.ysr.jiqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener, View.OnClickListener {
    private HttpData httpData;
    private List<ListData> list;
    private EditText etSend;
    private Button btSend;
    private ListView lv;
    private MyAdapter adapter;
    private String content_str;
    private String[] welcomearray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        etSend = (EditText) findViewById(R.id.sendText);
        btSend = (Button) findViewById(R.id.btSend);
        list = new ArrayList<>();
        btSend.setOnClickListener(this);
        adapter = new MyAdapter(list, this);
        lv.setAdapter(adapter);
        ListData listData;
        listData = new ListData(getRandomWelcomTips(), ListData.RECEIVE);
        list.add(listData);
    }

    private String getRandomWelcomTips() {
        String welcome_tip = null;
        welcomearray = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * (welcomearray.length - 1));
        welcome_tip = welcomearray[index];
        return welcome_tip;
    }

    @Override
    public void getDataUrl(String data) {
        paraseText(data);
    }

    public void paraseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
            String code = jb.getString("code");

            //你漂亮么
            if (code.equals("100000")) {
                ListData listData;
                listData = new ListData(jb.getString("text"), ListData.RECEIVE);
                list.add(listData);
                adapter.notifyDataSetChanged();
            }

            //机器人的图片
            if (code.equals("200000")) {
                ListData listData;
                listData = new ListData(jb.getString("text") + "\n" + jb.getString("url"), ListData.RECEIVE);
                list.add(listData);
                adapter.notifyDataSetChanged();
            }

            //体育新闻
            if (code.equals("302000")) {

                JSONArray list1 = jb.getJSONArray("list");

//                for (int i = 0; i < list1.length(); i++) {
//                    JSONObject jsonObject = list1.getJSONObject(i);
//                    String article = jsonObject.getString("article");
//                    String source = jsonObject.getString("source");
//                    String icon = jsonObject.getString("icon");
//                    String detailurl = jsonObject.getString("article");
//                }

                JSONObject jsonObject = list1.getJSONObject(0);
                String article = jsonObject.getString("article");
                String source = jsonObject.getString("source");
                String icon = jsonObject.getString("icon");
                String detailurl = jsonObject.getString("detailurl");
                ListData listData;
                listData = new ListData(jb.getString("text") + "\n" + article + "\n" + source + "\n" + icon + "\n" + detailurl, ListData.RECEIVE);
                list.add(listData);
                adapter.notifyDataSetChanged();
            }
            //北京到拉萨的火车
            if (code.equals("305000")) {


                JSONArray list1 = jb.getJSONArray("list");
                for (int i = 0; i < list1.length(); i++) {
                    JSONObject jsonObject = list1.getJSONObject(i);
                    String trainnum = jsonObject.getString("trainnum");
                    String start = jsonObject.getString("start");
                    String terminal = jsonObject.getString("terminal");
                    String starttime = jsonObject.getString("starttime");
                    String endtime = jsonObject.getString("endtime");
                    String icon = jsonObject.getString("icon");
                    String detailurl = jsonObject.getString("detailurl");

                }


                ListData listData;
                listData = new ListData(jb.getString("text"), ListData.RECEIVE);
                list.add(listData);
                adapter.notifyDataSetChanged();
            }


            //红烧肉怎么做
            if (code.equals("308000")) {
                JSONArray list1 = jb.getJSONArray("list");
                JSONObject jsonObject = list1.getJSONObject(0);
                String name = jsonObject.getString("name");
                String icon = jsonObject.getString("icon");
                String info = jsonObject.getString("info");
                String detailurl = jsonObject.getString("detailurl");


                ListData listData;
                listData = new ListData(jb.getString("text") + "\n" + name + "\n" + icon + "\n" + info + "\n" + detailurl, ListData.RECEIVE);
                list.add(listData);
                adapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        content_str = etSend.getText().toString();
        etSend.setText("");
        String dropk = content_str.replace(" ", "");
        String droph = dropk.replace("\n", "");
        ListData listData;
        listData = new ListData(content_str, ListData.SEND);
        list.add(listData);
        if (list.size() > 30) {
            for (int i = 0; i < list.size(); i++) {
                list.remove(i);

            }
        }
        adapter.notifyDataSetChanged();

        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=0fa840e0c49734eae7e260a911856afd&info=" + droph, this).execute();

    }
}
