package ran.com.swastikgold;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewAdapter> {
    private List<ClientData> list;
    private Context context;
    private String category;


    public ClientAdapter(List<ClientData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public ClientViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item_layout, parent,false);
        return new ClientViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewAdapter holder, int position) {

        final ClientData item = list.get(position);
        holder.name.setText(item.getName());
        holder.phone.setText(item.getPhone());
        holder.rate.setText(item.getRate());
        holder.quantity.setText(item.getQuantity());
        holder.total.setText(item.getTotal());
        holder.pending.setText(item.getPending());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateClient.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("rate",item.getRate());
                intent.putExtra("quantity",item.getQuantity());
                intent.putExtra("total",item.getTotal());
                intent.putExtra("pending",item.getPending());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ClientViewAdapter extends RecyclerView.ViewHolder {

        private TextView name,phone,rate,quantity,total,pending;
        private Button update;

        public ClientViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cName);
            phone = itemView.findViewById(R.id.cPhone);
            rate = itemView.findViewById(R.id.cRate);
            quantity = itemView.findViewById(R.id.cQuantity);
            total = itemView.findViewById(R.id.cTotalBill);
            pending = itemView.findViewById(R.id.cPendingBill);
            update = itemView.findViewById(R.id.clientUpdateInfo);
        }
    }
}
