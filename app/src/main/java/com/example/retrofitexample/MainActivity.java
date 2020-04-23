package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofitexample.model.Comment;
import com.example.retrofitexample.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        textViewResult.setText("Loading...");
//        getPosts();
//        getComments();
        createPost();
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                assert posts != null;
                textViewResult.setText("");
                for (Post post : posts) {
                    textViewResult.append("\n");
                    textViewResult.append("id :" + post.getId() + "\n");
                    textViewResult.append("UserId :" + post.getUserId() + "\n");
                    textViewResult.append("Title :" + post.getTitle() + "\n");
                    textViewResult.append("Text :" + post.getText() + "\n");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(1);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                assert comments != null;
                textViewResult.setText("");
                for (Comment comment : comments) {
                    textViewResult.append("\n");
                    textViewResult.append("id :" + comment.getId() + "\n");
                    textViewResult.append("UserId :" + comment.getPostId() + "\n");
                    textViewResult.append("Title :" + comment.getName() + "\n");
                    textViewResult.append("Title :" + comment.getEmail() + "\n");
                    textViewResult.append("Text :" + comment.getText() + "\n");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(23, "new Title", "new text");

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {


            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                    return;
                }

                Post postResponse = response.body();
                textViewResult.setText("");
                textViewResult.append(response.code() + "\n");
                textViewResult.append("\n");
                textViewResult.append("id :" + postResponse.getId() + "\n");
                textViewResult.append("UserId :" + postResponse.getUserId() + "\n");
                textViewResult.append("Title :" + postResponse.getTitle() + "\n");
                textViewResult.append("Text :" + postResponse.getText() + "\n");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
