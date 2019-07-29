package com.example.topicos;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static int count = 1;
    public static boolean contar = false;

    public ConnectionThread connect;
    public static TextView statusTest;

    ListView listaMovimentos;
    ImageView frente, tras, esq, dir;
    String actions;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        final TextView contador = findViewById(R.id.textContador);
        final TextView contador2 = findViewById(R.id.textContador2);

        if(contar){
            contador.setVisibility(View.VISIBLE);
            contador2.setVisibility(View.VISIBLE);
            contador.setText(count);
        }
        else{
            contador.setVisibility(View.INVISIBLE);
            contador2.setVisibility(View.INVISIBLE);
        }

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        statusTest = findViewById(R.id.debug);

        listaMovimentos = findViewById(R.id.listaMovimentos);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaMovimentos.setAdapter(adapter);

        frente = findViewById(R.id.setaFrente);
        tras = findViewById(R.id.setaTras);
        esq = findViewById(R.id.setaEsq);
        dir = findViewById(R.id.setaDir);

        Button execute = findViewById(R.id.execute);
        Button undo = findViewById(R.id.undo);

        actions = "";

        frente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0 && contar){
                    Toast.makeText(MainActivity.this, "Limite de Acoes Atingido", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(contar){
                    count--;
                    contador.setText(String.valueOf(count));
                }
                actions += 'F';
                adapter.add("Frente");
                //Toast.makeText(MainActivity.this, "Frente", Toast.LENGTH_LONG).show();
            }
        });

        tras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0 && contar){
                    Toast.makeText(MainActivity.this, "Limite de Acoes Atingido", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(contar){
                    count--;
                    contador.setText(String.valueOf(count));
                }
                actions += 'T';
                adapter.add("Tras");
                Toast.makeText(MainActivity.this, "Tras", Toast.LENGTH_LONG).show();
            }
        });

        esq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0 && contar){
                    Toast.makeText(MainActivity.this, "Limite de Acoes Atingido", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(contar){
                    count--;
                    contador.setText(String.valueOf(count));
                }

                actions += 'E';
                adapter.add("Esquerda");
                Toast.makeText(MainActivity.this, "Esquerda", Toast.LENGTH_LONG).show();
            }
        });

        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0 && contar){
                    Toast.makeText(MainActivity.this, "Limite de Acoes Atingido", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(contar){
                    count--;
                    contador.setText(String.valueOf(count));
                }

                actions += 'D';
                adapter.add("Direita");
                Toast.makeText(MainActivity.this, "Direita", Toast.LENGTH_LONG).show();
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, actions, Toast.LENGTH_LONG).show();
                try{
                    connect.write(actions.getBytes());
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Nenhuma conexão encontrada", Toast.LENGTH_LONG).show();
                }
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCount() > 0){
                    adapter.remove(adapter.getItem(adapter.getCount() - 1));
                    actions = actions.substring(0, actions.length() - 1);
                    count++;
                    contador.setText(String.valueOf(count));
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView statusTest = findViewById(R.id.debug);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                statusTest.setText("Bluetooth enabled");
            }
            else {
                statusTest.setText("Bluetooth not enabled");
            }
        }
        if(requestCode == 2 || requestCode == 3) {
            if(resultCode == RESULT_OK) {
                statusTest.setText("You`ve selected " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));

                connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
                connect.start();
                //connect.write("Recebendo info".getBytes());
            }
            else {
                statusTest.setText("None of devices was selected");
            }
        }
        if(requestCode == 4){

            TextView contador = findViewById(R.id.textContador);
            TextView contador2 = findViewById(R.id.textContador2);

            if(contar){
                contador.setVisibility(View.VISIBLE);
                contador2.setVisibility(View.VISIBLE);
                contador.setText(String.valueOf(count));
            }
            else{
                contador.setVisibility(View.INVISIBLE);
                contador2.setVisibility(View.INVISIBLE);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            if(dataString.equals("---N"))
                statusTest.setText("Ocorreu um erro durante a conexão D:");
            else if(dataString.equals("---S"))
                statusTest.setText("Conectado :D");
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.paired) {
            Intent searchPairedDevicesIntent = new Intent(MainActivity.this, PairedDevices.class);
            startActivityForResult(searchPairedDevicesIntent, 2);
        } else if (id == R.id.dicovered) {
            Intent discoverDevices = new Intent(MainActivity.this, DiscoveredDevices.class);
            startActivityForResult(discoverDevices, 3);
        } else if(id == R.id.settings){
            Intent settings = new Intent(MainActivity.this, configuracoes.class);
            startActivityForResult(settings, 4);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


