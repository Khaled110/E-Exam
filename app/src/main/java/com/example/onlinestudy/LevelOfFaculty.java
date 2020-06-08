package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelOfFaculty extends AppCompatActivity implements LevelDialog.ExampleDialogListenr {
    private ListView levels;
    private Button add;
    private TextView levelOfFaculty;
    ArrayList<StyleLevelFaculty> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_of_faculty);
        levels=findViewById(R.id.levels);
        add = findViewById(R.id.add);
        levelOfFaculty=findViewById(R.id.level_of_faculty);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

       items = new ArrayList<StyleLevelFaculty>();


    }
    public void openDialog(){
        LevelDialog levelDialog =new LevelDialog();
        levelDialog.show(getSupportFragmentManager(),"Level Dialog");
    }

    @Override
    public void applyText(String Level) {
    items.add(new StyleLevelFaculty(Level));
    MyCustomerAdapter adapter =new MyCustomerAdapter(items);
    levels.setAdapter(adapter);
    }

    class MyCustomerAdapter extends BaseAdapter{
            ArrayList<StyleLevelFaculty> items = new ArrayList<StyleLevelFaculty>();
            MyCustomerAdapter( ArrayList<StyleLevelFaculty> items  ) {

                this.items=items;
            }
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                final LayoutInflater inflater =getLayoutInflater();
                final View view=inflater.inflate(R.layout.stylelevelfaculty,parent,false);
                final TextView level=view.findViewById(R.id.level);
                final ImageView edit=view.findViewById(R.id.edit);
                final ImageView delete=view.findViewById(R.id.delet);
                level.setText(items.get(position).level);
             level.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(LevelOfFaculty.this,DepartmentOfLevel.class);
                        intent.putExtra("level",level.getText().toString());
                        startActivity(intent);
                    }
                });

             delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LevelOfFaculty.this);
                        builder.setMessage("Are you sure you want delete").setTitle("Delete")
                             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                               items.remove(position);
                               notifyDataSetChanged();
                             }
                         })
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                             }
                         }).show();

                    }
                });
               /* edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdataLevel(items.get(position),position);
                    }
                });*/
                return view;
            }

     /* public void showUpdataLevel(StyleLevelFaculty oldItem, final int index) {
            final Dialog dialog = new Dialog(getApplicationContext());
            dialog.setTitle("Edit Level");
            dialog.setContentView(R.layout.editdailog);
            final EditText editText =dialog.findViewById(R.id.edit_text);
            String name =oldItem.getLevel();
            editText.setText(name);
            Button button=dialog.findViewById(R.id.edit_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StyleLevelFaculty styleLevelFaculty =new StyleLevelFaculty(editText.getText().toString());
                    items.set(index,styleLevelFaculty);
                    dialog.dismiss();

                }
            });
            dialog.show();
        }*/
    }
 }
