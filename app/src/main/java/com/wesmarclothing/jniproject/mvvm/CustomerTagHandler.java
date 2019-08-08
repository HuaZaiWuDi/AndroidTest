package com.wesmarclothing.jniproject.mvvm;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.util.TypedValue;
import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Stack;

/**
 * 自定义的一html标签解析
 * <p>
 * Created by Siy on 2016/11/19.
 */
 
 class CustomerTagHandler implements Html.TagHandler {

     private Context context;

    public CustomerTagHandler(Context context) {
        this.context = context;
    }

    /**
     * html 标签的开始下标
     */
    private Stack<Integer> startIndex;
 
    /**
     * html的标签的属性值 value，如:<size value='16'></size>
     * 注：value的值不能带有单位,默认就是sp
     */
    private Stack<String> propertyValue;
 
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        Log.e("TAG","handleTag:"+tag);
        if (opening) {
            handlerStartTAG(tag, output, xmlReader);
        } else {
            handlerEndTAG(tag, output);
        }
    }
 
    /**
     * 处理开始的标签位
     *
     * @param tag
     * @param output
     * @param xmlReader
     */
    private void handlerStartTAG(String tag, Editable output, XMLReader xmlReader) {
        if (tag.equalsIgnoreCase("del")) {
            handlerStartDEL(output);
        } else if (tag.equalsIgnoreCase("size")) {
            handlerStartSIZE(output, xmlReader);
        }
    }
 
    /**
     * 处理结尾的标签位
     *
     * @param tag
     * @param output
     */
    private void handlerEndTAG(String tag, Editable output) {
        if (tag.equalsIgnoreCase("del")) {
            handlerEndDEL(output);
        } else if (tag.equalsIgnoreCase("size")) {
            handlerEndSIZE(output);
        }
    }
 
    private void handlerStartDEL(Editable output) {
        if (startIndex == null) {
            startIndex = new Stack<>();
        }
        startIndex.push(output.length());
    }
 
    private void handlerEndDEL(Editable output) {
        output.setSpan(new StrikethroughSpan(), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
 
    private void handlerStartSIZE(Editable output, XMLReader xmlReader) {
        if (startIndex == null) {
            startIndex = new Stack<>();
        }
        startIndex.push(output.length());
 
        if (propertyValue == null) {
            propertyValue = new Stack<>();
        }
 
        propertyValue.push(getProperty(xmlReader, "value"));
    }
 
    private void handlerEndSIZE(Editable output) {
 
        if (!isEmpty(propertyValue)) {
            try {
                int value = Integer.parseInt(propertyValue.pop());
                output.setSpan(new AbsoluteSizeSpan(sp2px(context, value)), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 利用反射获取html标签的属性值
     *
     * @param xmlReader
     * @param property
     * @return
     */
    private String getProperty(XMLReader xmlReader, String property) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);
 
            for (int i = 0; i < len; i++) {
                // 这边的property换成你自己的属性名就可以了
                if (property.equals(data[i * 5 + 1])) {
                    return data[i * 5 + 4];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
 
    /**
     * 缩放独立像素 转换成 像素
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue){
        return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics())+0.5f);
    }
}