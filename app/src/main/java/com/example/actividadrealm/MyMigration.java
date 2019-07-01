package com.example.actividadrealm;

import android.util.Log;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema personSchema = schema.get("Persona");
            personSchema
                    .addField("nombrecompleto", String.class, FieldAttribute.INDEXED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("nombrecompleto", obj.getString("nombre") + " " + obj.getString("apellido"));
                        }
                    })
                    .removeField("nombre")
                    .removeField("apellido");

            Log.d("Migration", "actualitzant a la versió 1");

            oldVersion++;
        }
    }
}
