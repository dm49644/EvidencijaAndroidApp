package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ListView registeredUsers = (ListView) findViewById(R.id.registeredUsersView);
        ListView unconfirmedUsers = (ListView) findViewById(R.id.unconfirmedUsersView);

       /* populateList(registeredUsers);
        populateList(unconfirmedUsers); */
        //populate the list with bullshit just to see if it works
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i=0; i<30; i++) {
            String temp = i + "";
            arrayList.add(temp);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        registeredUsers.setClickable(true);
        registeredUsers.setAdapter(adapter);

        unconfirmedUsers.setClickable(true);
        unconfirmedUsers.setAdapter(adapter2);

        //NOTIFY THE ADAPTER ABOUT CHANGES
        adapter.notifyDataSetChanged();

        registeredUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(UsersActivity.this);
                adb.setTitle("Delete user?");
                adb.setCancelable(false);
                adb.setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Remove from the database
                            } });
                adb.setNegativeButton("No", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Do nothing
                            } });
                adb.show();
            }
        });

        unconfirmedUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(UsersActivity.this);
                adb.setTitle("Confirm user?");
                adb.setCancelable(false);
                adb.setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Set user as confirmed
                            } });
                adb.setNegativeButton("No", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //set user as declined
                            } });
                adb.show();
            }
        });
    }

    private void populateList(ListView view) {
        //TODO: FETCH USERS FROM SERVER
        /* ArrayList<User> users = new ArrayList<>();
           ArrayList<String> usersList = new ArrayList<>();
           for(User usr : users) {
                usersList.add(usr.toString());
           }
         */
    }
}
