package coki.utils;

import coki.utils.mail.MailManager;

import java.util.Arrays;

/**
 * Created by wuyiming on 2018/1/15.
 */
public class Main {

    public static void main(String[] args) {
        MailManager.DEFAULT_SERVER.attachment(Arrays.asList("/Users/chen/Desktop/exportfiles/Courier-2018-04-10.xlsx"))
                .sendMail("lalalalala","test", Arrays.asList("january.4th@163.com"));
    }
}
