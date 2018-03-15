---
layout: post
title:  "CContentAssistProcessor字节码文件分析"
categories: java&jvm
tags:  java jvm Byte-code
author: GuoZhongXia
---

* content
{:toc}

# [一、相关说明]()

## [1.1]()、jvm及字节码

​        1.1.1、JVM是Java Virtual Machine（Java虚拟机）的缩写。Java语言使用Java虚拟机屏蔽了与具体平台相关的信息，使得Java语言编译程序只需生成在Java虚拟机上运行的目标代码（字节码），就可以在多种平台上不加修改地运行。Java虚拟机在执行字节码时，把字节码解释成具体平台上的机器指令执行。这就是Java的能够“一次编译，到处运行”的原因。

​       (本段摘录自百度百科https://baike.baidu.com/item/JVM/2902369?fr=aladdin)

 

​         1.1.2、JVM及java字节码的相关技术参考了以下文档：

A、《Java虚拟机：JVM高级特性与最佳实践.周志明》，部分截图摘录自该文档。

B、The JavaTM Virtual Machine Specification

​       https://docs.oracle.com/javase/specs/jvms/se6/html/VMSpecTOC.doc.html

C、The class File Format

​       https://docs.oracle.com/javase/specs/jvms/se6/html/ClassFile.doc.html#74353

D、Opcode Mnemonics by Opcode

​       https://docs.oracle.com/javase/specs/jvms/se6/html/Mnemonics.doc.html

E、Java虚拟机字节码指令

​       http://blog.csdn.net/wangxf_8341/article/details/50402525 

 

## [1.2]()、相关工具

1.2.1、UltraEdit 查看、修改二进制文件。

1.2.2、jclasslib、反编译.class文件，二进制信息将以java字节码指令助记符显示。

1.2.3、7zip、修改jar包中文件。

 

 

 

# [二、CContentAssistProcessor.class]()文件代码分析

## [2.1]()、Class字节码文件格式

![img](/css/clip_image001.jpg)

2.1.1、最开始的4个字节是Class文件的魔数(magic)标识0XCAFEBABE。

2.1.2、第二个信息是第5~8字节，存储着Class文件的版本号。第5~6个字节是次版本号;第7~8个字节是主版本。(下图的主版本号为0x34=52, 次版本号为0 表示是 jdk1.8.0)

2.1.3、紧接着2字节constant_pool_count是常量池数目。常量池数目标识着接下来的常量池有多少个常量。(数目为0x0168=360, 常量池索引计数从1开始，索引值为1~359个数量)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image004.jpg)

​              CContentAssistProcessor.class字节码开始到部分常量池的字节

 

​                                                               Class文件版本号

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image006.jpg)

## [2.2]()、常量池分析

### [2.2.1]()、常量池的项目类型

​       常量池有11种常量类型，所有类型的常量第一个信息，都是1字节的标志(tag, 取值为1~12)，标识着当前常量为哪种常量类型。如下图所示：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image008.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image010.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image012.jpg)

CONSTANT_String_info型常量的结构

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image014.jpg)

 

### [2.2.2]()、CContentAssistProcessor.class 的前3个常量分析

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image016.jpg)

⑴、第1(0x080018)与第2(0x08001A)个常量都以0x08开头， tag=0x08 是CONSTANT_String_info 型常量，0x18=24指向索引号为24的CONSTANT_Utf8_info字符串字面量的索引; 而0x1A=26指向索引号为26的CONSTANT_Utf8_info字符串字面量的索引。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image018.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image020.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image022.jpg)

⑵、第3个常量的字节为0x010003282949，以0x01开头，tag=0x01是CONSTANT_Utf8_info类型常量，数目为0x03, 字符串为0x282949 ="()I"，是一个返回值为int的方法描述符。

⑶、接下来的第4~10个常量都为CONSTANT_Utf8_info类型常量，都是某个方法的描述符。

 

### [2.2.3]()、CContentAssistProcessor.class最后一个常量的定位。

⑴、通过jclasslib软件，我们可以很容易的知道，第359个常量也为CONSTANT_Utf8_info类型常量，其字符串为“StackMapTable”。

⑵、在UltraEdit打开的CContentAssistProcessor.class文档中，切换为字符显示，查找“StackMapTable”，在整个文件中只有一个匹配项目。把光标定位到最后的"e"字符，然后切换为十六进制显示，得到其所在的字节为 0x0215e，如下图所示：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image024.jpg)

常量池分析到此为此，其他常量的分析类似。



### [2.2.4]()、常量池中的11种数据类型结构总表

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image026.jpg)

## [2.3]()、访问标志

紧跟常量池后面的2个字节为访问标志(access_flags), 其值为0x0021, 表示public类型的类。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image028.jpg)

## [2.4]()、类/父类索引与接口索引集合

接着的3个或以上的u2类型共6个或以上的字节(从索引为0x2161字节开始)的信息为类索引、父类索引，及接口索引集合。

2.4.1、类索引this_class, 其值为0x00A9 = 169, 对应着常量池索引为169，类型为CONSTANT_Class_info的常量，指向路径为“org/eclipse/cdt/internal/ui/text/contentassist/CContentAssistProcessor”的类。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image030.jpg)

 

2.4.2、父类索引super_class其值为0x00AC = 172,对应着常量池索引为172，类型为CONSTANT_Class_info的常量，指向路径为“org/eclipse/cdt/internal/ui/text/contentassist/ContentAssistProcessor”的类。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image032.jpg)

 

2.4.3、接口索引数目与接口索引集合

指向interfaces_count是索引为0x2165开始的2个字节，其值为0，表示该类没有实现任何接口。故后面的索引表容量为0，即interfaces不再占用字节。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image034.jpg)

  

## [2.5]()、字段表集合

接下来的2字节0x0004表示fields_count数目为4。字段表包含了类变量(即全局静态变量)或实例变量(即全局非静态变量)。字段表结构与访问标志(access_flags)如下：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image036.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image038.jpg)

### [2.5.1]()、分析第1个字段

access_flag=2,private; name_index=0x26=38;descriptor_index=0xC7=199; attributes_count=0

第38个与199个常量分别如下图所示

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image040.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image042.jpg)

即该成员变量为：privateActivationSet fReplacementAutoActivationCharacters;

### [2.5.2]()、分析第2个字段

access_flag=2,private; name_index=0x24=36;descriptor_index=0xC7=199; attributes_count=0

第36个常量如下图所示

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image044.jpg)

即该成员变量为：privateActivationSet fCContentAutoActivationCharacters;

### [2.5.3]()、分析第3个字段

access_flag=2,private;  name_index=0x27=39["fValidator"];descriptor_index=0xD3=211["IContextInformationValidator "];  attributes_count=0

即该成员变量为：private  IContextInformationValidator  fValidator;

2.5.4、分析第4个字段

access_flag=0x12,private final;  name_index=0x25=37["fEditor"];descriptor_index=0xD4=212["IEditorPart"];  attributes_count=0

即该成员变量为：private  final  IEditorPart  fEditor;

 

## [2.6]()、方法表相关介绍

方法表的内容与字段表类似，表结构的描述几乎是完全一致的。索引为0x2189开始的2个字节，其值为0x000B=11, 表示该类有11个方法。方法表结构与方法访问标志如下：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image046.jpg)

  

## [2.7]()、属性表相关介绍

方法表中将会出现“Code”等属性，其中“Code”属性用于存在字节码指令，其结构如下：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image048.jpg)



## [2.8]()、方法与字段的描述符

描述符是用来描述字段的数据类型，方法的参数列表(数量、类型及顺序)与返回值。基本数据类型用1个大写字母表示。对象类型用‘L’加对象对应的类的全限定名来表示。而数组类型，每一维度，用一个前置的“[”表示，如int[][]将表示为[[I。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image050.jpg)

## [2.9]()、分析第1个方法

上面提到，该类一共有11个方法，第一个方法从索引为0x218b字节开始。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image052.jpg)

### [2.9.1]()、方法表属性

access_flag=1,public;  name_index=0x0c=12["<init>"],这是实例构造器方法;descriptor_index=0xF6=246

["(Lorg/eclipse/ui/IEditorPart; Lorg/eclipse/jface/text/contentassist/ ContentAssistant;  Ljava/lang/String;)V"];  

即方法头应为：

public CContentAssistProcessor(IEditorParteditor, ContentAssistant assistant, String part);

attributes_count=01, 说明方法表后面有1个属性。

### [2.9.2]()、Code属性表

⑴、 看看紧接着在索引0x2193的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x2195的4个字节(0x5C=92), 表示code属性除了前面2个字段的6个字节，还有92个字节(从索引0x2199开始)。

max_stack=03, 该方法有包括"this"在内的4个操作数，操作数栈不会超过3这个深度。

max_locals=04, 该方法有包括"this"在内的4个操作数。

⑵、4字节的code_length=0x0c=12, 即后面有12个字节码指令：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image054.jpg)

⑶、其中 

0x2A      aload_0 将第1个引用类型本地变量推送至栈顶

0x2C             aload_2 将第3个引用类型本地变量推送至栈顶

0x2D      aload_3 将第4个引用类型本地变量推送至栈顶

注：aload_0在非静态方法中,表示对this的操作, 在static 方法中,表示对方法的第1个参数的操作。接下来的是3个引用型参数。

0xB7     invokespecial这条指令是以栈顶的引用类型(即第1个参数this)所指向的对象作为方法接收者，调用对对象的父类构造方法，实例初始化方法，私有方法。

0x0149=329 这两个字节，是常量池的方法常量的索引，是invokespecial指令要执行的方法

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image056.jpg)

即前面6个字节的代码可翻译为

this.super(assistant, part);

⑷、再接下来的0x2A与0x2B指令是将第1与第2个引用类型本地变量推送至栈顶

0xB5             putfield   为指定类实例的域赋值。

0x012F=303 这两个字节，是常量池的字段常量的索引，是putfield指令要赋值字段。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image058.jpg)

即前面6个字节的代码可翻译为

this. fEditor= editor;

⑸、0xB1                   return       从当前方法返回void。

⑹、根据上面的分析，这段代码可以表示为：

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image060.jpg)

⑺接下来的exception_table_length=0(在索引0x21ad的2个字节)表示该方法没有异常表。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image062.jpg)

 

⑻、接下来的是Code属性表中嵌套的2个属性表。

先来分析第一个属性

①、 前面2字节(索引0x21b1)为0x0162=354,指向常量池的String常量" LineNumberTable ".

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image064.jpg)

②、 接下来的4字节0x0000000E=14是行号属性表的长度，即后面有14个字节。

③、 紧随其后的2字节((在索引0x21b7处)为0x0003，表示line_number_info的数目，line_number_info表示字节码与java源码对应关系，是两个u2类型的数据项，第一个u2表示字节码字节索引，第二个u2表示java源码行号。即2 + 4 *3 =14个字节，刚好与上面吻合。 

​                   字节码字节索引                java源码行号

A、                     0x00                                               0x89=137            

B、                     0x06                                               0x8A=138

C、                     0x0B                                              0x8B=139         (即B1指令的相对位置)

与源代码文件吻合

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image066.jpg)

再来分析第二个属性

Ⅰ、前面2字节(索引0x21c5)为0x0163=355,指向常量池的String常量" LocalVariableTable ".

局部变量表LocalVariableTable的结构如下图所示

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image068.jpg)

Ⅱ、紧接着的4字节0x0000002A=42，表示局部变量表属性除了前面2个字段的6个字节，还有42个字节(从索引0x21cb开始)。

Ⅲ、下1个u2类型的数据为0x04表示有4个局部变量信息，与上面的分析一致。

字节长度共2 + 10*4 ＝42字节，与上面相符。

start_pc与length属性分别代表了这个局部变量作用域开始字节码偏移量与范围长度。

name_index与descriptor_index指向常量池的字符串常量，表示局部变量名称及其描述符。

index是这个局部变量在变量表中的位置。

 

Ⅳ、变量     start_pc              length           name_index        descriptor_index              index

​       1            0x00                    0x0C                    0x0088=136               0x00C6=198         0

​       2            0x00                    0x0C                    0x0023=35                 0x00D4=212         1

​       3            0x00                    0x0C                    0x0017=23                 0x00D1=209         2

​       4            0x00                    0x0C                    0x0076=118               0x00BC=188          3****

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image070.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image072.jpg)

第1与第2个变量this与editor；

 ![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image074.jpg)

第3与第4个变量assistant与partition。

综上所述，Code属性的代码信息从索引0x2199开始，到0x 21F4结束，刚好92个字节。

 

## [2.10]()、分析第2-11个方法

### [2.10.1]()、第2个方法从字节索引0x21F5开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image076.jpg)

①、方法表属性

access_flag=1,public;  name_index=0x33=51["<getContextInformationValidator>"];descriptor_index=0xED=237

["()Lorg/eclipse/jface/text/contentassist/IContextInformationValidator"];  

即方法头应为：

public IContextInformationValidator getContextInformationValidator();

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x21FD的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x21FF的4个字节(0x52=82),表示code属性除了前面2个字段的6个字节，还有82个字节(从索引0x2203开始)。

### [2.10.2]()、第3个方法从字节索引0x2255开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image078.jpg)

①、方法表属性

access_flag=4, protected;  name_index=0x2A=42["<filterAndSortProposals>"];descriptor_index=0xFA=250

["(Ljava/util/List;  Lorg/eclipse/core/runtime/ IProgressMonitor;       Lorg/eclipse/cdt/ui/text/contentassist/ContentAssistInvocationContext;)Ljava/util/List"];  

即方法头应为：

public List<? > filterAndSortProposals(List<?>proposals,

​                     IProgressMonitormonitor, ContentAssistInvocationContext context) ;

②、attributes_count=02,说明方法表后面有2个属性。

 

③、紧接着在索引0x225D的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x225F的4个字节(0x037C=892), 表示code属性除了前面2个字段的6个字节，还有892个字节(从索引0x2263开始)。

即Code属性最后一个字节索引为：0x2262 + 0x37C = 0x25DE。

 ④、从0x25DF开始是第2个属性的数据。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image080.jpg)

0x25DF开始的2个字节(0x0165=357["Signature "]), 说明后面是一个Signature属性，结构为：

Signature_attribute {

​      u2 attribute_name_index;

​      u4 attribute_length;

​      u2 signature_index;

}

⑤、Signature属性是泛型方法的特征，该属性长度为2字节，最后2字节0xFC=252指向字符串常量，其值为：

(Ljava/util/List<Lorg/eclipse/jface/text/contentassist/ICompletionProposal;>;Lorg/eclipse/core/runtime/IProgressMonitor;Lorg/eclipse/cdt/ui/text/contentassist/ContentAssistInvocationContext;)Ljava/util/List<Lorg/eclipse/jface/text/contentassist/ICompletionProposal;>;

⑥综上所述，第3个方法应为：

protected List<ICompletionProposal>filterAndSortProposals(List<ICompletionProposal> proposals,  IProgressMonitor monitor,  ContentAssistInvocationContext context)

 

### [2.10.3]()、第4个方法从字节索引0x25E7开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image082.jpg)

①、方法表属性

access_flag=2, private;  name_index=0x31=49["<getCompletionFilter>"];descriptor_index=0xE4=228

["()Lorg/eclipse/cdt/ui/text/contentassist/IProposalFilter;"];  

即方法头应为：

private IProposalFiltergetCompletionFilter();

 

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x25EF的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x25F1的4个字节(0xF7=247), 表示code属性除了前面2个字段的6个字节，还有247个字节(从索引0x25F5开始)。

即第4个方法属性结束的字节索引为0x25F4 + 0xF7 = 0x26EB。

 

### [2.10.4]()、第5个方法从字节索引0x26EC开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image084.jpg)

①、方法表属性

access_flag=4, protected;  name_index=0x29=41["<filterAndSortContextInformation>"]; descriptor_index=0xF7=247

["(Ljava/util/List;  Lorg/eclipse/core/runtime/ IProgressMonitor;) Ljava/util/List"];;  

即方法头应为：

protected List<? >filterAndSortContextInformation(List<? > contexts,  IProgressMonitor monitor) 

②、attributes_count=02,说明方法表后面有2个属性。

 

③、紧接着在索引0x26F4的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x26F6的4个字节(0x52=82), 表示code属性除了前面2个字段的6个字节，还有82个字节(从索引0x26FA开始)。

即Code属性最后一个字节索引为：0x26F9 + 0x52 = 0x274B。 

④、从0x274C开始是第2个属性的数据。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image086.jpg)

0x274C开始的2个字节(0x0165=357["Signature "]), 说明后面是一个Signature属性，结构为：

Signature_attribute {

​      u2 attribute_name_index;

​      u4 attribute_length;

​      u2 signature_index;

}

⑤、Signature属性是泛型方法的特征，该属性长度为2字节，最后2字节0xFB=251指向字符串常量，其值为：

(Ljava/util/List<Lorg/eclipse/jface/text/contentassist/IContextInformation;>;    Lorg/eclipse/core/runtime/IProgressMonitor;)Ljava/util/List<Lorg/eclipse/jface/text/contentassist/IContextInformation;>;

⑥综上所述，第5个方法应为：

protected List<IContextInformation>filterAndSortContextInformation(List<IContextInformation> contexts, IProgressMonitormonitor);

 

### [2.10.5]()、第6个方法从字节索引0x2754开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image088.jpg)

①、方法表属性

access_flag=1, public;  name_index=0x84=132["<setReplacementAutoActivationCharacters>"];descriptor_index=0xDA=218

["(Ljava/lang/String;)V"];  

即方法头应为：

public voidsetReplacementAutoActivationCharacters(String activationSet);

 

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x275C的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x275E的4个字节(0x45=69), 表示code属性除了前面2个字段的6个字节，还有69个字节(从索引0x2762开始)。

即第6个方法属性结束的字节索引为0x2761 + 0x45 = 0x27A6。

 

### [2.10.6]()、第7个方法从字节索引0x27A7开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image090.jpg)

①、方法表属性

access_flag=1, public;  name_index=0x82=130["<setCContentAutoActivationCharacters>"];descriptor_index=0xDA=218

["(Ljava/lang/String;)V"];  

即方法头应为：

public void setCContentAutoActivationCharacters(StringactivationSet);

 

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x27AF的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x27B1的4个字节(0x45=69), 表示code属性除了前面2个字段的6个字节，还有69个字节(从索引0x27B5开始)。

即第7个方法属性结束的字节索引为0x27B4 + 0x45 = 0x27F9。

 

### [2.10.7]()、第8个方法从字节索引0x27FA开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image092.jpg)

①、方法表属性

access_flag=4, protected;  name_index=0x1E=30["<createContext>"];descriptor_index=0xF4=244

["(Lorg/eclipse/Jface/text/ITextViewer;IZ) Lorg/eclipse/cdt/ui/text/contentassist/ContentAssistInvocationContext;"];  

即方法头应为：

protected ContentAssistInvocationContextcreateContext(ITextViewer viewer, int offset, boolean isCompletion);

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x2802的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x2804的4个字节(0x253=595), 表示code属性除了前面2个字段的6个字节，还有595个字节(从索引0x2808开始)。

即第8个方法属性结束的字节索引为0x2807 + 0x253 = 0x2A5A。

### [2.10.8]()、第9个方法从字节索引0x2A5B开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image094.jpg) 

①、方法表属性

access_flag=2, private;  name_index=0x7F=127 ["<replaceDotWithArrow>"];descriptor_index=0xF9=249

["(Lorg/eclipse/Jface/text/ITextViewer;IZ Lorg/eclipse/cdt/internal/ui/text/contentassist/CContentAssistInvocationContext;C)Lorg/eclipse/cdt/internal/ui/text/contentassist/CContentAssistInvocationContext;"];  

即方法头应为：

private CContentAssistInvocationContextreplaceDotWithArrow(ITextViewer viewer, int offset,

​                     booleanisCompletion, CContentAssistInvocationContext context, char activationChar)

 

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x2A63的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x2A65的4个字节(0xF4=244), 表示code属性除了前面2个字段的6个字节，还有244个字节(从索引0x2A69开始)。

即第9个方法属性结束的字节索引为0x2A68 + 0xF4 = 0x2B5C。

 

### [2.10.9]()、第10个方法从字节索引0x2B5D开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image096.jpg) 

①、方法表属性

access_flag=2, private;  name_index=0x2E=46 ["<getActivationChar>"]; descriptor_index=0xE9=233

["(Lorg/eclipse/Jface/text/ITextViewer;I) C"];  

即方法头应为：

private char getActivationChar(ITextViewer viewer,int offset)

 

②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x2B65的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x2B67的4个字节(0xA0=160), 表示code属性除了前面2个字段的6个字节，还有160个字节(从索引0x2B6B开始)。

即第10个方法属性结束的字节索引为0x2B6A + 0xA0 = 0x2C0A。

### [2.10.10]()、第11个方法从字节索引0x2C0B开始

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image098.jpg) 

①、方法表属性

access_flag=4, protected;  name_index=0x8C=140 ["<verifyAutoActivation>"]; descriptor_index=0xEA=234

["(Lorg/eclipse/Jface/text/ITextViewer;I) Z"];  

即方法头应为：

protected booleanverifyAutoActivation(ITextViewer viewer, int offset)

 ②、attributes_count=01,说明方法表后面有1个属性。

③、紧接着在索引0x2C13的2个字节(0x0160=352["Code"]), 说明后面是一个Code属性，接下来在索引0x2C15的4个字节(0x196=406), 表示code属性除了前面2个字段的6个字节，还有406个字节(从索引0x2C19开始)。接下来的4个字节表示max_stack与max_locals各为5与7。之后索引0x2C1D的4个字节(0xB6=182)表示有182个指令码，最后一个指令码(0xACireturn)的字节索引为0x2C20 +0xB6 = 0x2CD6。

 即第11个方法属性结束的字节索引为0x2C18 + 0x196= 0x2DAE。

 

## [2.1]()1、属性表

2.10.1、接下来的2个字节(index: 0x2DAF)attributes_count= 2，表示最后有2个属性。

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image100.jpg)

2.10.2、分析第1个属性

①、在索引0x2DB1的2个字节 (0x0166=358["SourceFile"]),说明后面是一个SourceFile属性

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image102.jpg)

②、接下来在索引0x2DB3的4个字节(0x2=2), 是SourceFile属性后面字节的长度。

③、跟随其后索引0x2DB7的2个字节(0x015F=351["CContentAssistProcessor.java"]),是SourceFile文件名的字符串常量索引。 

2.10.3、分析第2个属性

④、在索引0x2DB9的2个字节 (0x0161=353["InnerClasses"]),说明后面是一个InnerClasses属性

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image104.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image106.jpg)

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image108.jpg)

​    内部类属性中，inner_class_info_index与outer_class_info_index都是指向常量池中CONSTANT_Class_info型常量的索引，分别代表了内部类和宿主类的符号引用。

   inner_name_index是指向常量池中CONSTANT_Utf8_info型常量的索引，代表内部类的名称，如果是匿名内部类，则为0。

​       inner_class_access_flags是内部类的访问标志，类似于类的access_flags。取值如上表。

⑤、接下来在索引0x2DBB的4个字节(0x12=18), 是InnerClasses属性后面字节的长度。

⑥、跟随其后索引0x2DBF的2个字节(0x02]), 是内部类的数目。

⑦、第1个内部类的值为 00 AA 00 A9 01 5D 00 0A

​    inner_class_info_index：0xAA=170:

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image110.jpg)

​       outer_class_info_index：0xA9=169:

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image112.jpg)

​       inner_name_index:    0x 015D = 349["ActivationSet"]

​       inner_class_access_flags:0x0A[private, static] 

即内部类可表示为:private static class CContentAssistProcessor. ActivationSet{};

 

⑧、第2个内部类的值为 00 AB 00 A9 01 5E 00 0A

inner_class_info_index：0xAB=171:

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image114.jpg)

​       outer_class_info_index：0xA9=169:

![img](file:///C:\Users\GUOXUE~1\AppData\Local\Temp\msohtmlclip1\01\clip_image115.jpg)

​       inner_name_index:    0x 015E = 350["CCompletionProposalWrapper"]

​       inner_class_access_flags:0x0A[private, static] 

即内部类可表示为:private static class CContentAssistProcessor. CCompletionProposalWrapper{};

  

 

 

 

 

 
