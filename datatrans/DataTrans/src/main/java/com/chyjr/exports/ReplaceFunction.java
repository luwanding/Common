package com.chyjr.exports;

public class ReplaceFunction {

    public static void main(String args[]){
        String str = "'163';()";
        System.out.println(str.replaceAll("'","").replaceAll(";","").replaceAll("\\(","").replaceAll("\\)",""));
    }
}
