---
layout: post
title:  "Objective-C开发编程规范"
categories: Objective-C 
tags:  Objective-C 开发规范
author: GuoZhongXia
---

* content
{:toc}
# Objective-C开发编程规范






# 一、编程规约

## (一)命名规约

**1.1、**【强制】 代码中的命名除了实例私有变量与宏定义，均不能以*__下划线__*开始。

**1.2、**【强制】 代码中的命名除了宏定义，均不能以*__下划线__*结束。

**1.3、**【强制】代码中的命名均不能以*__美元符号__*开始，也不能以*__美元符号__*结束。

*__反例__*： \$Object  / name\$  / Object\$      

**1.4、**【强制】代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。

**说明：**正确的英文拼写和语法可以让阅读者易于理解，避免歧义。注意，即使纯拼音命名方式也要避免采用。

*__正例__*： google/ zjht/jingdong/jd/qq/guangzhou等国际通用或约定俗成的，可视同英文。

*__反例__*： DaZhePromotion [打折] / getPingfenByName()[评分] / int 某变量 = 3

**1.5、**【强制】类/协议名,  函数名,   及头文件名使用UpperCamelCase大驼峰命名惯例。

*__正例__*：NSLocking/NSCopying / XmlService / TcpUdpDeal / float NSHeight() /  NSLocale.h

*__反例__*：nsLocking /NsCopying/ XMLService / TCPUDPDeal  / float nsHeight()/ nsLocale.h

**1.6、**【强制】方法名/参数名/属性/委托/成员变量/局部变量统一使用lowerCamelCase 命名惯例。

*__正例__*： localValue / getHttpMessage()/ inputUserId  /  (BOOL)windowShouldClose:(id)sender;

**1.7、**【强制】常量名使用大驼峰命名惯例; 或全部大写，单词间用下划线隔开；力求语义表达清楚。 

*__正例__*：EOCViewClassAnimationDuration， MAX_STOCK_COUNT,  NSLightGray

*__反例__*：eocViewClassAnimationDuration，  MAX_COUNT

**1.8、**【强制】抽象类命名使用 Abstract 或 Base 开头；异常类命名使用 Exception 结尾；测试类命名以它要测试的类的名称开始，以 Test 结尾。

**1.9、**【强制】杜绝完全不规范的缩写，避免望文不知义。

*__反例__*：         AbstractClass“缩写”命名成 AbsClass；condition“缩写”命名成condi，此类随意缩写严重降低了代码的可阅读性。

**1.10、**【推荐】如果使用到了设计模式，建议在命名中体现出具体模式。

**说明：**将设计模式体现在名字中，有利于阅读者快速理解架构设计思想。

*__正例__*：@interface OrderFactory;   @interface LoginProxy;  @interface ResourceObserver;

 

**1.11、**【参考】枚举成员建议带上 Enum 类型名称前缀，Enum类型及成员名使用大驼峰命名惯例。

*__正例__*：枚举名字：UIControlState，成员名称：UIControlStateNormal / UIControlStateDisabled



## (二)常量定义

**2.1、**【强制】不允许出现任何魔法值（即未经定义的常量）直接出现在代码中。 

 *__反例__*：

```objective-c
  NSString *orderIdString = [NSString stringWithFormat:@"Id#zjht_%@%@",orderId];

  self.orderLabel.attributedText = orderIdString; 
```

**2.2、**【强制】long 或者 Long 初始赋值时，必须使用大写的 L，不能是小写的 l，小写容易跟数字1混淆，造成误解。

 **说明：**constLong time = 86400l; 写的是数字的 864001，还是 Long 型的 86400?

 

**2.3、**【强制】不要使用 #define 宏来定义常量。若是多个相关整型常量，尽量使用枚举(参见2.5)。 

*__正例__*：  const float GSTitlesViewH = 35.0;  // 标题按钮的高度

*__反例__*：  #defineGSTitlesViewH 35.0  // 标题按钮的高度

**说明：**#define定义的常量，预编译时，要把宏定义的值代替用到这个常量的代码，会影响编译速度。

**2.4、**【推荐】不要使用一个常量类维护所有常量，应该按常量功能进行归类，分开维护。如：缓存 相关的常量放在类：CacheConsts 下；系统配置相关的常量放在类：ConfigConsts 下。

**说明：**大而全的常量类，非得使用查找功能才能定位到待修改的常量，不利于理解和维护。

 

**2.5、**【推荐】如果变量值仅在一个范围内变化用Enum 类。如果还带有名称之外的延伸属性，必须使用 Enum 类，下面*__正例__*中的数字就是延伸信息，表示星期几。

*__正例__*：

```objective-c
  typedef NS_ENUM(NSInteger, DayEnum) {
      MONDAY    = 1, 
      TUESDAY   = 2, 
      WEDNESDAY = 3, 
      THURSDAY  = 4, 
      FRIDAY    = 5, 
      SATURDAY  = 6, 
      SUNDAY    = 7  
  };
```

 

 

## (三)格式规约

**3.1、**【强制】大括号的使用约定, 如果大括号内为空，则简洁地写成{}即可，不需要换行；如果为非空代码块，则按如下处理：

 1） 左大括号前不换行。

 2） 左大括号后换行。

 3） 右大括号前换行。

 4） 右大括号后还有else 等代码则不换行；表示终止右大括号后必须换行。

**3.2、**【强制】 左小括号和后一个字符之间不出现空格；同样，右小括号和前一个字符之间也不出现空格。详见*__第 6 条下方正例__*提示。

**3.3、**【强制】if/for/while/switch/do 等保留字与括号之间都必须加空格。

**3.4、**【强制】一元运算符与变量之间不能有空格。

*__正例__*：

```objective-c
  !bValue;   ~iValue;   ++iCount;      *strSource;    &fSum;
```

**3.5、**【强制】任何二元、三元运算符左右必须加一个空格。

**说明：**运算符包括赋值运算符=、逻辑运算符、加减乘除符号等。

**3.6、**【强制】缩进采用 4 个空格，禁止使用 tab 字符。

**说明：**  如果使用tab 缩进，必须设置 1 个 tab 为 4 个空格。在 Xcode > Preferences > TextEditing 将 Tab 和自动缩进都设置为 4 个空格。

*__正例__*： （涉及 1-6 点）

```objective-c
  int main(int argc, char * argv[]) {
      // 缩进 4 个空格
      NSString *say = @"hello";
      // 运算符的左右必须有一个空格
      int flag = 0;
      // 关键词if与括号之间必须有一个空格，括号内的f 与左括号，0 与右括号不需要空格
      if (flag == 0) {
          NSLog(@"%@",say);    
      }
      // 左大括号前加空格且不换行；左大括号后换行
      if (flag == 1) {
          NSLog(@"world");       
          // 右大括号前换行，右大括号后有 else，不用换行
      } else{
          NSLog(@"ok");      
      } // 在右大括号后直接结束，则必须换行
  }
```

**3.7、**【强制】表达式中若同时存在多个不同运算符时，应使用括号来明确优先级。

**说明：**在多个不同的运算符同时存在时应合理使用括号，不要盲目依赖操作符优先级。

因为有时候不能保证阅读你代码的人就一定能了解你写的算式里面所有操作符的优先级。

*__正例__*：

```objective-c
  ((val << (howmuch++)) | (val >> (MAS_NSUINT_BIT - howmuch)))
```

*__反例__*：

```objective-c
  val << howmuch++ | val >> MAS_NSUINT_BIT - howmuch
```

**3.8、**【强制】单行字符数限制不超过 120 个，超出需要换行，换行时遵循如下原则：

 1） 第二行相对第一行缩进 4 个空格，从第三行开始，不再继续缩进，参考示例。

 2） 运算符与下文一起换行。

 3） 方法调用的点符号与下文一起换行。

 4） 方法调用时，多个参数，需要换行时，在逗号后进行换行。

 5） 在括号前不要换行，见*__反例__*。

*__正例__*：

```objective-c
  // 超过120个字符的情况下，换行缩进4个空格，并且方法前的点符号一起换行, 伪代码
  CGFloat detailTextH = [addressModel.completeAddress boundingRectWithSize:CGSizeMake(
      Screen_Width - 20, maxHeight) options:NSStrin.. attributes:attribute context:nil]
      .size.height + 20;
```

*__反例__*：

```objective-c
  //超过 120 个字符的情况下，不要在括号前换行, 伪代码
  CGFloat detailTextH = [addressModel.completeAddress boundingRectWithSize:CGSizeMake
      (Screen_Width - 20, maxHeight) options:NSStri.. attributes:attribute context:nil]
      .size.height + 20;  

  //参数很多的方法调用可能超过 120 个字符，不要在逗号前换行
  NSLog(@"\n---Request---\n(url = %@;\nparam = %@;\nmethod = %@;)\n(response = %@)"
      , urlString, parameters, method, jsonString);
```

**说明：**可通过设置 Xcode > Preferences > Text Editing > Show page guide，来发现代码中越界的行。

**3.9、**【强制】方法(/函数)参数在定义和传参时，多个参数(/逗号后边)必须加空格。 

*__正例__*：下例中第一个参数的逗号后边必须要有一个空格。

```objective-c
  CGSize constraintSize = CGSizeMake(frame.size.width - 20, MAXFLOAT);
```

**3.10、**【强制】IDE 的 text fileencoding 设置为 UTF-8;  IDE 设置中的换行符使用Unix 格式。

**3.11、**【推荐】没有必要增加若干空格来使某一行的字符与上一行的相应字符对齐。 

*__正例__*：

```objective-c
  NSString * const WX_ACCESS_TOKEN = @"access_token";
  NSString * const WX_OPEN_ID = @"openid";
  NSString * const WX_REFRESH_TOKEN = @"refresh_token";
  CGRect rect;
  float secretNum = [[UIScreen mainScreen] bounds].size.width / 375;
```

**3.12、**【推荐】方法/函数体内的执行语句组、变量的定义语句组、不同的业务逻辑之间或者不同的语义之间插入一个空行。相同业务逻辑和语义之间不需要插入空行。

**说明：**没有必要插入**多行空格**进行隔开。下列情况应该总是使用空行。

a)      一个源文件的两个片段(section)之间

b)      类声明和接口声明之间

c)       常量声明区域之后

d)      常量和变量/属性之间

e)      方法/函数声明之前

f)        两个方法/函数之间

g)      方法/函数内的局部变量和方法/函数的第一条语句之间

h)      一个方法/函数内的两个逻辑段之间，用以提高可读性

i)        一行声明一个变量，不要一行声明多个变量，这样有利于写注释。

 



## (四)集合处理

**4.1、**【强制】关于 hash 和 isEqual 的处理，需遵循如下规则：

 1） 只要重写isEquals，就必须重写 hash。

 2）因为 NSSet 存储的是不重复的对象，依据hash 和 isEquals 进行判断，所以NSSet存储的对象必须重写这两个方法。

 3） 如果自定义对象做为 NSMapTable 的键，那么必须重写 hash 和 isEqual。

*__正例__*： NSString重写了 hash属性 和isEqual方法，所以我们可以非常愉快地使用NSString对象 作为 key 来使用。

 

**4.2、**【强制】不能在NSMutableSet/ NSMutableDictionary/NSHashTable/NSMapTable自身的 foreach 循环里对其元素进行 remove/add 操作。remove 元素请使用临时变量或临时集合记录下待删除的，然后在循环结束后再删除。 

*__正例__*：

```objective-c
  id obj = nil;
  id removeObj = nil;
  NSEnumerator *enumerator = [aSet objectEnumerator];
  while (obj = [enumerator nextObject]) {
      MBProgressHUD *hud = (MBProgressHUD *) obj;
      if (hud.hasFinished == YES) {
          //cannot remove obj here;
          removeObj = obj;
          break;
      }   
  }
  [aSet removeObject:removeObj];
```



**4.3、**【推荐】集合类NSSet与NSHashTable类似，但前者持有对象的强引用；而后者可以持有对象的弱引用，且在对象被销毁后能正确地将其从集合移除。在只用到NSSet的特性情况下，只需要使用NSSet即可。但若在对内存很敏感或频繁大量使用内存，而对性能要求不高时，使用NSHashTable。

**说明：**NSSet在添加元素时性能约是NSHashTable的2倍；其他方面的效率差别不大。

 

**4.4、**【推荐】字典类NSDictionary与NSMapTable类似，但前者持有key/value的强引用；而后者可以持有key/value的弱引用，且在key/value被销毁后能正确地将其从字典移除。在性能对比上，NSMapTable比NSDictionary稍微差一点，但它优点更多，建议用NSMapTable替代NSDictionary。



**4.5、**【推荐】NSMutableSet /NSHashTable/NSMapTable等集合初始化时，尽量指定集合初始大小。

**说明：**NSMutableSet尽量使用initWithCapacity:N 初始化。

**4.6、**【推荐】枚举(遍历)集合时，尽量使用 for(id object in set), 这种枚举方式速度最快。****

**4.6.1、**当枚举一个NSArray的时候：

· 使用 for (id object in array) 如果是顺序枚举

· 使用 for (id objectin [array reverseObjectEnumerator]) 如果是倒序枚举

· 使用 for (NSInteger i = 0; i <count; i++) 如果你需要知道它的索引值，或者需要改变数组

· 尝试 [arrayenumerateObjectsWithOptions:usingBlock:] 如果你的代码受益于并行执行

**4.6.2、**当枚举一个NSSet的时候：

· 使用  for (id object inset) 大多数时候

· 使用 for (id object in [setcopy]) 如果你需要修改集合(但是会很慢)

· 尝试 [arrayenumerateObjectsWithOptions:usingBlock:] 如果你的代码受益于并行执行

**4.6.3、**当枚举一个NSDictionary的时候：

· 使用  for (id object inset) 大多数时候

· 使用 for (id object in [setcopy]) 如果你需要修改词典

· 尝试 [arrayenumerateObjectsWithOptions:usingBlock:] 如果你的代码受益于并行执行



**4.7、**【参考】合理利用好集合的有序性(sort)和稳定性(order)，避免集合的无序性(unsort)和 不稳定性(unorder)带来的负面影响。

 **说明：**        稳定性指集合每次遍历的元素次序是固定的。有序性是指遍历的结果是按某种比较规则 依次排列的。如：NSArray是 order/unsort；NSSet是 unorder/unsort。

 

**4.8、**【参考】利用 Set 的元素唯一特性，可以快速对集合进行去重，无须通过遍历对比来去重。

 

 

## (五)控制语句

**5.1、**【强制】在一个switch 块内，每个case 要么通过 break/return 等来终止，要么注释说明程序将继续执行到哪一个 case 为止；在一个 switch 块内(除了枚举类型)，都必须包含一个 default语句并且放在最后，即使它什么代码也没有。

**5.2、**【强制】在 if/else/for/while/do 语句中必须使用大括号，即使只有一行代码，避免使用下面的形式：if (condition) statements;

**5.3、**【推荐】推荐尽量少用 else， if-else 的方式可以改写成：

```objective-c
  if (condition) {
      // ...
      return obj;
  }
  // 接着写 else 的业务逻辑代码;
```

**说明：**         如果非得使用if()...else if()...else...方式表达逻辑，【强制】避免后续代码维 护困难，请勿超过 3 层。

*__正例__*：超过 3 层的 if-else的逻辑判断代码可以使用卫语句、策略模式、状态模式来实现。

 

**5.4、**【推荐】除常用方法（如 getXxx/isXxx）等外，不要在条件判断中执行其它复杂的语句，将复杂逻辑判断的结果赋值给一个有意义的布尔变量名，以提高可读性。

**说明：**    很多 if 语句内的逻辑相当复杂，阅读者需要分析条件表达式的最终结果，才能明确什么样的条件执行什么样的语句，那么，如果阅读者分析逻辑表达式错误呢？

*__正例__*：

```objective-c
  // 伪代码如下
  bool existed = (file.open(fileName, "w") != nil) && (...) || (...); 
  if (existed) {
     // ...
  }
```

*__反例__*：

```objective-c
  if ((file.open(fileName, "w") != nil) && (...) || (...)) {
      // ...
  }
```



**5.5、**【推荐】循环体中的语句要考量性能，以下操作尽量移至循环体外处理，如定义对象、变量、 获取数据库连接，进行不必要的try-catch 操作（若try-catch 可以移至循环体外）。

 

**5.6、**【推荐】接口入参保护，这种场景常见的是用于做批量操作的接口。

 

**5.7、**【参考】方法/函数中需要进行参数校验的场景：

 1） 调用频次低的方法/函数。

 2） 执行时间开销很大的方法/函数，参数校验时间几乎可以忽略不计，但如果因为参数错误导致中间执行回退，或者错误，那得不偿失。

 3） 需要极高稳定性和可用性的方法。

 4） 对外提供的开放接口，不管是 RPC/API/HTTP 接口。

 5） 敏感权限入口。

 

**5.8、**【参考】方法/函数中不需要参数校验的场景：

 1） 极有可能被循环调用的方法/函数，不建议对参数进行校验。但在方法/函数说明里必须注明外部参数检查。

 2）底层的方法/函数调用频度都比较高，一般不校验。毕竟是像纯净水过滤的最后一道，参数错误不太可能到底层才会暴露问题。

 3） 被声明成private 只会被自己代码所调用的方法/函数，如果能够确定调用方法/函数的代码传入参数已经做过检查或者肯定不会有问题，此时可以不校验参数。

 

 

## (六)注释规约

**6.1、**【强制】方法、函数、类、协议、类别的注释，必须采用 Apple 的标准注释风格，使用/**内容*/格式，不得使用// xxx 方式。

**说明：**有很多可以自动生成注释格式的插件，推荐使用 VVDocumenter。在 IDE 中，在引用的地方，可以按ALT+ 点击自动弹出注释，提高阅读效率。

 

**6.2、**【强制】所有协议中的方法必须要用/**内容*/格式注释、除了返回值、参数、 异常说明外，还必须指出该方法做什么事情，实现什么功能。

 **说明：**对子类的实现要求，或者调用注意事项，请一并说明。

 

**6.3、**【强制】所有类都必须添加创建者信息。

 

**6.4、**【强制】方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/**/注释，注意与代码对齐。

 

**6.5、**【强制】所有枚举类型字段必须要有注释，说明每个数据项的用途。

 

**6.6、**【强制】注释的双斜线或*号与注释内容之间有且仅能有一个空格。

*__正例__*：

```objective-c
  /**
   * 用于表示星期的枚举类，周一从1开始。
   * @author guo * 
   */
  typedef NS_ENUM(NSInteger, DayEnum) {
      /**
       * 周一从1开始
       */
      MONDAY    = 1, 
      TUESDAY   = 2, 
      WEDNESDAY = 3, 
      THURSDAY  = 4, 
      FRIDAY    = 5, 
      SATURDAY  = 6, 
      SUNDAY    = 7  
  };
```



**6.7、**【推荐】与其“半吊子”英文来注释，不如用中文注释把问题说清楚。专有名词与关键字保持英文原文即可。

 *__反例__*：“TCP 连接超时”解释成“传输控制协议连接超时”，理解反而费脑筋。

 

**6.8、**【推荐】代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑 等的修改。 

**说明：**         代码与注释更新不同步，就像路网与导航软件更新不同步一样，如果导航软件严重滞后，就失去了导航的意义。

 

**6.9、**【参考】注释掉的代码尽量要配合说明，而不是简单的注释掉。

**说明：**         代码被注释掉有两种可能性：1）后续会恢复此段代码逻辑。2）永久不用。前者如果没有备注信息，难以知晓注释动机。后者建议直接删掉（代码仓库保存了历史代码）。

 

**6.10、**【参考】对于注释的要求：第一、能够准确反应设计思想和代码逻辑；第二、能够描述业务含 义，使别的程序员能够迅速了解到代码背后的信息。完全没有注释的大段代码对于阅读者形同天书。注释既是给自己看的，即使隔很长时间，也能清晰理解当时的思路；注释也是给继任者看的，使其能够快速接替自己的工作。

**6.11、**【参考】好的命名、代码结构是自解释的，注释力求精简准确、表达到位。避免出现注释的 一个极端：过多过滥的注释，代码的逻辑一旦修改，修改注释是相当大的负担。

*__反例__*：

```objective-c
  // put egg into fridge 
  put(egg , fridge);
```

方法名 put，加上有意义的变量名 egg 和 fridge足够了。语义清晰的代码不需要额外的注释。

 

**6.12、**【参考】特殊注释标记，请注明标记人与标记时间。注意及时处理这些标记，通过标记扫描，经常清理此类标记。线上故障有时候就是来源于这些标记处的代码。

 1） 待办事宜（TODO）:（ 标记人，标记时间，[预计处理时间]）表示需要实现，但目前还未实现的功能。这实际上是一个标签，目标的逻辑还没有实现，但已经被广泛使用。只能应用于类，协议，方法与函数中。

 2） 错误，不能工作（FIXME）:（标记人，标记时间，[预计处理时间]）在注释中用 FIXME 标记某代码是错误的，而且不能工作，需要及时纠正的情况。

# 二、异常日志

## (一)异常处理

**1.1、**【强制】Objective-c类库中定义的运行时异常可以通过预检查进行规避，而不应该通过catch 来处理，比如：NSRangeException，NSInvalidArgumentException等。  

**说明：**无法通过预检查的异常除外。如在解析一个外部传来的字符串形式数字时要捕捉异常。 

*__正例__*：if (obj != nil) {...}  

*__反例__*：@try {array[k].method()} @catch (NSRangeException *e) {/**...**/}

**1.2、**【强制】异常不要用来做流程控制，条件控制，因为异常的处理效率比条件分支低。

**1.3、**【强制】对大段代码进行 try-catch，这是不负责任的表现。catch 时请分清稳定代码和非稳 定代码，稳定代码指的是无论如何不会出错的代码。对于非稳定代码的 catch 尽可能进行区分异常类型，再做对应的异常处理。

**1.4、**【强制】捕获异常是为了处理它，不要捕获了却什么都不处理而抛弃之，如果不想处理它，请将该异常抛给它的调用者。最外层的业务使用者，必须处理异常，将其转化为用户可以理解的内容。

**1.5、**【强制】有 try 块放到了事务代码中，catch 异常后，如果需要回滚事务，一定要注意手动回滚事务。

**1.6、**【强制】finally 块必须对资源对象、流对象进行关闭，有异常也要做 try-catch。  

**1.7、**【强制】不能在finally 块中使用 return，finally 块中的 return 返回后方法结束执行，不会再执行try块中的 return 语句。

**1.8、**【强制】捕获异常与抛异常，必须是完全匹配，或者捕获异常是抛异常的父类。 

**说明：**如果预期对方抛的是绣球，实际接到的是铅球，就会产生意外情况。

**1.9、**【推荐】方法/函数的返回值可以为 nil，不强制返回空集合，或者空对象等，必须添加注释充分说明什么情况下会返回 nil 值。调用方需要进行 nil 判断。

 **说明：**        本规约明确检查 nil 是调用者的责任。即使被调用方法返回空集合或对象，对调用者来说，也并非高枕无忧，必须考虑到远程调用/序列化失败与运行时异常等场景返回 nil 的情况。

**1.10、**【推荐】防止 nil，是程序员的基本修养，注意 nil 产生的场景：

 1） 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 nil。

​    *__反例__*：`int fun() {return NSInteger对象;} 如果对象为nil，自动解箱值为nil。`

 2） 数据库的查询结果可能为 nil。

 3） 集合里的元素即使isNotEmpty，取出的数据元素也可能为 NSNull。

 4） 远程调用返回对象，一律要求进行 nil 判断。

 5） 对于Session 中获取的数据，建议 nil 检查，避免空指针。

 6） 级联调用obj.getA().getB().getC()；一连串调用，易产生 nil。

**1.13、**【参考】避免出现重复的代码（Don’t Repeat Yourself），即 DRY 原则。 

**说明：**         随意复制和粘贴代码，必然会导致代码的重复，在以后需要修改时，需要修改所有的副本，容易遗漏。必要时抽取共性方法，或者抽象公共类，甚至是组件化。

*__正例__*：一个类中有多个方法，都需要进行数行相同的参数校验操作，这个时候请抽取： 

```objective-c
    bool checkParam(DTO dto) {/*...*/}
```

 

## (二)日志规约

**2.1、**【强制】在没有捕捉到的异常导致APP崩溃时，应提供uncaughtexceptions的处理，让用户选择是否把APP崩溃时的错误信息提交到服务器。



**2.2、**【强制】异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么往上 抛出。

*__正例__*：logger.error(各类参数或者对象toString + "_" + e.getMessage(), e);

**2.3、**【推荐】谨慎地记录日志。生产环境禁止输出 debug 日志；有选择地输出 info 日志。

 **说明：**        大量地输出无效日志，不利于系统性能提升，也不利于快速定位错误点。记录日志时请 思考：这些日志真的有人看吗？看到这条日志你能做什么？能不能给问题排查带来好处？ 





# 三、SQLite数据库规约

## (一)建表规约

**1.1、**【强制】表达是与否概念的字段，必须使用is_xxx的方式命名，数据类型是INTEGER（ 1 表示是，0 表示否）。

*__正例__*：表达逻辑删除的字段名 is_deleted，1 表示删除，0 表示未删除。 

**1.2、**【强制】表名、字段名必须使用小写字母或数字；禁止出现数字开头，禁止两个下划线中间只出现数字。修改数据库字段名代价很大，因为无法进行预发布，所以字段名需要慎重考虑。

*__正例__*：getter_admin，task_config，level3_name

*__反例__*：GetterAdmin，taskConfig，level_3_name

**1.3、**【强制】表名不使用复数名词。

 **说明：**        表名应该仅仅表示表里面的实体内容，不应该表示实体数量，对应于实体类名也是单数形式，符合表达习惯。

**1.4、**【强制】禁用保留字，如 desc、key、match、action 等，请参考 SQLite 官方保留字。

**1.5、**【强制】主键索引名为 pk_字段名；唯一索引为 uk_字段名；普通索引名则为idx_字段名。

**说明：**pk_ 即primary key；uk_ 即unique key；idx_ 即 index 的简称。

**1.6、**【强制】表必备三字段：id, gmt_create, gmt_modified。

**说明：**        其中 id 必为主键，单表时自增、步长为 1。gmt_create, gmt_modified，前者现在时表示主动创建，后者过去分词表示被 动更新。

**1.7、**【推荐】表的命名最好是加上“业务名称_表的作用”。

*__正例__*：tiger_task/tiger_reader/ mpp_config

 

**1.8、**【推荐】库名与应用名称尽量一致。

 

**1.9、**【推荐】如果修改字段含义或对字段表示的状态追加时，需要及时更新字段注释。

 

## (二)索引规约

**2.1、**【强制】业务上具有唯一特性的字段，即使是组合字段，也必须建成唯一索引。

**说明：**         不要以为唯一索引影响了insert 速度，这个速度损耗可以忽略，但提高查找速度是明显的；另外，即使在应用层做了非常完善的校验和控制，只要没有唯一索引，根据墨菲定律，必然有脏数据产生。

 

**2.2、**【强制】超过三个表禁止join。需要 join 的字段，数据类型必须绝对一致；多表关联查询时，保证被关联的字段需要有索引。

**说明：**即使双表 join 也要注意表索引、SQL 性能。

 

**2.3、**【强制】在 text 字段上建立索引时，必须指定索引长度，没必要对全字段建立索引，根据实际文本区分度决定索引长度。

**说明：**        索引的长度与区分度是一对矛盾体，一般对字符串类型数据，长度为20 的索引，区分度会高达 90%以上，可以使用count(distinct left(列名, 索引长度))/count(*)的区分度 来确定。

 

**2.5、**【推荐】如果有 order by 的场景，请注意利用索引的**有序性**。order by 最后的字段是组合索引的一部分，并且放在索引组合顺序的最后，避免出现file_sort 的情况，影响查询性能。 

*__正例__*：wherea=? and b=? order by c; 索引：a_b_c 

*__反例__*：索引中有范围查找，索引有序性无法利用，如WHERE a>10 ORDER BY b; 索引 a_b 无法排序。

 

**2.9、**【推荐】建组合索引的时候，区分度最高的在最左边。

*__正例__*：如果 where a=? and b=? ，a 列的几乎接近于唯一值，那么只需要单建 idx_a索引即可。 

**说明：**存在非等号和等号混合判断条件时，在建索引时，请把等号条件的列前置。

​       如：where a>? and b=? 那么即使 a 的区分度更高，也必须把b 放在索引的最前列。

 

**2.10、**【推荐】防止因字段类型不同造成的隐式转换，导致索引失效。

 

**2.11、**【参考】创建索引时避免有如下极端误解：

1）宁滥勿缺。认为一个查询就需要建一个索引。

2）宁缺勿滥。认为索引会消耗空间、严重拖慢更新和新增速度。

3）抵制惟一索引。认为业务的惟一性一律需要在应用层通过“先查后插”方式解决。 



## (三)SQL 规约

**3.1、**【强制】不要使用 count(列名)或 count(常量)来替代 count(*)**，count(*)就是 SQL92 定义 的标准统计行数的语法，跟数据库无关，跟null 和非 null 无关。

 **说明：**count(*)会统计值为 null 的行，而 count(列名)不会统计此列为null 值的行。

**3.2、**【强制】count(distinct col)计算该列除 null 之外的不重复行数。注意count(distinct col1, col2) 如果其中一列全为 null，那么即使另一列有不同的值，也返回为 0。

**3.3、**【强制】当某一列的值全是 null 时，count(col)的返回结果为0，但sum(col)的返回结果为null，因此使用 sum()时需注意 nil 问题。

*__正例__*：可以使用如下方式来避免 sum 的 nil 问题：

​    SELECT IF(ISNULL(SUM(g)), 0,SUM(g)) FROM table;

**3.4、**【强制】使用 ISNULL()来判断是否为 null 值。注意：null 与任何值的直接比较都为 null。

**说明：**

1）null<>null的返回结果是 null，而不是 false。

2） null=null 的返回结果是null，而不是 true。

3）null<>1 的返回结果是 null，而不是true。

**3.5、**【强制】在代码中写分页查询逻辑时，若 count 为 0 应直接返回，避免执行后面的分页语句。

**3.9、**【推荐】in 操作能避免则避免，若实在避免不了，需要仔细评估 in 后边的集合元素数量，控 制在 1000 个之内。

**3.10、**【参考】如果有全球化需要，所有的字符存储与表示，均以utf-8 编码，要注意字符统计函数的区别。

**说明：**
```sql
SELECT LENGTH("轻松工作")； --返回为 12
SELECT CHARACTER_LENGTH("轻松工作")； --返回为 4
```

如果要使用表情，那么使用 utfmb4 来进行存储，注意它与utf-8 编码的区别。

**3.11、**【参考】TRUNCATE TABLE 比 DELETE 速度快，且使用的系统和事务日志资源少，但TRUNCATE 无事务且不触发trigger，有可能造成事故，故不建议在开发代码中使用此语句。 

**说明：**TRUNCATE TABLE 在功能上与不带 WHERE子句的 DELETE 语句相同。

 

 

# 四、安全规约

**1、**【强制】用户敏感数据禁止直接展示，必须对展示数据脱敏。

**说明：**查看个人手机号码会显示成:158\*\*\*\*9119，隐藏中间 4 位，防止隐私泄露。

**2、**【强制】用户输入的 SQL 参数严格使用参数绑定或者 METADATA 字段值限定，防止 SQL 注入，禁止字符串拼接 SQL 访问数据库。

**3、**【强制】用户请求传入的任何参数必须做有效性验证。 

**说明：**忽略参数校验可能导致：

• pagesize 过大导致内存溢出

• 恶意 order by 导致数据库慢查询

• 任意重定向

• SQL 注入

• 反序列化注入

• 正则输入源串拒绝服务ReDoS

**说明：**        用正则来验证客户端的输入，有些正则写法验证普通用户输入没有问题，但是如果攻击人员使用的是特殊构造的字符串来验证，有可能导致死循环。

**4、**【强制】禁止向 HTML 页面输出未经安全过滤或未正确转义的用户数据。

**5、**【强制】在使用平台资源，譬如短信、邮件、电话、下单、支付，必须实现正确的防重放限制，如数量限制、疲劳度控制、验证码校验，避免被滥刷、资损。

**说明：**         如注册时发送验证码到手机，如果没有限制次数和频率，那么可以利用此功能骚扰到其它用户，并造成短信平台资源浪费。

**6、**【推荐】发贴、评论、发送即时消息等用户生成内容的场景必须实现防刷、文本内容违禁词过 滤等风控策略。

 

 

 

 

# 附录、专有名词

**1、**OOP（Object Oriented Programming）: 本文档泛指类、对象的编程处理方式。 

**2、**ORM（Object Relation Mapping）: 对象关系映射，对象领域模型与底层数据之间的转换。

 