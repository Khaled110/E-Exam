package com.example.onlineexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class MCQDialog extends AppCompatDialogFragment  {
    Spinner category,chapter;
    CheckBox option1,option2,option3,option4;
    EditText question, editOption1,editOption2,editOption3,editOption4;
    Button save;
    ImageView add;
    McqListener listener;
    String questionName, option1Name,option2Name,option3Name,option4Name,correctA,correctB,correctC,correctD;
    ArrayList<String> chapterValue;
    String categoryName,checker="",myURL="";
    Boolean bolOption1,bolOption2,bolOption3,bolOption4;
    Uri fileUri,uri1,uri2,uri3;
    StorageReference reference;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    MediaController mediaController;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.mcq_prof_dialog,null);
        option1=view.findViewById(R.id.ch_op1);
        option2=view.findViewById(R.id.ch_op2);
        option3=view.findViewById(R.id.ch_op3);
        option4=view.findViewById(R.id.ch_op4);
        question=view.findViewById(R.id.edit_q_prof);
        editOption1=view.findViewById(R.id.edit_option_1);
        editOption2=view.findViewById(R.id.edit_option_2);
        editOption3=view.findViewById(R.id.edit_option_3);
        editOption4=view.findViewById(R.id.edit_option_4);
        save=view.findViewById(R.id.btn_save_option);
        add=view.findViewById(R.id.img_q_prof);
        category=view.findViewById(R.id.spinner_category_q);
        chapter=view.findViewById(R.id.spinner_chapter_q);

        storage=FirebaseStorage.getInstance();
        reference=storage.getReference();
        mediaController=new MediaController(getActivity());

        progressDialog=new ProgressDialog(getActivity());
        chapterValue=new ArrayList<>();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setText(editOption1.getText().toString());
                option2.setText(editOption2.getText().toString());
                option3.setText(editOption3.getText().toString());
                option4.setText(editOption4.getText().toString());
                bolOption1=option1.isChecked();
                option2.setChecked(bolOption1);



            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence uploads [] =new CharSequence []{"Image","Video"};
                AlertDialog.Builder builder1=new AlertDialog.Builder(getActivity());
                builder1.setTitle("Type of question ..!");
                builder1.setItems(uploads, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            checker="Image";
                           uploadImage();
                        }
                        if(which==1){
                            checker="Video";
                            uploadVedio();
                        }
                        if(which==2) {
                            checker="Audio";
                            uploadAudio();
                        }
                    }


                });
                builder1.show();

            }


        });



        String[] values ={"A","B","C"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view)
                .setTitle("Add MCQ question ..!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                questionName=question.getText().toString();
                option1Name=option1.getText().toString();
                option2Name=option2.getText().toString();
                option3Name=option3.getText().toString();
                option4Name=option4.getText().toString();
                bolOption1=option1.isChecked();
                bolOption2=option2.isChecked();
                bolOption3=option3.isChecked();
                bolOption4=option4.isChecked();
                if(option1.isChecked()==true){
                    correctA=option1Name;
                }
                if(option2.isChecked()==true){
                    correctB=option2Name;
                }if(option3.isChecked()==true){
                    correctC=option3Name;
                }if(option4.isChecked()==true){
                    correctD=option4Name;
                }
               // uploadImage();
                if(uri1!=null){
                    listener.applyMCQ(questionName,option1Name,option2Name,option3Name,option4Name,categoryName,
                            bolOption1,bolOption2,bolOption3,bolOption4,correctA,correctB,correctC,correctD,uri1.toString(),null,null);

                }else if(uri2!=null){
                    listener.applyMCQ(questionName,option1Name,option2Name,option3Name,option4Name,categoryName,
                            bolOption1,bolOption2,bolOption3,bolOption4,correctA,correctB,correctC,correctD,null,uri2.toString(),null);

                }else if(uri3!=null){
                    listener.applyMCQ(questionName,option1Name,option2Name,option3Name,option4Name,categoryName,
                            bolOption1,bolOption2,bolOption3,bolOption4,correctA,correctB,correctC,correctD,null,null,uri3.toString());
                }else{
                    listener.applyMCQ(questionName,option1Name,option2Name,option3Name,option4Name,categoryName,
                            bolOption1,bolOption2,bolOption3,bolOption4,correctA,correctB,correctC,correctD,null,null,null);
                }


            }
        });
        return builder.create();
    }
    private void uploadAudio(){
        Intent intent=new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        MCQDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),2);
    }
    private void uploadVedio(){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        MCQDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),2);
    }

    private void uploadImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        MCQDialog.this.startActivityForResult(intent.createChooser(intent,"Select video"),2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
            fileUri = data.getData();
            Log.d("FileUri", fileUri.toString());

            if (checker.equals("Image")) {
                getData();
            }
            if(checker.equals("Video")){
                getVedio();
            }
            if(checker.equals("Audio")){
                getAudio();
            }
        }
    }
    private void getVedio(){
        if(fileUri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            StorageReference storageReference = reference.child("QuestionVedio/"+UUID.randomUUID().toString());
            storageReference.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            uri2=uri.getResult();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"upload done",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();

                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double prog=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Upload "+(int)prog+"%");
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"upload error",Toast.LENGTH_LONG).show();

        }
    }
    private void getAudio(){
        if(fileUri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            StorageReference storageReference = reference.child("QuestionAudio/"+UUID.randomUUID().toString());
            storageReference.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            uri3=uri.getResult();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"upload done",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();

                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double prog=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Upload "+(int)prog+"%");
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"upload error",Toast.LENGTH_LONG).show();

        }
    }
    private void getData(){

       // Log.d("FileUri", fileUri.toString();

        if(fileUri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            StorageReference storageReference = reference.child("QuestionImages/"+UUID.randomUUID().toString());
            storageReference.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                             uri1=uri.getResult();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"upload done",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();

                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double prog=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Upload "+(int)prog+"%");
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"upload error",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (McqListener) context;
         }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented MCQListener ");
        }
    }

    public interface McqListener{
        public void applyMCQ(String question,String option1,String option2,String option3,String option4,
                             String level,boolean bopOption1,boolean bolOption2,boolean bolOption3,boolean bolOption4,
                             String correctA,String correctB,String correctC,String correctD ,String img,String vedio,String audio);
//this.setFinishOnTouchOutside(false);

        //void applyMCQ(String questionName, String option1Name, String option2Name, String option3Name, String option4Name, String categoryName, Boolean bolOption1, Boolean bolOption2, Boolean bolOption3, Boolean bolOption4, String correctA);
    }



}
