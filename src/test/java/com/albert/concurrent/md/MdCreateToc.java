package com.albert.concurrent.md;

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
                .genTocFile("D:\\IdeaWorkSpace\\concurrent-practice\\src\\test\\java\\com\\albert\\concurrent\\md\\READMEBak.md");
        System.out.println(tocGen);
    }


}
