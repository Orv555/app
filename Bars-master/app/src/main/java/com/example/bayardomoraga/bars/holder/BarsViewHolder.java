package com.example.bayardomoraga.bars.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.activity.Delete_Bars;
import com.example.bayardomoraga.bars.activity.Update_Bars;

public class BarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView id;
    private TextView name;
    private TextView description;
    private TextView address;
    private TextView type;
    private ImageButton delete;
    private ImageButton edit;

    private Context context;
    private Intent intent;


    public BarsViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        setId((TextView) itemView.findViewById(R.id.tvid));
        setName((TextView) itemView.findViewById(R.id.tvname));
        setDescription((TextView) itemView.findViewById(R.id.tvdescription));
        setType((TextView) itemView.findViewById(R.id.tvtype));
        setAddress((TextView) itemView.findViewById(R.id.tvaddress));
        setDelete((ImageButton) itemView.findViewById(R.id.btnDelete));
        setEdit((ImageButton) itemView.findViewById(R.id.btnEdit));
    }
    public void setOnClickListeners(){
        getDelete().setOnClickListener(this);
        getEdit().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDelete:
                AlertDialog dialog = deleteDialog();
                dialog.show();
                break;
            case R.id.btnEdit:
                intent = new Intent(context, Update_Bars.class);
                intent.putExtra("Id", getId().getText().toString());
                intent.putExtra("Name", getName().getText().toString());
                intent.putExtra("Description", getDescription().getText().toString());
                intent.putExtra("Address", getAddress().getText().toString());
                intent.putExtra("Type", getType().getText().toString());
                context.startActivity(intent);
                break;
        }
    }
    public AlertDialog deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete Bar")
                .setMessage("The element will be deleted")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(context, Delete_Bars.class);
                                intent.putExtra("Id", getId().getText().toString());
                                context.startActivity(intent);
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public ImageButton getDelete() {
        return delete;
    }

    public void setDelete(ImageButton delete) {
        this.delete = delete;
    }

    public ImageButton getEdit() {
        return edit;
    }

    public void setEdit(ImageButton edit) {
        this.edit = edit;
    }
}
