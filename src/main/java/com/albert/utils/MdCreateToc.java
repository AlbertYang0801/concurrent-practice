package com.albert.utils;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;
import com.github.houbb.markdown.toc.vo.TocGen;

/**
 * 为指定MD文件生成目录
 * @author Albert
 * @date 2021/1/25 下午5:30
 */
public class MdCreateToc {

    public static void main(String[] args) {
        TocGen tocGen = AtxMarkdownToc.newInstance()
                .genTocFile("/Users/yangjunwei/IdeaProjects/concurrent-practice/README.md");
        System.out.println(tocGen);
    }


}
