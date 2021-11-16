package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;
    DBNHelper dbnHelper;

    //Обьявляем переменную класса DBNHelper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);


        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);


        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmaile);
        dbnHelper = new DBNHelper(this);
    }

    @Override
    public void  onClick (View v) {

        String name = etName.getText().toString();
        String emaile = etEmail.getText().toString();
        // в методе onClick создаем оббект   SQLiteDatabase - этот класс предназначен для управления баззой данный
        //в классе  SQLiteDatabase определенны такие методы guery-для чтения баззы данных их бд,insert-для
        // добавления данных их бд,delete-для удаления данных их бд,update-для изменения данных в бд
        SQLiteDatabase database = dbnHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues этот класс используетс для добавления новых строк в таблицу
        switch (v.getId()) {
            case R.id.btnAdd:
                contentValues.put(DBNHelper.KEY_NAME, name);
                contentValues.put(DBNHelper.KEY_MAIL, emaile);
                database.insert(DBNHelper.TABLE_CONTACTS, null, contentValues);
                //действия по нажатию btnAdd
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBNHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBNHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBNHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBNHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID =" + cursor.getInt(nameIndex) +
                                ",name - " + cursor.getString(nameIndex) +
                                ",email = " + cursor.getString(emailIndex));


                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows ");
                cursor.close();


                //действия по нажатию btnRead
                break;
            case R.id.btnClear:
                database.delete(DBNHelper.TABLE_CONTACTS, null, null);

                //действия по нажатию btnClear
                break;
        }
        dbnHelper.close();
    }

}