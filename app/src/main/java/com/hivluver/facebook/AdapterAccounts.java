package com.hivluver.facebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAccounts extends RecyclerView.Adapter<AdapterAccounts.ViewHolder> {
    private List<ModelAccounts> mEmpleados;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder{
        //TextView IDACCOUNT;
        TextView EMAIL;
        TextView PASSWORD;

        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            EMAIL = v.findViewById(R.id.email_item);
            PASSWORD = v.findViewById(R.id.password_item);

        }


    }
    public void add(int position, ModelAccounts empleados_admin) {
        mEmpleados.add(position, empleados_admin);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mEmpleados.remove(position);
        notifyItemRemoved(position);
    }
    public AdapterAccounts(List<ModelAccounts> myDataset, Context context, RecyclerView recyclerView) {
        mEmpleados = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }
    @Override
    public AdapterAccounts.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.item_accounts, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(AdapterAccounts.ViewHolder holder, final int position) {

        final ModelAccounts person = mEmpleados.get(position);
        // holder.personId.setText("Numero:"+ person.getId());
        holder.PASSWORD.setText(person.getPassword());
        holder.EMAIL.setText(person.getEmail());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
    @Override
    public int getItemCount() {
        return mEmpleados.size();
    }
}
