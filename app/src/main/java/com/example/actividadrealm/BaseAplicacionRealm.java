package com.example.actividadrealm;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseAplicacionRealm extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        //----------------------------------------------------------------------------------------------
        //VERSION 1
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(1)
                .migration(new MyMigration())
                .build();

        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);

        //----------------------------------------------------------------------------------------------
        //VERSION 0
        //RealmConfiguration config= new RealmConfiguration.Builder()
        //.name("myrealm.realm")
        //.schemaVersion(0)
        //.build();
        //Realm.setDefaultConfiguration(config);
        //----------------------------------------------------------------------------------------------
    }
}
