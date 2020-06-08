package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BuildQuestionBank extends AppCompatDialogFragment {

    RadioButton mcq,tf,open;
    Intent intent;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.build_question_bank,null);
        mcq=view.findViewById(R.id.radio_MCQ);
        tf=view.findViewById(R.id.radio_t_f);
        open=view.findViewById(R.id.radio_open);

        builder.setView(view)

                .setTitle("Please select type of question ..!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                if(mcq.isChecked()){
                    OpenMCQDialog();
                }else if(tf.isChecked()){
                   openTFDialog();
                }else if(open.isChecked()){
                   openOpenQuestionDialog();
                }
            }
        });
        return builder.create();
    }
    private void OpenMCQDialog(){
        MCQDialog exampleDialog=new MCQDialog();
        exampleDialog.show(getFragmentManager(),"MCQ dialog");
    }
    private void openTFDialog(){
        TrueFalseDialog exampleDialog=new TrueFalseDialog();
        exampleDialog.show(getFragmentManager(),"TrueFalse dialog");
    }
    private void openOpenQuestionDialog(){
        OpenQuestionDialog exampleDialog=new OpenQuestionDialog();
        exampleDialog.show(getFragmentManager(),"OpenQuestion dialog");
    }

}
