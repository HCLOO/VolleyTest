package com.example.huangcl.volleytest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    ImageView imageView;
    Button btnString;
    Button btnJson;
    Button btnImage;
    Button btnImageLoader;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    ImageLoader.ImageListener imageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestQueue=Volley.newRequestQueue(MainActivity.this);
    }

    public StringRequest createStringRequest() {
        StringRequest stringRequest=new StringRequest("https://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        textView.setText(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        textView.setText(volleyError.getMessage());
                    }
                });
        return stringRequest;
    }

    public JsonObjectRequest createJsonRequest() {
        JsonObjectRequest jsonRequest=new JsonObjectRequest("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&page=1&limit=20", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        textView.setText(jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        textView.setText(volleyError.getMessage());
                    }
                });
        return jsonRequest;
    }

    public ImageRequest createImageRequest() {
        ImageRequest imageRequest=new ImageRequest("http://d.hiphotos.baidu.com/image/pic/item/bd315c6034a85edff62cdd7045540923dc5475c4.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                },0,0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        textView.setText(volleyError.getMessage());
                    }
                });
        return imageRequest;
    }

    public void imageLoaderLoadNewWorkImage() {
        imageLoader=new ImageLoader(requestQueue,new BitmapCache());
        imageListener=ImageLoader.getImageListener(imageView,0,0);
        imageLoader.get("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd1007fde3f4e7e8b4710b9122f1b.jpg",imageListener);
    }

   public void initView() {
        textView = (TextView)findViewById(R.id.text);
        imageView=(ImageView)findViewById(R.id.image_view);
        btnString=(Button)findViewById(R.id.string_btn);
        btnJson=(Button)findViewById(R.id.json_btn);
        btnImage=(Button)findViewById(R.id.image_btn);
        btnImageLoader=(Button)findViewById(R.id.image_loader);
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnImageLoader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.string_btn:
                requestQueue.add(createStringRequest());
                break;
            case R.id.json_btn:
                requestQueue.add(createJsonRequest());
                break;
            case R.id.image_btn:
                requestQueue.add(createImageRequest());
                break;
            case R.id.image_loader:
                imageLoaderLoadNewWorkImage();
                break;
        }
    }
}
