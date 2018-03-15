# About this Cool Concise Jekyll Blog Theme 🤘🤘🤘

[![GitHub stars](https://img.shields.io/github/stars/Gaohaoyang/gaohaoyang.github.io.svg)](https://github.com/Gaohaoyang/gaohaoyang.github.io/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/Gaohaoyang/gaohaoyang.github.io.svg)](https://github.com/Gaohaoyang/gaohaoyang.github.io/network)
[![GitHub issues](https://img.shields.io/github/issues/Gaohaoyang/gaohaoyang.github.io.svg)](https://github.com/Gaohaoyang/gaohaoyang.github.io/issues)
[![GitHub release](https://img.shields.io/github/release/Gaohaoyang/gaohaoyang.github.io.svg)](https://github.com/Gaohaoyang/gaohaoyang.github.io/releases)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/Gaohaoyang/gaohaoyang.github.io/master/LICENSE)

**[中文版 Chinese README 请点击这里 🇨🇳](https://github.com/Gaohaoyang/gaohaoyang.github.io/blob/master/README-zh-cn.md)**

With the escalation of jekyll version, but I also want to reconstruct my older blog theme, so I did reconstruction and added some features recently. My new blog theme will still be stored in this repository. I will also use this theme in the future. Now I have done basically, then I will focus on issues that users opend to make theme better.

## Preview

First of all, let's see previews.

Index Page
![index](http://ww3.sinaimg.cn/large/7011d6cfjw1f3bdli86awj211k0oyqen.jpg)

Post Page
![post](http://ww4.sinaimg.cn/large/7011d6cfjw1f3bdmzb9v6j210p0j7gwn.jpg)

## Page Details

### Home

Index page show 5 posts excerpt as a default. Readers can click article title or read more button to see full post. There are recent posts area, categories area and tags area at the right part of the index page. You can also add an area at this part, if you change the file `index.html`.

### Archives

Archive post according to the year.

### Categories

Show posts according to the category.

### Tags

Show posts according to the tags.

### Collections

The user can collect their favorite article links with `markdown` syntax.

### Demo

I use *[Masonry](http://masonry.desandro.com/)* to rewrite the waterfall responsive layout. Better interactive experience.

### About

The user can write some introduction about theirselves and their site with `markdown` syntax.

### Comments

This theme supports both [disqus](https://disqus.com/) and [多说评论 duoshuo comments](http://duoshuo.com/). It's very easy to config your comments module.

The only thing you need do is to change the `short_name` in the file `_config.yml`. As follows.

```yml
# comments
# two ways to comment, only choose one, and use your own short name
duoshuo_shortname: #xxx
disqus_shortname: xxx
```

### Post Contents

The post contents is fixed at the right side while page is scrolling. There will be a scroll bar on contents while it is outside the window height.

### Code Highlight

While the jekyll is update to 3.x.x, you can use github flavored markdown to write code.

More info to see [syntax-highlighter-changed](https://jekyllrb.com/docs/upgrading/2-to-3/#syntax-highlighter-changed).

### Light Shadow

![light](http://ww3.sinaimg.cn/large/7011d6cfjw1f3be6y4vp3j209i02rweg.jpg)

You can see the white shadow on the current item in the navbar. I call this light shadow.

### Mobile Adaptation

Of course, I have done a very good mobile adaptation.

![mobile](http://ww4.sinaimg.cn/large/7011d6cfjw1f3bebnzxkpj20ah0fzgp4.jpg)

### Footer

**Welcome to use this blog theme, but please keep the theme author info at footer.** Theme designed by [HyG](https://github.com/gaohaoyang).

![footer](http://ww3.sinaimg.cn/large/7011d6cfjw1f3bepd8002j20hl02ct95.jpg)

### Statistical Analysis

This theme supports Google Analytics and Baidu Statistics， you can just config the id in the file `_config.yml`, as follows.

```yml
# statistic analysis 统计代码
# 百度统计 id，将统计代码替换为自己的百度统计id，即
# hm.src = "//hm.baidu.com/hm.js?xxxxxxxxxxxx";
# xxxxx字符串
baidu_tongji_id: xxxxxxxxxxxx
google_analytics_id: UA-xxxxxxxx # google 分析追踪id
```


