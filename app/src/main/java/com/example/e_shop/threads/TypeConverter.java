package com.example.e_shop.threads;

import com.example.e_shop.model.Product;
import com.google.gson.Gson;

import java.util.Date;
import androidx.room.TypeConverters;

public class TypeConverter {
        @androidx.room.TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }
        @androidx.room.TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    @androidx.room.TypeConverter
    public Product fromJson(String value) {
        return new Gson().fromJson(value, Product.class);
    }

    @androidx.room.TypeConverter
    public String fromProduct(Product product) {
        return new Gson().toJson(product);
    }


}
