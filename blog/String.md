

```java
String str1 = "str";
String str2 = "ing";

String str3 = "str" + "ing";//常量池中的对象
String str4 = str1 + str2; //在堆上创建的新的对象
String str5 = "string";//常量池中的对象
System.out.println(str3 == str4);	//false
System.out.println(str3 == str5);//true
System.out.println(str4 == str5);//false
```

如果字符串在编译期间可以得到确定的内容，那么编译器会通过“常量折叠”的优化技术，计算表达式的值并且赋值给变量。可以进行常量折叠的情况：

1. 基本数据类型(byte、boolean、short、char、int、float、long、double)以及字符串常量
2. `final` 修饰的基本数据类型和字符串变量
3. 字符串通过 “+”拼接得到的字符串、基本数据类型之间算数运算（加减乘除）、基本数据类型的位运算（<<、>>、>>> ）

引用的值在程序编译期是无法确定的，编译器无法对其进行优化。对象引用和“+”的字符串拼接方式，实际上是通过 `StringBuilder` 调用 `append()` 方法实现的，拼接完成之后调用 `toString()` 得到一个 `String` 对象 ，不属于字符串常量池中的变量，而属于堆中的新对象，`str4`就是这样子的

添加`final`关键字后，编译器会将其当成常量处理，因此通过”常量折叠“后，c和d是相同的字符串常量池对象

```java
final String str1 = "str";
final String str2 = "ing";
// 下面两个表达式其实是等价的
String c = "str" + "str2";// 常量池中的对象
String d = str1 + str2; // 常量池中的对象
System.out.println(c == d);// true
```

使用`new`关键字创建的字符串对象，会在堆内存中创建一个对象，同时字符串常量池中如果没有字面量相同的对象则会另外创建，因此通过`new`创建的字符串对象，最多可能会生成两个对象

```java
String str1 = "abcd";
String str2 = new String("abcd");
String str3 = new String("abcd");
System.out.println(str1==str2);		// false
System.out.println(str2==str3);		// false
```



intern()操作，会去字符串常量池中查找是否有等于该字符串对象的引用，如果有则返回字符串常量池中的引用

```java
String str1= "abc";
String str2= new String("abc");
String str3= str2.intern();
System.out.println(str1==str2);     // false
System.out.println(str2==str3);     // false
System.out.println(str1==str3);     // true



String a =new String("abc").intern();
String b = new String("abc").intern();
System.out.println(a==b);     // true
```



