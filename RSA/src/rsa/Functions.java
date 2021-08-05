/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Functions {

    ArrayList verilerN1 = new ArrayList();
    ArrayList sifirKutusu = new ArrayList();
    ArrayList ascii = new ArrayList();
    ArrayList getirVerilerN1 = new ArrayList();

    BigInteger p;

    BigInteger q;

    BigInteger n;

    BigInteger totient;

    BigInteger e;

    BigInteger d;

    int bitUzunlugu = 11;

    Random r;

    public Functions(String metin) {
        r = new Random();

        p = BigInteger.probablePrime(bitUzunlugu, r);//probablePrime, asal sayı kontrolü sağlıyor.
        q = BigInteger.probablePrime(bitUzunlugu, r);
        n = p.multiply(q);
        totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitUzunlugu, r);//e asal olarak alınmalı ki şifreleme zor olsun.

        System.out.println("p :" + p);
        System.out.println("q :" + q);
        System.out.println("totient :" + totient);
        System.out.println("e :" + e);
        System.out.println("n: " + n);

        if (e.compareTo(BigInteger.ONE) > 0 && e.compareTo(totient) < 0) {
            e.add(BigInteger.probablePrime(bitUzunlugu, r));
        }

        d = e.modInverse(totient);
        System.out.println("d: " + d);
        int Lclear = basamakSayisiBulma(n.intValue()) - 1;//n-1
        int Lcipher = basamakSayisiBulma(n.intValue());//n

        //ASCII ÇEVİRME
        char[] dizi = new char[metin.length()];
        dizi = metin.toCharArray();

        for (int i = 0;
                i < metin.length();
                i++) {
            ascii.add((int) dizi[i]);

        }

        ArrayList asciLazim = new ArrayList();
        for (int j = 0; j < ascii.size(); j++) {
            asciLazim.add(ascii.get(j));
        }//asciiye çevrilmiş elemanları tutuyoruz lazım olma durumuna karşı

        System.out.println(
                "ASCII:" + ascii);

        //SIFIRLARI SOLA EKLEME N-1 BASAMAK KADAR YAPMA. ŞİFRELENECEK SAYILAR N BASAMAK SAYISINDAN KÜÇÜK OLMALIDIR.BU NEDENLE N-1 PARÇALARA AYRILIR.
        for (int a = 0;
                a < metin.length();
                a++) {
            sifirKutusu.add(0);
        }
        for (int j = 0;
                j < ascii.size();
                j++) {
            if (ascii.get(j).toString().length() < Lclear)//3e bak! basamak sayısı aldık.
            {

                for (int i = 0; i < Lclear - ascii.get(j).toString().length();) {

                    ascii.set(j, sifirKutusu.get(j).toString() + ascii.get(j).toString());
                }

            }

        }

        System.out.println(
                "ASCII BASAMAK FORMATI:" + ascii);//ascii formatı için yapılıyor.

        //SIFIRLI ASCII METNİN BİRLEŞTİRİLMESİ
        String birlesim = "";
        for (int i = 0;
                i < ascii.size();
                i++) {
            birlesim += ascii.get(i).toString();
        }

        System.out.println(
                "ASCII BİRLEŞMİŞ METİN :" + birlesim);

        //SIFIRLI ASCII METNİN N-1 BASAMAK ŞEKLİNDE PARÇALANMASI
        System.out.println(
                "ASCII BİRLEŞMİŞ LCLEAR PARÇALAMA : " + LclearParcalama(birlesim, Lclear));

        //N-1 PARÇALI METİNDE N-1 BASAMAK OLMAYANLARIN N-1 BASAMAKLI YAPILMASI SIFIR EKLENMESİ
        int a = 0;
        int b = Lclear;

        for (int j = 0;
                j < getirVerilerN1.size();
                j++) {

            if (getirVerilerN1.get(j).toString().length() < Lclear) {

                for (int i = 0; i < Lclear - getirVerilerN1.get(j).toString().length();) {

                    getirVerilerN1.set(j, getirVerilerN1.get(j).toString() + sifirKutusu.get(i).toString());

                }

            }

        }
        //int t;
        String deger = "";
        String deger1 = "";
        ArrayList veri = new ArrayList();
        for (int k = 0; k < getirVerilerN1.size(); k++) {

            deger1 = getirVerilerN1.get(k).toString();
            deger = deger1.toString();
            a = Integer.valueOf(deger1);
            veri.add(a);
        }

        System.out.println(
                "LCLEAR PARÇALI METİNDE LCLEAR BASAMAK SAYISINDA OLMAYANLARIN SIFIRLANMIŞ HALİ : " + getirVerilerN1);

        //ŞİFRERLENMİŞ METİN
        ArrayList sifreliMetin = sifrele(veri, e, n);
        System.out.println("E ÜSTEL N MOD ALINMIŞ ELEMANLAR : " + sifreliMetin);//ascii tablosu karakterleri

        for (int j = 0;
                j < sifreliMetin.size();
                j++) {
            if (sifreliMetin.get(j).toString().length() < Lcipher) {

                for (int i = 0; i < Lcipher - sifreliMetin.get(j).toString().length();) {

                    sifreliMetin.set(j, sifirKutusu.get(i).toString() + sifreliMetin.get(j).toString());
                }

            }

        }

        System.out.println("ŞİFRELEME İŞLEMİ SONUCU LCİPHER BASAMAK SAYISINA UYMAYANLARIN SIFIRLANMIŞ HALİ : " + sifreliMetin);

        //ÇÖZÜMLENMİŞ METİN
        String sifreliBirlesmis = "";

        for (int f = 0; f < sifreliMetin.size(); f++) {
            sifreliBirlesmis += sifreliMetin.get(f).toString();

        }

        String deger2 = "";
        String deger3 = "";
        ArrayList veri1 = new ArrayList();
        for (int k = 0; k < sifreliMetin.size(); k++) {

            deger3 = sifreliMetin.get(k).toString();
            deger2 = deger3.toString();
            a = Integer.valueOf(deger3);
            veri1.add(a);
        }
        System.out.println("LCİPHER UYGUN HALİN STRİNG BİRLEŞMİŞ DURUMU :" + sifreliBirlesmis);
        ArrayList coz = cozumle(veri1, d, n);//yukarıda verileri veri1 kısmında int yapıp çözümleme işlemlerini gerçekleştiriyoruz.

        //STANDART BİR FORM OLMASI ADINA N-1 DEĞİL DE ASCII 3 LÜ FORMATA DÖNÜŞTÜRÜLÜP ÇÖZÜLÜR
        ArrayList asciiParcala = asciiHaldeParcala(sifreliBirlesmis);
        System.out.println("BİRLEŞMİŞ DURUMUN ASCII FORMATI ADINA ASCII PARÇALI HALİ : " + asciiParcala);

        //yukarıdaki asciiparcalayı şifrelemek aslında hepsinin tek tek ascii karşılığını bulma.
        char[] asciiDizi = new char[asciiParcala.size()];
        for (int i = 0; i < asciiParcala.size(); i++) {
            asciiDizi[i] = (char) (Integer.parseInt(asciiParcala.get(i).toString()));
        }

        System.out.print("ŞİFRELİ METİN : ");
        System.out.println(asciiDizi);//böyle aşağıda yazdık çünkü char olduğu için hata veriyor.

        //ZATEN YUKARIDA D ÜSSÜNÜ N MODUNU KALIP İLK ASCII HALE GELEN IFADELER
        System.out.println("ÇÖZÜMLÜ METİN: " + coz);

        //GİRİLEN Y>0 METNİ ÖRNEĞİN ONU TEKRAR YUKARIDAKİ ÇÖZDEN ALIP ASCII TERSİNE DÖNÜŞÜMLE ESKİ HALE GETİRME
        char[] son = new char[coz.size()];
        for (int k = 0; k < coz.size(); k++) {
            son[k] = (char) (Integer.parseInt(coz.get(k).toString()));
        }
        System.out.print("SON HAL : ");
        System.out.println(son);

    }

    //SIFIRLI ASCII METNİN N-1 BASAMAK ŞEKLİNDE PARÇALANMASI
    public ArrayList LclearParcalama(String metin, int Lclear) {//LclearParcalama

        int a = metin.length();
        int b = metin.length() % Lclear;
        int c = 0;
        int d = Lclear;
        for (int i = 0; i < metin.length() / Lclear + 1; i++) {

            if (c <= metin.length() && d <= metin.length()) {
                //System.out.println(i + "bak:" + metin.substring(c, d));
                verilerN1.add(metin.substring(c, d));
                c = c + Lclear;
                d = d + Lclear;

                if (metin.length() - c <= Lclear) {
                    //System.out.println("x:" + metin.substring(c));
                    verilerN1.add(metin.substring(c));
                    break;
                }

            }

        }
        for (int k = 0; k < verilerN1.size(); k++) {
            getirVerilerN1.add(verilerN1.get(k));
        }

        return verilerN1;

    }

    public static ArrayList asciiHaldeParcala(String metin) {
        ArrayList asciiParca = new ArrayList();

        int a = metin.length();
        int b = metin.length() % 3;
        int c = 0;
        int d = 3;
        for (int i = 0; i < metin.length() / 3 + 1; i++) {

            if (c <= metin.length() && d <= metin.length()) {
                //System.out.println(i + "bak:" + metin.substring(c, d));
                asciiParca.add(metin.substring(c, d));
                c = c + 3;
                d = d + 3;

                if (metin.length() - c <= 3) {
                    //System.out.println("x:" + metin.substring(c));
                    asciiParca.add(metin.substring(c));
                    break;
                }

            }

        }

        return asciiParca;

    }

    public static ArrayList sifrele(ArrayList veriler, BigInteger publicKey, BigInteger base) {
        ArrayList sifreli = new ArrayList();
        //BigInteger baseBig = BigInteger.valueOf(base);
        //BigInteger publicBig = BigInteger.valueOf(publicKey);

        for (int i = 0; i < veriler.size(); i++) {
            BigInteger mesajBig = BigInteger.valueOf((int) veriler.get(i));
            BigInteger sifreli1 = mesajBig.pow(publicKey.intValue()).mod(base);

            sifreli.add(sifreli1);

        }
        return sifreli;
    }

    public static ArrayList cozumle(ArrayList veriler, BigInteger d, BigInteger n) {
        ArrayList sifreli = new ArrayList();
        //BigInteger baseBig = BigInteger.valueOf(base);
        //BigInteger publicBig = BigInteger.valueOf(publicKey);

        for (int i = 0; i < veriler.size(); i++) {
            BigInteger mesajBig = BigInteger.valueOf((int) veriler.get(i));
            BigInteger sifreli1 = mesajBig.pow(d.intValue()).mod(n);

            sifreli.add(sifreli1);

        }
        return sifreli;
    }

    public static boolean aralarindaAsal(int x, int y) {
        int sayac = 0;
        for (int i = 1; i <= x; i++) {
            if (x % i == 0 && y % i == 0) {
                sayac++;
            }
        }
        if (sayac == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int basamakSayisiBulma(int n) {
        int adet = 0;
        while (n != 0) {
            n = n / 10;
            adet++;

        }
        return (int) adet;
    }

}
