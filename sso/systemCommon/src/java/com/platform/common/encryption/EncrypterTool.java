package com.platform.common.encryption;


import java.io.IOException;
import java.util.Scanner;

/**
 * <pre>
 *
 * </pre>
 *
 * @author twotoo
 * @version $ EncrypterTool.java, v 0.1 May 3, 2011 10:39:00 AM twotoo Exp $
 * @since   JDK1.6
 */
public class EncrypterTool {

    /**
     * <pre>
     *
     * </pre>
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        while (true) {

            encrypter();
        }

    }

    public static void encrypter() {

        System.out.print("请输入要加密的字符串:");

        Scanner sc = new Scanner(System.in);

        String encrypterString = sc.nextLine();

        if (encrypterString != null && !"".equals(encrypterString.trim())) {

            if ("exit".equals(encrypterString.trim().toLowerCase())) {

                System.exit(0);

            } else {

                System.out.println("加密的字串:" + Encrypter.encrypt(encrypterString.trim()));

            }

        } else {

            System.out.println("输入的字串为空，请重新输入");
        }

    }

}
