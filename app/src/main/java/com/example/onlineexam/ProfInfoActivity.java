package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ProfInfoActivity extends AppCompatActivity {
    TextView textView;
    EditText editPDF;
    ImageView pdfImg;
    ListView listView;
    DatabaseReference databaseReference,reference;
    StorageReference storageReference;
    ArrayList<UploadPDFInfo> uploadPDFS;
    UploadPDFInfo pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_info);
        textView=findViewById(R.id.text_info);
        editPDF=findViewById(R.id.edit_info_subject);
        pdfImg=findViewById(R.id.img_pdf_info);
        listView=findViewById(R.id.list_info_prof);
        uploadPDFS=new ArrayList<>();
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploadsInfo");

        String name=getIntent().getStringExtra("subjectInfo");
        textView.setText(name);
        viewFiles();
        pdfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPDF.getText().toString().length()>0) {
                    selectPDF();
                }else {
                    Toast.makeText(ProfInfoActivity.this,"Must Enter PDF Name .. ",Toast.LENGTH_LONG).show();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UploadPDFInfo uploadPDF=uploadPDFS.get(position);
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));

                startActivity(intent);

            }
        });
    }
    private void selectPDF(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"select PDF"),1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            UploadPDFFile(data.getData());
        }
    }
    private void UploadPDFFile(Uri data){
        final ProgressDialog progressDialog=new ProgressDialog(ProfInfoActivity.this);
        progressDialog.setTitle("Uploading ...!");
        progressDialog.show();
        StorageReference sr=storageReference.child("uploadsInfo/"+System.currentTimeMillis()+".pdf");
        sr.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri uri1=uri.getResult();


                        UploadPDFInfo uploadPDF=new UploadPDFInfo(editPDF.getText().toString(),uri1.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(ProfInfoActivity.this,"File Uploead",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double prog=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Upload "+(int)prog+"%");
            }
        });
    }
    private void viewFiles(){
        Query query =FirebaseDatabase.getInstance().getReference().child("uploadsInfo");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    pdf=snapshot.getValue(UploadPDFInfo.class);
                    uploadPDFS.add(pdf);
                }
                String [] values=new String[uploadPDFS.size()];

                for(int i=0;i<values.length;i++){
                    values[i]=uploadPDFS.get(i).getName();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ProfInfoActivity.this,android.R.layout.simple_list_item_1,values){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView textView=view.findViewById(android.R.id.text1);
                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextSize(20);

                        return view;
                    }
                };

                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
