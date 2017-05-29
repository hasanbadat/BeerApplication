package com.hasan.beerapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hasan.beerapp.R;
import com.hasan.beerapp.models.Datum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hasan on 5/25/2017.
 */

public class AdapterPager extends PagerAdapter {
    public List<Datum> mDataset = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    ViewPager viewPager;

    public AdapterPager(Context context, List<Datum> list, ViewPager viewPager) {
        mDataset = list;
        this.context = context;
        this.viewPager = viewPager;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getCount() {
        if (mDataset == null)
            return 0;
        return mDataset.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        View itemView = null;
        final Datum item = mDataset.get(position);

        ImageView ivImage = null;
        ProgressBar pb;
        TextView tvTitle, tvDesc;
        Button btnNext;
        try {
            itemView = inflater.inflate(R.layout.row_bear, container, false);
            if (item == null) {
                return itemView;
            }

            ivImage = (ImageView) itemView.findViewById(R.id.iv);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            btnNext = (Button) itemView.findViewById(R.id.btn_next);
            pb = (ProgressBar) itemView.findViewById(R.id.pb);
            tvDesc.setText(item.getDescription() + "");
            tvTitle.setText(item.getName() + "");
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(position + 1);
                }
            });
            if (item.getLabels() != null && !TextUtils.isEmpty(item.getLabels().getMedium()))
                Glide.with(context).load(item.getLabels().getMedium()).into(ivImage);
            else Glide.with(context).load(R.mipmap.ic_launcher).into(ivImage);

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("Position", position + "");
        }


        ((ViewPager) container).addView(itemView);
        return itemView;


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;  // This will get invoke as soon as you call notifyDataSetChanged on viewPagerAdapter.
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    public void replaceData(List<Datum> list) {
        this.mDataset = list;
        notifyDataSetChanged();
    }

    public void addToList(List<Datum> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


//    public Datum getItemAtPosition(int position) {
//        if (position < mDataset.size())
//            return mDataset.get(position);
//        return null;
//    }


}
