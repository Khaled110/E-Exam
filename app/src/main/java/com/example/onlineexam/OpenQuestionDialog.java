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
import android.widget.Spinner;
import android.widget.TextView;
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

public class OpenQuestionDialog extends AppCompatDialogFragment {
    EditText openQuestion;
    TextView degree;
    ImageView add,top,down;
    Spinner category,chapter;

    static int i=0;
    String questionName,degreeValue,categoryName;
    OpenQuestionListener listener;
    String checker="";
    Uri uri1,uri2,uri3,fileUri;
    StorageReference reference;
    FirebaseStorage storage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.openquestion_prof_dialog,null);
        openQuestion=view.findViewById(R.id.edit_open_question);
        add=view.findViewById(R.id.img_open_question);
        category=view.findViewById(R.id.spinner_category_q_open);
        chapter=view.findViewById(R.id.spinner_chapter_q_open);
        degree=view.findViewById(R.id.open_question_dgree);
        top=view.findViewById(R.id.top);
        down=view.findViewById(R.id.down);
        storage= FirebaseStorage.getInstance();
        reference=storage.getReference();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence uploads [] =new CharSequence []{"Image","Video","Audio"};
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

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                degree.setText(String.valueOf(i));
                down.setEnabled(true);

            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                degree.setText(String.valueOf(i));
                if(i<=0){
                    i=0;
                    down.setEnabled(false);
                }
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
                .setTitle("Add open question ..!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i=0;
                        dismiss();
                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(openQuestion.getText().toString().length()>0){
                    questionName=openQuestion.getText().toString();
                    degreeValue=degree.getText().toString();
                    if(uri1!=null){
                        listener.applyOpenQuestion(questionName,degreeValue,uri1.toString(),null,null,categoryName);
                    }
                    else if(uri2!=null){
                        listener.applyOpenQuestion(questionName,degreeValue,null,uri2.toString(),null,categoryName);
                    }else if(uri3!=null){
                        listener.applyOpenQuestion(questionName,degreeValue,null,null,uri3.toString(),categoryName);
                    }else{
                        listener.applyOpenQuestion(questionName,degreeValue,null,null,null,categoryName);

                    }
                }else{
                    Toast.makeText(getActivity(),"Must Enter Question",Toast.LENGTH_SHORT).show();

                }

            }
        });
        return builder.create();
    }
    private void uploadAudio(){
        Intent intent=new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        OpenQuestionDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),4);
    }
    private void uploadVedio(){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        OpenQuestionDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),4);
    }

    private void uploadImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        OpenQuestionDialog.this.startActivityForResult(intent.createChooser(intent,"Select Image"),4);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==4&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
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
            StorageReference storageReference = reference.child("QuestionVedio/"+ UUID.randomUUID().toString());
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
            listener= (OpenQuestionListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented OpenQuestionListener ");
        }
    }
    public interface OpenQuestionListener{
        public void applyOpenQuestion(String question,String degree,String img,String video,String audio,String caregory);
    }
}
