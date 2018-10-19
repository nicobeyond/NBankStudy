package ico.ico.ico;

import org.junit.Test;

import java.util.Scanner;

import ico.ico.util.Common;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        System.out.println(RingTypeEnum.ALARM.getIndex() + "");

//        String name = Common.getFilename(new File("/dsadas/dasdas/dasdas.pdf"));
        Scanner input = new Scanner(System.in);
        String str = "";
//        do {
            System.out.println("请输入：");
            str = input.next();
            System.out.println(Common.encodeByMd5(str));
            System.out.println(Common.toMd5(str));
//        } while (!str.equals("exit"));
    }

}