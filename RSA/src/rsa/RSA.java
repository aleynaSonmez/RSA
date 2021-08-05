/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in, "ISO-8859-9");//""içindeki ifade Scanner ile uzun metin almamızı sağlıyor..

        String metin;

        System.out.println("Şifrelenecek Metni Giriniz:");

        metin = sc.nextLine();

        Functions f = new Functions(metin);

    }

}
