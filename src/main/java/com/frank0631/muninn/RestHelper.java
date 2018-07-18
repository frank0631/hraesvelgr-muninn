package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import com.frank0631.nidhogg.book.BookFormat;
import com.frank0631.nidhogg.customer.Customer;
import com.frank0631.nidhogg.calculator.TOperation;

import retrofit2.*;
import retrofit2.http.*;
import retrofit2.converter.gson.*;
import com.google.gson.*;
import okhttp3.ResponseBody;

import java.util.*;
import com.github.javafaker.*;


public class RestHelper {
    
	private String APIAddress;
	private HuginnService huginnService;

	public RestHelper(String APIAddress) {
		this.APIAddress = APIAddress;
		
		Gson gson = new GsonBuilder()
            .setLenient()
            .create();
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIAddress)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        this.huginnService = retrofit.create(HuginnService.class);
	}

	public String echo(String msg) {
	    String output = null;
	    try {
                        
            Call<Map<String,String>> call = huginnService.echo(msg);
            Response<Map<String,String>> result = call.execute();
            Map<String,String> body = result.body();
            //System.out.println(body.string());
            //System.out.println(result.errorBody().string());
            output = body.get("output");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return output;
    }
    
	public int calc(int num1, int num2, String op) {
	    int calc = 0;
	    try {
            Call<Map<String,Integer>> call = huginnService.calculate(num1,num2,op);
            Response<Map<String,Integer>> result = call.execute();
            Map<String,Integer> body = result.body();
            //System.out.println(body);
            //System.out.println(result.errorBody().string());
            calc = body.get("result");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return calc;
    }
    
    
    public interface HuginnService {

        @POST("/api/echo/")
        public Call<Map<String,String>> echo(@Query("input") String message);

        @POST("/api/calculator/")
        public Call<Map<String,Integer>> calculate(@Query("num1") int num1, @Query("num2") int num2, @Query("op") String op);

        @GET("/data/customers/")
        public Call<ResponseBody> readCustomers();
        
        @POST("/data/customers/")
        public Call<Customer> createCustomer(@Body Customer customer);
    }

}
