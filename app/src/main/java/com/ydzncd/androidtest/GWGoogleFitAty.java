package com.ydzncd.androidtest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
                .setDataType(DataType.TYPE_NUTRITION)  //营养
        .build();
        DataPoint banana = DataPoint.create(nutritionSource);
        banana.setTimestamp(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        banana.getValue(Field.FIELD_FOOD_ITEM).setString("banana");
        banana.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_SNACK);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_FAT, 0.4f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SODIUM, 1f);
        banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_POTASSIUM, 422f);
    }

    private void setWeightToGoogleFit(){
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(this)
                        .setDataType(DataType.TYPE_WEIGHT)
                        .setStreamName("")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        // Create a data set
        // 创建数据集
        DataSet dataSet = DataSet.create(dataSource);
        // For each data point, specify a start time, end time, and the data value -- in this case,
        // the number of new steps.
        // 对于每个数据点，指定开始时间、结束时间和数据值——在这种情况下，是新步骤的数量。
        Float tWeight = 75.5f;
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(0, 100, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_WEIGHT).setFloat(tWeight);
        dataSet.add(dataPoint);

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .insertData(dataSet)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // 此时，数据已经插入并可以读取。
                                    Log.i("qob", "Data insert was successful!(数据插入成功！)");
                                } else {
                                    Log.e("qob", "There was a problem inserting the dataset.(插入数据集时出现问题)", task.getException());
                                }
                            }
                        });
    }
}
