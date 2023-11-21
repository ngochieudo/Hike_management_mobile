package com.example.assignment.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment.MainActivity;
import com.example.assignment.R;
import com.example.assignment.db.entity.Hike;
import java.util.ArrayList;
import java.util.List;

public class HikesAdapter extends RecyclerView.Adapter<HikesAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Hike> hikesList;
    private MainActivity mainActivity;

    // 2- ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView location;
        public TextView difficulty;
        public TextView date;
        public TextView length;
        public TextView parking;
//        public ImageView image;
        public Button deleteBtn;
        public Button editBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.location = itemView.findViewById(R.id.location);
            this.difficulty = itemView.findViewById(R.id.difficulty);
            this.date = itemView.findViewById(R.id.date);

//            this.image = itemView.findViewById(R.id.image);

        }
    }
    //bo sung
    public HikesAdapter(Context context, ArrayList<Hike> hikes, MainActivity mainActivity){
        this.context = context;
        this.hikesList = hikes;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public HikesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.listview_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HikesAdapter.MyViewHolder holder, int positions) {
        final Hike hike = hikesList.get(positions);

//        byte[] imageData = hike.getImage();
//        Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        holder.name.setText(hike.getName());
        holder.location.setText(hike.getLocation());
        holder.difficulty.setText(hike.getDifficulty());
        holder.date.setText(hike.getDate());
//        holder.image.setImageBitmap(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAndEditHikes(true,hike,positions);
            }
        });
    }
    //cho biết số phần tử của dữ liệu
    @Override
    public int getItemCount() {
        return hikesList.size();
    }

    // do update hikeList in the adapter and notify the adapter for changes
    public void updateList(List<Hike> list){
        hikesList = (ArrayList<Hike>) list;
        notifyDataSetChanged();
    }
}