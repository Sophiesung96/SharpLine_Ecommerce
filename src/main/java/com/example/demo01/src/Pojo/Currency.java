package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Data
@NoArgsConstructor
@Slf4j
public class Currency implements Serializable {
    private int id;
    private String name;
    private String code;
    private String symbol;


    @Override
    public String toString() {
        return name  + "-" + code + "-"+symbol;
    }

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int[] actual = { 5, 1, 6, 2, 3, 4 };
        int[] expected = { 1, 2, 3, 4, 5, 6 };
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
            log.info("this is the:{}",i+" times to count l");
            log.info("l[i]:{}",l[i]);

        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
            log.info("r is count from :{}",i+"times ");
            log.info("r[i - mid]:{}",r[i - mid]);

        }
        log.info("l:{},r:{},mid:{}",l,r,mid);
        mergeSort(l, mid);
        mergeSort(r, n - mid);

       // merge(a, l, r, mid, n - mid);
        }

    public static void main(String[] args) {
        int[] actual = { 5, 1, 6, 2, 3, 4 };
        int[] expected = { 1, 2, 3, 4, 5, 6 };
        Currency.mergeSort(actual, actual.length);
    }
}
