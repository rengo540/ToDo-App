package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todo.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView ;
    FloatingActionButton floatingActionButton ;

   DatabaseReference reference ;
   FirebaseAuth auth ;

private ProgressDialog loader ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        toolbar=findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        recyclerView = findViewById(R.id.homeRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

      auth=FirebaseAuth.getInstance();
      String onlineUserId = auth.getCurrentUser().getUid();
      reference=FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserId);

      loader= new ProgressDialog(this);


        floatingActionButton = findViewById(R.id.floatingBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });

    }

    private void addTask() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myview = inflater.inflate(R.layout.input_file, null);
        mydialog.setView(myview);

        AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        dialog.show();

        final EditText task = myview.findViewById(R.id.task);
        final EditText description = myview.findViewById(R.id.description);

        Button save = myview.findViewById(R.id.savebtn);
        Button cancel = myview.findViewById(R.id.cancelBtn);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTask = task.getText().toString().trim();
                String mDescription = description.getText().toString().trim();
                String id =reference.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());



                if (TextUtils.isEmpty(mTask)) {
                    task.setError("task is required");
                    return;
                }
                if (TextUtils.isEmpty(mDescription)) {
                    description.setError("Description Required");
                    return;
                } else {

                    loader.setMessage("adding data");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    Task task =new Task(mTask,mDescription,id,date);

                    reference.child(id).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Home.this,"task has been added successfully",Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                                dialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(Home.this,"Failed",Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });



                }


            }
        });

    }
}