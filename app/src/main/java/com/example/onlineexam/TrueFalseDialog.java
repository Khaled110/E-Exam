package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class TrueFalseDialog extends AppCompatDialogFragment {
        RadioButton trueQ,falseQ;
    Spinner category,chapter;
    EditText question;
        ImageView img;
        String trueName,falseName,questionName,categoryName,correctA,correctB,checker="";
        TruefalseListener listener;
        Uri fileUri,uri1,uri2,uri3;
    StorageReference reference;
    boolean t,f;
    FirebaseStorage storage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.tf_prof_dialog,null);
        trueQ=view.findViewById(R.id.radio_true_prof);
        falseQ=view.findViewById(R.id.radio_false_prof);
        question=view.findViewById(R.id.edit_tf_q_prof);
        img=view.findViewById(R.id.img_tf_q_prof);
        category=view.findViewById(R.id.spinner_category_q_tf);
         chapter=view.findViewById(R.id.spinner_chapter_q_tf);
        storage=FirebaseStorage.getInstance();
        reference=storage.getReference();

        img.setOnClickListener(new View.OnClickListener() {
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
                .setTitle("Add true & false question ..!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                questionName=question.getText().toString();
                trueName=trueQ.getText().toString();
                falseName=falseQ.getText().toString();
                t=trueQ.isChecked();
                f=falseQ.isChecked();
                if(trueQ.isChecked()==true){
                    correctA=trueName;
                }
                if(falseQ.isChecked()==true){
                    correctB=falseName;
                }

                if(uri1!=null){
                    listener.applyTF(questionName,trueName,falseName,uri1.toString(),null,null,categoryName,correctA,correctB,t,f);

                }
                else if(uri2!=null){
                    listener.applyTF(questionName,trueName,falseName,null,uri2.toString(),null,categoryName,correctA,correctB,t,f);

                }else if(uri3!=null) {
                    listener.applyTF(questionName,trueName,falseName,null,null,uri3.toString(),categoryName,correctA,correctB,t,f);

                }else{
                    listener.applyTF(questionName,trueName,falseName,null,null,null,categoryName,correctA,correctB,t,f);

                }

            }
        });
        return builder.create();
    }
    private void uploadImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        TrueFalseDialog.this.startActivityForResult(intent.createChooser(intent,"Select video"),3);
    }
    private void uploadVedio(){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        TrueFalseDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),3);
    }
    private void uploadAudio(){
        Intent intent=new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       TrueFalseDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
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
    private void getVedio(){
        if(fileUri!=null){
            final ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading ...!");
            progressDialog.show();
            StorageReference storageReference = reference.child("QuestionVedio/"+ UUID.randomUUID().toString());
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
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (TruefalseListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented MCQListener ");
        }
    }
    public interface TruefalseListener{
        public void applyTF(String question,String trueName,String falseName ,String img,String video,String audio,String categoryName,
                            String correctA,String correnctB,boolean t,boolean f);
    }
}
