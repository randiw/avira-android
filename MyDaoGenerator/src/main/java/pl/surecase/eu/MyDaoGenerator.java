package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "my.com.avira");

        Entity record = schema.addEntity("Record");
        record.addIdProperty();
        record.addIntProperty("record_id");
        record.addStringProperty("description");
        record.addStringProperty("date_time");
        record.addLongProperty("created_at");
        record.addLongProperty("updated_at");
        record.addStringProperty("photos");
        record.addDateProperty("date");

        Entity photo = schema.addEntity("Photo");
        photo.addIdProperty();
        photo.addIntProperty("photo_id");
        photo.addIntProperty("record_id");
        photo.addStringProperty("caption");
        photo.addStringProperty("url");
        photo.addLongProperty("created_at");
        photo.addLongProperty("updated_at");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
