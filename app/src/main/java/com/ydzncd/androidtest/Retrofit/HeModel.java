package com.ydzncd.androidtest.Retrofit;

import java.util.ArrayList;

public class HeModel {
    public ArrayList<BasicModel> HeWeather6;

    class BasicModel{
        public NowModel now;
        public String status;

        class NowModel{
            public int cond_code;
            public int tmp;
            public String cond_txt;

            @Override
            public String toString() {
                return "cond_code " + cond_code + " tmp " + tmp + " cond_txt " + cond_txt;
            }
        }

        @Override
        public String toString() {
            return "now " + now + " status " + status;
        }
    }

    @Override
    public String toString() {
        return "HeWeather6 " + HeWeather6;
    }
}
