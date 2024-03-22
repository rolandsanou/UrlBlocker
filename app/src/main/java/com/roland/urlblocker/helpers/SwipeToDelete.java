package com.roland.urlblocker.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.roland.urlblocker.R;
import com.roland.urlblocker.adapter.UrlAdapter;
import com.roland.urlblocker.database.UrlDatabaseDao;
import com.roland.urlblocker.database.UrlLocalDatabase;

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    private UrlAdapter mAdapter;
    private boolean mSwipeRightEnabled;
    private Paint mBackgroundPaint;
    private RectF mBackgroundRect;
    private Drawable mDeleteIcon;
    private int mIconMargin;
    private Context mContext;
    public SwipeToDelete(UrlAdapter adapter, Drawable deleteIcon, Context context) {
        super(0,  ItemTouchHelper.LEFT);
        mContext = context;
        mAdapter = adapter;
        mBackgroundPaint = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBackgroundPaint.setColor(context.getColor(R.color.primary));
        }
        mBackgroundRect = new RectF();
        mDeleteIcon = deleteIcon;
        mIconMargin = 16; // Set your desired margin for the icon
        mSwipeRightEnabled = true; // Set to false if you don't want right swipe to delete
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if(mAdapter.getSelectedItem()!=null){
            //UrlLocalDatabase.getInstance(mContext).UrlDatabaseDao().deleteUrl(mAdapter.getSelectedItem());
            mAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getHeight();

            if (dX > 0 && mSwipeRightEnabled) { // Swiping right
                mBackgroundRect.set(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + dX, itemView.getBottom());
            } else if (dX < 0) { // Swiping left
                mBackgroundRect.set(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else {
                mBackgroundRect.setEmpty();
            }

            c.drawRect(mBackgroundRect, mBackgroundPaint);

            if (mDeleteIcon != null) {
                int iconSize = mDeleteIcon.getIntrinsicHeight();
                int iconTop = itemView.getTop() + (itemHeight - iconSize) / 2;
                int iconBottom = iconTop + iconSize;

                if (dX > 0 && mSwipeRightEnabled) { // Swiping right
                    int iconLeft = itemView.getLeft() + mIconMargin;
                    int iconRight = itemView.getLeft() + mIconMargin + iconSize;
                    mDeleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                } else if (dX < 0) { // Swiping left
                    int iconRight = itemView.getRight() - mIconMargin;
                    int iconLeft = itemView.getRight() - mIconMargin - iconSize;
                    mDeleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                }

                mDeleteIcon.draw(c);
            }
        }
    }
}
