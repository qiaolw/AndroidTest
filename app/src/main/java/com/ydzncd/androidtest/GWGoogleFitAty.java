package com.ydzncd.androidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GWGoogleFitAty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwgoogle_fit_aty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.fit_test)
    public void onGoogleFitTestClick(){
        //DataType
        //https://developers.google.com/android/reference/com/google/android/gms/fitness/data/DataType
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.AGGREGATE_WEIGHT_SUMMARY, FitnessOptions.ACCESS_WRITE)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            Log.e("qob", "hasPermissions NO!");
            GoogleSignIn.requestPermissions(
                    this,
                    1,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        }
        else {
            Log.e("qob", "hasPermissions!");

        }
    }

    private void setDataToGoogleFit(){
        DataSource nutritionSource = new DataSource.Builder()
                .setDataType(DataType.TYPE_NUTRITION)
        .build();
        DataPoint banana = DataPoint.create(nutritionSource);
        banana.setTimestamp(now.getMillis(), TimeUnit.MILLISECONDS);
        banana.getValue(Field.FIELD_FOOD_ITEM).setString("banana");
        banana.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_SNACK);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_FAT, 0.4f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SODIUM, 1f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_POTASSIUM, 422f);
    }
}
