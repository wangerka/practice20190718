package com.example.myapplication;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private TextView text;
//    Button button1;
//    Button button2;
//    Button button3;
//    ImageView image,image1;
//    ProgressBar bar;
//
//    RequestQueue queue;
//    StringRequest request;
//    ImageRequest imageRequest;
//    ImageLoader loader;
//    ImageLoader.ImageListener listener;
//
//    String url1 = "https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg";
//    String url2 = "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg";
//
//    int i =0;
    int screenWidth;

    RecyclerView photosRecyclerView;
    List<String> photoUrls = new ArrayList<>();
    String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        photosRecyclerView = findViewById(R.id.photos_recyclerivew);
        photosRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0;i<array.length();i++){
                                JSONObject o = array.getJSONObject(i);
                                String url = o.getString("url");
                                Log.i("gejun",""+url);
                                photoUrls.add(url);
                            }
                            photosRecyclerView.setAdapter(new PhotosAdapter(photoUrls));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(request);

//        text = findViewById(R.id.text);
//        button1 = findViewById(R.id.btn1);
//        button1.setOnClickListener(this);
//        button2 = findViewById(R.id.btn2);
//        button3 = findViewById(R.id.btn3);
//        button2.setOnClickListener(this);
//        button3.setOnClickListener(this);
//        image = findViewById(R.id.image);
//        image1 = findViewById(R.id.image1);
//        bar = findViewById(R.id.progress);
//
        screenWidth = getResources().getDisplayMetrics().widthPixels;
//        text.setText(screenWidth+"");
//
//
//        Glide.with(this)
//                .load(url1)
//                .override(screenWidth/2,SIZE_ORIGINAL)
//                .into(image);
//
//        Glide.with(this)
//                .load(url2)
//                .override(screenWidth/2,SIZE_ORIGINAL)
//                .into(image1);
//
//
//        String url = "https://www.baidu.com";
//        request = new StringRequest(url,
//                new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                text.setText(response);
//                showResult(false);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                text.setText(error.getMessage());
//                showResult(false);
//            }
//        });
//
//
//        imageRequest = new ImageRequest(
//                url1,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        showResult(true);
//                        image.setImageBitmap(response);
//                    }
//                },
//                0,
//                0,
//                ImageView.ScaleType.CENTER,
//                Bitmap.Config.RGB_565,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        showResult(false);
//                        text.setText(error.getMessage());
//                    }
//                });
//
//        loader = new ImageLoader(queue, new ImageLoader.ImageCache() {
//            @Override
//            public Bitmap getBitmap(String url) {
//                return null;
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//
//            }
//        });
//
//        listener = ImageLoader.getImageListener(image,
//                R.drawable.ic_launcher_background,
//                R.drawable.ic_launcher_foreground);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.i("gejun","count " + i++);
//                }
//            }
//        }).start();
    }

    class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosVH>{

        class PhotosVH extends RecyclerView.ViewHolder{
            ImageView p;
            public PhotosVH(@NonNull View itemView) {
                super(itemView);

                p = itemView.findViewById(R.id.photo);
            }
        }

        List<String> urls;

        public PhotosAdapter(List<String> urls) {
            this.urls = urls;
        }

        @NonNull
        @Override
        public PhotosVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.photo_item, null);
            return new PhotosVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotosVH viewHolder, int i) {
            Glide.with(MainActivity.this)
                    .load(urls.get(i))
                    .override(screenWidth/2,SIZE_ORIGINAL)
                    .into(viewHolder.p);
        }

        @Override
        public int getItemCount() {
            return urls.size();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
//        switch (id){
//            case R.id.btn1:
//                showProgressBar();
//                queue.add(request);
//                break;
//            case R.id.btn2:
//                showProgressBar();
//                queue.add(imageRequest);
//                break;
//            case R.id.btn3:
//                loader.get(url2, listener);
//                break;
//        }
    }

//    private void showProgressBar(){
//        bar.setVisibility(View.VISIBLE);
//        image.setVisibility(View.GONE);
//        text.setVisibility(View.GONE);
//    }
//
//    private void showResult(boolean showImage){
//        bar.setVisibility(View.GONE);
//        image.setVisibility(showImage ? View.VISIBLE : View.GONE);
//        text.setVisibility(showImage ? View.GONE : View.VISIBLE);
//    }
}
