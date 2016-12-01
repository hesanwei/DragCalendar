package com.landscape.dragcalendar.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.landscape.dragcalendar.R;
import com.landscape.dragcalendar.adapter.MonthCalendarAdpter;
import com.landscape.dragcalendar.utils.CalendarUtil;
import com.landscape.dragcalendar.utils.DateUtils;

import java.util.Calendar;

/**
 * Created by landscape on 2016/11/30.
 */

public class MonthCard extends LinearLayout implements CardRender {
    int selectLine = 0;
    int selectPos = 0;

    public MonthCard(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        setBackgroundResource(android.R.color.tertiary_text_light);
        View.inflate(context, R.layout.month, this);

    }

    @Override
    public void render(ViewGroup view1, Calendar today,String selectTime) {
        //一页显示一个月+7天，为42；
        for (int b = 0; b < 6; b++) {
            final ViewGroup view = (ViewGroup) view1.getChildAt(b);
            for (int a = 0; a < 7; a++) {
                final int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
                final ViewGroup dayOfWeek = (ViewGroup) view.getChildAt(a);
                ((TextView) dayOfWeek.findViewById(R.id.gongli)).setText(dayOfMonth + "");
                dayOfWeek.setTag(DateUtils.getTagTimeStr(today));

                dayOfWeek.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callbk != null) {
                            callbk.onSelect(dayOfWeek.getTag().toString());
                        }
                    }
                });

                //不是当前月不显示
                int currentMonth = today.get(Calendar.MONTH);
                if ((Integer.parseInt((String) view1.getTag())) != currentMonth) {
                    dayOfWeek.setVisibility(INVISIBLE);
                    today.add(Calendar.DATE, 1);
                    continue;
                } else {
                    dayOfWeek.setVisibility(VISIBLE);
                }
                //如果是选中天的话显示为红色
                if (selectTime.equals(DateUtils.getTagTimeStr(today))) {
                    selectLine = b;
                    selectPos = calculatePos(dayOfWeek.getMeasuredHeight(),selectLine);
                    renderSelect(dayOfWeek,true);
                } else {
                    renderNormal(dayOfWeek,true);
                }
                today.add(Calendar.DATE, 1);
            }
        }
    }

    private void renderSelect(ViewGroup dayView,boolean containData) {
        dayView.findViewById(R.id.cal_container).setBackgroundResource(R.drawable.corner_shape_blue);
        if (containData) {
            ((ImageView) dayView.findViewById(R.id.imv_point)).setImageResource(R.drawable.calendar_item_point_select);
            dayView.findViewById(R.id.imv_point).setVisibility(VISIBLE);
        } else {
            dayView.findViewById(R.id.imv_point).setVisibility(INVISIBLE);
        }
    }

    private void renderNormal(ViewGroup dayView,boolean containData) {
        dayView.findViewById(R.id.cal_container).setBackgroundResource(R.drawable.corner_shape_blue);
        if (containData) {
            ((ImageView) dayView.findViewById(R.id.imv_point)).setImageResource(R.drawable.calendar_item_point);
            dayView.findViewById(R.id.imv_point).setVisibility(VISIBLE);
        } else {
            dayView.findViewById(R.id.imv_point).setVisibility(INVISIBLE);
        }
    }

    private int calculatePos(int unitHeight,int line) {
        return unitHeight * line;
    }

    public int getSelectLine() {
        return selectLine;
    }

    public int getSelectPos() {
        return selectPos;
    }

    public interface ICallbk{
        void onSelect(String selectTime);
    }

    ICallbk callbk = null;
    public void setCallbk(ICallbk callbk) {
        this.callbk = callbk;
    }
}
