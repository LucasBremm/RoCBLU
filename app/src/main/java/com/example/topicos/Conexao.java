package com.example.topicos;

import android.app.Application;

public class Conexao extends Application {

        private ConnectionThread connection;

        public Conexao(String address){
            connection = new ConnectionThread(address);
        }

        public void setConnection(String address){
            connection = new ConnectionThread(address);
        }

        public ConnectionThread getConnection(){
            return connection;
        }

        public void start(){
            connection.start();
        }
}
