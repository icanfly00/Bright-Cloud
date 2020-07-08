package com.tml.uaa;


import cn.hutool.crypto.asymmetric.RSA;

/**
 * @Description com.tml
 * @Author TuMingLong
 * @Date 2020/4/6 16:25
 */
public class MyTest {
    public static void main(String[] args) {
        RSA rsa = new RSA();
        //获得私钥
        System.out.println("私钥："+rsa.getPrivateKey());
        System.out.println("私钥Base64："+rsa.getPrivateKeyBase64());
        //获得公钥
        System.out.println("公钥："+rsa.getPublicKey());
        System.out.println("公钥Base64："+rsa.getPublicKeyBase64());


    }
}
