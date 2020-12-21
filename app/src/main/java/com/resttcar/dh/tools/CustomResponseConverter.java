package com.resttcar.dh.tools;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.resttcar.dh.entity.ApiResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
 
    private final Gson gson;
    private TypeAdapter<T> adapter;
    private Type mType;
 
 
    CustomResponseConverter(Gson gson, TypeAdapter<T> mAdapter, Type mType) {
        this.gson = gson;
        this.adapter = mAdapter;
        this.mType = mType;
 
    }
 
    @Override
    public T convert(ResponseBody value) throws IOException {
 
        ApiResponse response = new ApiResponse();
        try {
            String body = value.string();
 
            JSONObject json = new JSONObject(body);
 
            int ret = json.optInt("code");
            String msg = json.optString("msg", "");
 
            if (ret == 1) {
                return gson.fromJson(body, mType);
            } else {
                response.setCode(ret);
                response.setMsg(msg);
                response.setData(json.opt("data"));
                return (T) response;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}