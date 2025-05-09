package com.example.madassignment4.FoodModule.FoodBean;

import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class FoodBean implements Serializable {
    //食物名称
    private String name;
    //图片
    @DrawableRes
    private int image;
    //数量
    private String count;
    //详情
    private int detail;
    //卡路里
    private int calorie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodBean foodBean = (FoodBean) o;

        return name.equals(foodBean.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public FoodBean() {
    }

    public FoodBean(String name, int image, String count, int detail, int calorie) {
        this.name = name;
        this.image = image;
        this.count = count;
        this.detail=detail;
        this.calorie=calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }
}
