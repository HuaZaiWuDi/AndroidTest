package com.wesmarclothing.jniproject

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("测试开始")
        assertEquals(4, 2 + 2)
        println("测试结束")
    }


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }



    @Test
    fun test1() {
        /**
         * StringFormat
         *  "%s.%S.%s", "a", "b", "c"  ,a.B.c
         *  "%s$1.%s$3.%s$2" ,"a", "b", "c"  ,a.c.b
         *  %b  boolean类型
         *  %d和%h  int类型
         *  %f   float浮点类型  %.2f ,0.3333 : 0.33
         *  %02d   数字前补0    %05d ，22 ：00022
         *  % 10d  前面补空格   % 3d ，4 ：\t\t\t3
         *  %,d
         *  年-月-日格式：%tF
         *  月/日/年格式：%tD
         *  HH:MM:SS格式（24时制）：%tT
         *  HH:MM格式（24时制）：%tR
         *   HH  %tH
         *   MM  %tM
         *   SS  %tS
         *   即年的第几天：%tj
         *   本地星期的简称：%tA
         *   小写字母的上午或下午标记（中）：%tp  上午
         * */


        var str: String? = null
        System.out.printf("Hi,%s %n", "王力")//Hi,王力
        System.out.printf("Hi,%s:%s.%s %n", "王南", "王力", "王张")
        System.out.printf("字母a的大写是：%c %n", 'A')//字母a的大写是：A
        System.out.printf("3>7的结果是：%b %n", 3 > 7)//3>7的结果是：false
        System.out.printf("100的一半是：%d %n", 100 / 2)//100的一半是：50
        System.out.printf("100的16进制数是：%x %n", 100)//100的16进制数是：64
        System.out.printf("100的8进制数是：%o %n", 100)
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50 * 0.85)
        System.out.printf("上面价格的16进制数是：%a %n", 50 * 0.85)
        System.out.printf("上面价格的指数表示：%e %n", 50 * 0.85)
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50 * 0.85)
        System.out.printf("上面的折扣是%d%% %n", 85)
        System.out.printf("字母A的散列码是：%h %n", 'A')


        System.out.printf("%n")
        System.out.printf("\$使用%n")
        System.out.printf("%n")

        //$使用
        str = String.format("\$使用格式参数\$的使用：%1\$d,%2\$s", 99, "abc")
        println(str)
        //+使用
        System.out.printf("+使用显示正负数的符号：%+d与%d%n", 99, -99)
        //补O使用
        System.out.printf("补0使用：%05d%n", 7)
        //空格使用
        System.out.printf("空格使用Tab键的效果是：% 10d%n", 7)
        //,使用
        System.out.printf(",使用：%,d%n", 9989997)
        //空格和小数点后面个数
        System.out.printf("一本书的价格是：% .5f元%n", 49.8)


        System.out.printf("%n")
        System.out.printf("Date使用%n")
        System.out.printf("%n")


        val date = Date()
        //c的使用
        System.out.printf("全部日期和时间信息：%tc%n", date)//全部日期和时间信息：星期五 八月 02 10:57:37 CST 2019
        //f的使用
        System.out.printf("年-月-日格式：%tF%n", date)//年-月-日格式：2019-08-02
        //d的使用  月/日/年格式：08/02/19
        System.out.printf("月/日/年格式：%tD%n", date)
        //r的使用 HH:MM:SS PM格式（12时制）：10:57:37 上午
        System.out.printf("HH:MM:SS PM格式（12时制）：%tr%n", date)
        //t的使用 HH:MM:SS格式（24时制）：10:57:37
        System.out.printf("HH:MM:SS格式（24时制）：%tT%n", date)
        //R的使用 HH:MM格式（24时制）：10:57
        System.out.printf("HH:MM格式（24时制）：%tR", date)


        System.out.printf("%n")
        System.out.printf("B使用%n")
        System.out.printf("%n")

        //b的使用，月份简称 英文月份简称：Aug
        System.out.printf(Locale.US, "英文月份简称：%tb%n", date)
        //本地月份简称：八月
        System.out.printf("本地月份简称：%tb%n", date)
        //B的使用，月份全称  英文月份全称：August
        str = String.format(Locale.US, "英文月份全称：%tB", date)
        println(str)
        //本地月份全称：八月
        System.out.printf("本地月份全称：%tB%n", date)
        //a的使用，星期简称 英文星期的简称：Fri
        str = String.format(Locale.US, "英文星期的简称：%ta", date)
        println(str)
        //A的使用，星期全称 本地星期的简称：星期五
        System.out.printf("本地星期的简称：%tA%n", date)
        //C的使用，年前两位 年的前两位数字（不足两位前面补0）：20
        System.out.printf("年的前两位数字（不足两位前面补0）：%tC%n", date)
        //y的使用，年后两位
        System.out.printf("年的后两位数字（不足两位前面补0）：%ty%n", date)
        //j的使用，一年的天数
        System.out.printf("一年中的天数（即年的第几天）：%tj%n", date)
        //m的使用，月份
        System.out.printf("两位数字的月份（不足两位前面补0）：%tm%n", date)
        //d的使用，日（二位，不够补零）
        System.out.printf("两位数字的日（不足两位前面补0）：%td%n", date)
        //e的使用，日（一位不补零）
        System.out.printf("月份的日（前面不补0）：%te", date)
        //H的使用
        System.out.printf("2位数字24时制的小时（不足2位前面补0）:%tH%n", date)
        //I的使用
        System.out.printf("2位数字12时制的小时（不足2位前面补0）:%tI%n", date)
        //k的使用
        System.out.printf("2位数字24时制的小时（前面不补0）:%tk%n", date)
        //l的使用
        System.out.printf("2位数字12时制的小时（前面不补0）:%tl%n", date)
        //M的使用
        System.out.printf("2位数字的分钟（不足2位前面补0）:%tM%n", date)
        //S的使用
        System.out.printf("2位数字的秒（不足2位前面补0）:%tS%n", date)
        //L的使用
        System.out.printf("3位数字的毫秒（不足3位前面补0）:%tL%n", date)
        //N的使用
        System.out.printf("9位数字的毫秒数（不足9位前面补0）:%tN%n", date)
        //p的使用
        str = String.format(Locale.US, "小写字母的上午或下午标记(英)：%tp", date)
        println(str)
        System.out.printf("小写字母的上午或下午标记（中）：%tp%n", date)
        //z的使用
        System.out.printf("相对于GMT的RFC822时区的偏移量:%tz%n", date)
        //Z的使用
        System.out.printf("时区缩写字符串:%tZ%n", date)
        //s的使用
        System.out.printf("1970-1-1 00:00:00 到现在所经过的秒数：%ts%n", date)
        //Q的使用
        System.out.printf("1970-1-1 00:00:00 到现在所经过的毫秒数：%tQ%n", date)
    }
}
