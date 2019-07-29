package com.example.topicos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class configuracoes extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText numero = findViewById(R.id.numero);

        final CheckBox checkBox = findViewById(R.id.checkPoints);

        Button cadastrar = findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    MainActivity.contar = false;
                }
                else{
                    if (TextUtils.isEmpty(numero.getText().toString())){
                        numero.setError("Por favor entre um numero de contagem");
                        numero.requestFocus();
                        return;
                    }
                    else{
                        MainActivity.count = Integer.parseInt(numero.getText().toString());
                        MainActivity.contar = true;
                    }
                }
                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        finish();
        return true;
    }
}
