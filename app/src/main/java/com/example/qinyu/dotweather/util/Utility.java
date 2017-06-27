package com.example.qinyu.dotweather.util;

import android.text.TextUtils;

import com.example.qinyu.dotweather.db.DotWeatherDB;
import com.example.qinyu.dotweather.model.City;
import com.example.qinyu.dotweather.model.County;
import com.example.qinyu.dotweather.model.Province;

/**
 * Created by qinyu on 2017/6/26.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(DotWeatherDB dotWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    dotWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCitiesResponse(DotWeatherDB dotWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvince_id(provinceId);
                    dotWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountiesResponse(DotWeatherDB dotWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCity_id(cityId);
                    dotWeatherDB.saveCounty(county);
                }
                return true;
            }
         }
        return false;
    }

    
}
