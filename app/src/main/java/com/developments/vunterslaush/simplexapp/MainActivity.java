package com.developments.vunterslaush.simplexapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText funcionObjetivoText;
    Button simplexButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        funcionObjetivoText = (EditText)findViewById(R.id.editFuncionObjetivo);
        simplexButton = (Button)findViewById(R.id.simplexButton);
        simplexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Ecuacion e = new Ecuacion(funcionObjetivoText.getText().toString());
                    Toast.makeText(getApplicationContext(),"GOOOD!",Toast.LENGTH_SHORT).show();

                }catch (EcuacionNoValida ex)
                {
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
