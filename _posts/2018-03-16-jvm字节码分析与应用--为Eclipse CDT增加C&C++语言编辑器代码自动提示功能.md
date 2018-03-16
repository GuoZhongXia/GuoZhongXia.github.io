---
layout: post
title:  "jvm字节码分析与应用--为Eclipse CDT增加C&C++语言编辑器代码自动提示功能"
categories: java&jvm
tags:  java jvm Byte-code
author: GuoZhongXia
---

* content
{:toc}
# [一、相关说明]()

## [1.1]()、jvm及字节码

​    1.1.1、JVM是Java Virtual Machine（Java虚拟机）的缩写。Java语言使用Java虚拟机屏蔽了与具体平台相关的信息，使得Java语言编译程序只需生成在Java虚拟机上运行的目标代码（字节码），就可以在多种平台上不加修改地运行。Java虚拟机在执行字节码时，把字节码解释成具体平台上的机器指令执行。这就是Java的能够“一次编译，到处运行”的原因。

​       (本段摘录自百度百科https://baike.baidu.com/item/JVM/2902369?fr=aladdin)

​         1.1.2、字节码详细分析讲解请参见《CContentAssistProcessor字节码文件分析》WORD文档。





## [1.2]()、技术背景

1.2.1、大家都熟悉Eclipse java编辑器的代码自动提示功能。

设置如下：Window菜单--Preferences--Java--Editor--ContentAssist--Auto Activation中勾选并填入java triggers“<=:.@()[]abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ”。

![img](/js/clip_image001.png)

 1.2.2、编辑java代码时，键入任何字符均会自动提示：

![img](/js/clip_image003.png)

1.2.3、但在Eclipse CDTC&C++编辑器中，没有提供代码自动提示的功能，只有按了快捷键[alt+(shift+)/]，才弹出提示。

其代码提示的设置如下：Window菜单--Preferences--C/C++--Editor--ContentAssist--Auto Activation。

![img](/js/clip_image005.png)

 

1.2.4、Eclipse CDT的 C&C++编辑器中只有在键入"."、"->"及"::"时才出自动弹出提示：

![img](/js/clip_image007.png)

![img](/js/clip_image009.png)

![img](/js/clip_image011.png)

![img](/js/clip_image013.png)

## [1.3]()、修改字节码要达到的目标

​         在Eclipse CDTC&C++编辑器中，实现与java编辑器一样，在键入任何有意义的代码字符时，都能自动提示的功能。

![img](/js/clip_image015.png)![img](/js/clip_image017.png)

![img](/js/clip_image019.png)![img](/js/clip_image021.png)

 

 

# [二、]()CDT编辑器自动提示功能分析

## [2.1]()、分析CDT Preferences的配置

2.1.1、我们在Preferences 中的CDT Content Assist--AutoActivation中只选中某一项，如

![img](/js/clip_image023.png)

 

2.1.2、在workspace所在目录，搜索与CDT相关的配置文件，我们搜索到如下文件：

![img](/js/clip_image025.png)

 

通过比较*.prefs的文件，我们发现在org.eclipse.cdt.ui.prefs保存content assist的配置项：

![img](/js/clip_image027.png)

## [2.2]()、分析CDT content assist的相关代码

2.2.1、在eclipse的plugin目录中把org.eclipse.cdt.ui.source_6.2.0.201710130142.jar文件复制出来，解压后，搜索“*assist”，结果如下：

![img](/js/clip_image029.png)

从源码文件的命名上，我们可以看出，CContentAssistProcessor.java、ContentAssistPreference.java及ContentAssistProcessor.java等文件，与我们要做的修改可能相关，阅读这几个文件的源码，我们可以进一步确定与content assist相关代码如下：

①、ContentAssistPreference.java文件，在这里读取配置文件保存的提示字符设置：

![img](/js/clip_image031.png)

②、CContentAssistProcessor.java文件，在这里做自动提示字符的校验：

![img](/js/clip_image033.png)

 

③、综上所述，我们只需做2点修改，

A、在ContentAssistPreference.java的文件中，修改configureActivationCharacters方法的triggers局部变量的赋值。

B、在CContentAssistProcessor.java的文件中，修改verifyAutoActivation方法最后的返回值为true。

但如果我们在源代码中修改，要下载配置Eclipse插件的相关编译环境，并且在编译过程中，难免要解决这样那样的依赖库与代码出错。

若能通过修改这两个源代码文件对应的class文件来实现上述功能，则是很好的一种途径。下面章节分别对这两个源文件的class字节码文件所做修改进行详述。

 

# [三、相关字节码文件分析]()与修改

## [3.1]()、ContentAssistPreference.class字节码文件分析

3.1.1、我们从上节知道，只需修改configureActivationCharacters方法中triggers的值。如下:

![img](/js/clip_image035.png)

 

3.1.2、用jclasslib打开ContentAssistPreference.class,定位到Methods-configureActivationCharacters-Code

![img](/js/clip_image037.png)

 

对比java源码与字节码，我们可以很明显地看出，17行有字节码指令“ldc#2 <.>”与“.”字符相关， 查指令表得知这条指令是加载常量池中的常量到栈顶(注：该指令为loadconst 的缩写)，再结合上下的源代码与字节码：

①、在17行上面的5~14行字节码是调用3次IPreferenceStore.getBoolean方法加载3个boolean类型变量(注:在字节码存储中，是以整形0与1表示boolean值)。

14行的 istore 5是保存栈顶(IPreferenceStore.getBoolean获取AUTOACTIVATION_TRIGGERS_DOUBLECOLON的值放在栈顶)的值到第6个局部变量useDoubleColonAsTrigger。接下来的iload_3加载第4个局部变量useDotAsTrigger到栈顶，作为下面判断指令的参数。这与源代码一致。

 

②、在18行的astore_2是把加载到栈顶的字符串"."，保存到第3个(引用型)变量，即triggers。

 

根据以上分析，我们最终明确了需要把17行的常量池字符串常量"."修改为“<=:.@()[]abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ”。

## [3.]()2、修改ContentAssistPreference.class字节码文件

3.2.1、修改该字节码文件需要用到jclasslib的jclasslib-browser.jar与jclasslib-library.jar。

​       在Eclipse中新建一个java项目，把上述两个jar文件加入依赖库。

 

3.2.2、点击jclasslib的17行ldc #2的链接，跳转到常量池的字符串常量中，如下：

![img](/js/clip_image039.png)![img](/js/clip_image041.png)

​     我们记下它的值是存放在常量池第24个为utf8_info常量。

 

3.2.3、在java项目测试类main方法中，编写如下代码(在i==24的判断中，24即为常量索引)：

![img](/js/clip_image043.png)

保存并执行后，用jclasslib打开新生成的class文件，即可看到修改已经生效

![img](/js/clip_image045.png)

 

## [3.]()3、修改CContentAssistProcessor.class字节码文件

3.3.1、按上面分析，我们需要修改最后一个方法verifyAutoActivation()最后的返回值为true。

用jclasslib打开CContentAssistProcessor.class文件, 定位到Methods--verifyAutoActivation--Code, 看到最后两条指令为iconst_0与ireturn, 从而确定我们只需修改iconst_0与iconst_1即可，iconst_N是把int型N放到栈顶，做为下条指令的参数。

![img](/js/clip_image047.png)

 

3.3.2、参考另个文档《CContentAssistProcessor字节码文件分析》中的分析，得知verifyAutoActivation()的ireturn单字节指令(字节码为0xAC)的字节索引为0x2CD6, 即在字节索引为0x2CD5的iconst_0指令(字节码为0x03)，要修改为iconst_1(字节码为0x04)即可。

![img](/js/clip_image049.png)

 

3.3.3、用UltraEdit打开CContentAssistProcessor.class文件，修改并保存。

用jclasslib打开修改后的CContentAssistProcessor.class文件，检查是否修改成功：

![img](/js/clip_image051.png)

 

## [3.]()4、更新 CDT plugin。

3.4.1、打开Eclipse所在目录中的plugin目录，并把“org.eclipse.cdt.ui_xxx.jar”文件做备份。

![img](/js/clip_image053.png)

3.4.2、用7-zip打开jar包文件，进入到“\org\eclipse\cdt\internal\ui\text\contentassist\”目录，把CContentAssistProcessor.class与ContentAssistPreference.class文件拖到备份目录备份。

 

3.4.3、删除7-zip中的两个class文件，然后把修改后的CContentAssistProcessor.class与ContentAssistPreference.class文件拖进7-zip即可。

## [3.5]()、打开/重启Eclipse，校验修改是否成功。

如没有意外，即可以达到1.3节所看到的“修改字节码要达到的目标”。

 
