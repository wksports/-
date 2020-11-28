package com.example.zhloginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        gan();
        gan1();
        gan2();

    }
    public void initview(){
        text1 =findViewById(R.id.text1);
        text1.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

public void gan() {
    button1 = findViewById(R.id.button1);
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "欢迎", Toast.LENGTH_SHORT).show();
        }
    });
}
    public void gan1(){
        text2=findViewById(R.id.text2);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "即将跳转到第三方应用", Toast.LENGTH_SHORT).show();
            }
        });

}
    public void gan2() {
        text3 = findViewById(R.id.text3);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "即将跳转到第三方应用", Toast.LENGTH_SHORT).show();
            }
        });
    }

}