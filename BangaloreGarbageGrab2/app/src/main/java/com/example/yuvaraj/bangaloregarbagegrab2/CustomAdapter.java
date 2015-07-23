//package com.example.yuvaraj.bangaloregarbagegrab2;
//
///**
// * Created by Yuvaraj on 18/07/15.
// */
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class CustomAdapter extends BaseAdapter{
//
//    String [] result,result1,result2;
//    Context context;
//    int [] imageId;
//    private static LayoutInflater inflater=null;
//    public CustomAdapter(MainActivity mainActivity, String[] LatitudeList, String[] LongitudeList, String[] TimeList,int[] prgmImages) {
//        // TODO Auto-generated constructor stub
//        result=LatitudeList;
//        result1=LongitudeList;
//        result2=TimeList;
//        context=mainActivity;
//        imageId=prgmImages;
//        inflater = ( LayoutInflater )context.
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return result.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    public class Holder
//    {
//        TextView tv;
//        TextView tv1;
//        TextView tv2;
//        ImageView img;
//    }
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//        Holder holder=new Holder();
//        View rowView;
//        rowView = inflater.inflate(R.layout.list_row, null);
//        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
//        holder.tv1=(TextView) rowView.findViewById(R.id.textView2);
//        holder.tv2=(TextView) rowView.findViewById(R.id.textView3);
//        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
//        holder.tv.setText(result[position]);
//        holder.tv1.setText(result1[position]);
//        holder.tv2.setText(result2[position]);
//        holder.img.setImageResource(imageId[position]);
//        rowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });
//        return rowView;
//    }
//
//}
