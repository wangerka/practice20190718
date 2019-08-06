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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnImageReady{
    int screenWidth;

    RecyclerView photosRecyclerView;
    List<String> photoUrls = new ArrayList<>();
    String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


//        Glide.with(this).asBitmap().load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565082845867&di=8febe4285563fe12fa23d2bf0f20636f&imgtype=0&src=http%3A%2F%2F365jia.cn%2Fuploads%2F13%2F0523%2F519e13069d6f1.jpg").into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                Log.d("gejun","width " + width); //900px
//                Log.d("gejun","height " + height); //500px
//            }
//        });

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
                            PhotosAdapter adapter = new PhotosAdapter(photoUrls);
                            adapter.setImageReadyListener(MainActivity.this);
                            photosRecyclerView.setAdapter(adapter);
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

        screenWidth = getResources().getDisplayMetrics().widthPixels;
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
        private OnImageReady l;

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
        public void onBindViewHolder(@NonNull final PhotosVH viewHolder, int i) {
            final String url = urls.get(i);
            Glide.with(MainActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    int w = bitmap.getWidth();
                    int h = bitmap.getHeight();
                    l.setIamge(w,h, viewHolder.p, url);
                }
            });



        }

        @Override
        public int getItemCount() {
            return urls.size();
        }

        public void setImageReadyListener(OnImageReady listener){
            l = listener;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    @Override
    public void setIamge(int w, int h, ImageView iv, String url) {
        Glide.with(MainActivity.this)
                .load(url)
                .override(screenWidth/2,screenWidth*h/w*2)
                .into(iv);
    }
}
