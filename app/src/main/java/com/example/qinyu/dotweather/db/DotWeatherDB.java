package com.example.qinyu.dotweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;

import com.example.qinyu.dotweather.model.City;
import com.example.qinyu.dotweather.model.County;
import com.example.qinyu.dotweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinyu on 2017/6/25.
 */
public class DotWeatherDB {

    public static final String DB_NAME = "dot_weather";

    public static final int VERSION = 1;
    private static DotWeatherDB dotWeatherDB;
    private SQLiteDatabase db;

    private DotWeatherDB(Context context) {
        DotWeatherOpenHelper dbHelper = new DotWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static DotWeatherDB getInstance(Context context) {
        if (dotWeatherDB == null) {
            dotWeatherDB = new DotWeatherDB(context);
        }
        return dotWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues cv = new ContentValues();
            cv.put("province_name", province.getProvinceName());
            cv.put("province_code", province.getProvinceCode());
            db.insert("Province", null, cv);
        }
    }

    public List<Province> loadProvinces() {
        List<Province> listProv = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                listProv.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return listProv;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues cv = new ContentValues();
            cv.put("city_name", city.getCityName());
            cv.put("city_code", city.getCityCode());
            cv.put("province_id", city.getProvince_id());
            db.insert("City", null, cv);
        }
    }

    public List<City> loadCities(int provinceID) {
        List<City> listCity = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceID)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getInt(cursor.getColumnIndex("id"))));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvince_id(provinceID);
                listCity.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return listCity;
    }

    public void saveCounty(County county) {
        if (county != null) {
            ContentValues cv = new ContentValues();
            cv.put("county_name", county.getCountyName());
            cv.put("county_code", county.getCountyCode());
            cv.put("city_id", county.getCity_id());
            db.insert("County", null, cv);
        }
    }

    public List<County> loadCounties(int cityId) {
        List<County> listCounty = new ArrayList<County>();
        Cursor cursor = db.query("County", null,"city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToLast()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCity_id(cityId);
                listCounty.add(county);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return listCounty;
    }
}
