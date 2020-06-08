package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LevelAdapter extends ArrayAdapter<Level>  {
    AlertDialog dialog;
    ListView listView;
    ArrayList<Level> levels;

    public LevelAdapter(@NonNull Context context, @NonNull ArrayList<Level> levels) {
        super(context, 0, levels);
        this.levels = levels;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row_levels,parent,false);


        TextView textView=convertView.findViewById(R.id.txt);
       ImageView remove=convertView.findViewById(R.id.img_remove_level_i);
       ImageView edit=convertView.findViewById(R.id.img_edit_level_i);
       listView=convertView.findViewById(R.id.list);
        Level level =getItem(position);

            textView.setText(level.getLevel());
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("Warning!")
                            .setMessage("Are you sure you want to delete this level ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    levels.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            });
                    dialog=builder.create();
                    dialog.show();

                }

            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdata(levels.get(position),position);
                }
            });

      /*  Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels")
                .orderByChild("LevelName");

        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        java.lang.Object h = snapshot.getValue(java.lang.Object.class);
                        Map<Subject, java.lang.Object> mdata = (Map<Subject, java.lang.Object>) h;
                        Subject data = (Subject) mdata.get("LevelName");
                        // Artist artist = snapshot.getValue(Artist.class);
                        levels.add(new Level(data));
                       // listView.setAdapter(adapter);
                     notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/


        return convertView;
    }
    public void showUpdata(Level oldItem, final int index){
        final Dialog dialog=new Dialog(getContext());
        dialog.setTitle("Update Level");
        dialog.setContentView(R.layout.activity_updata_level);
        final EditText editText=dialog.findViewById(R.id.edit_level_update);
        String name=oldItem.getLevel();
        editText.setText(name);
        Button button=dialog.findViewById(R.id.btn_level_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Level level =new Level(editText.getText().toString());
                levels.set(index, level);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
