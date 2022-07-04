package com.example.tianping;

public class ValueBean {
    public int product_id;
    public String title;
    public int num;
    public int count;
    //变压器id
    public int getProduct_id(){
        return product_id;
    }
    public void setProduct_id(int product_id){
        this.product_id = product_id;
    }
    //变压器名称
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    //变压器数值
    public int getNum(){
        return num;
    }
    public void  setNum(int num){
        this.num = num;
    }
    //变压器的数量
    public int getCount(){return count;}
    public void setCount(int count){this.count = count;}
}
