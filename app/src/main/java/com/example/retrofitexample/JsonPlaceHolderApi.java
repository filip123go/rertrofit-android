package com.example.retrofitexample;

import com.example.retrofitexample.model.Comment;
import com.example.retrofitexample.model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {


//baseUrl("https://jsonplaceholder.typicode.com/")


    //pass queryParams
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(
            @QueryMap Map<String , String> parameters
            );

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId); //pass pathParam id

    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
