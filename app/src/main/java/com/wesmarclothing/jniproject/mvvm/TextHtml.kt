package com.wesmarclothing.jniproject.mvvm

import android.text.Html
import android.widget.TextView

/**
 * @Package com.wesmarclothing.kotlintools.kotlin
 * @FileName TextHtml
 * @Date 2019/7/24 11:02
 * @Author JACK
 * @Describe TODO
 * @Project WeiMiBra
 */


fun TextView.htmlText(
    source: String,
    color: String = "",
    spanSize: Int = 0,
    isBold: Boolean = false,
    isItalic: Boolean = false,
    isUnderline: Boolean = false,
    isDeleteLine: Boolean = false,
    supString: String = "",
    subString: String = "",
    keyString: String = ""
) {
    var html = ""
    var str = source

    if (subString.isNotEmpty())
        str = keywordReplaceAll(str, subString, "<sub>$subString</sub>")

    if (supString.isNotEmpty())
        str = keywordReplaceAll(str, supString, "<sup>$supString</sup>")


    if (keyString.isNotEmpty()) {
        html =
            "<font ${if (color.isNotEmpty()) "color=$color" else ""} >$keyString</font>"
        if (isBold)
            html = "<b>$html</b>"
        if (isItalic)
            html = "<i>$html</i>"
        if (isUnderline)
            html = "<u>$html</u>"
        if (isDeleteLine)
            html = "<del>$html</del>"
        if (spanSize > 0)
            html = "<size value='$spanSize'>$html</size>"

        html = keywordReplaceAll(
            str, keyString,
            html
        )
    } else {
        html =
            "<font ${if (color.isNotEmpty()) "color=$color" else ""} ${if (spanSize > 0) "size=$spanSize" else ""}>$str</font>"
        if (isBold)
            html = "<b>$html</b>"
        if (isItalic)
            html = "<i>$html</i>"
        if (isUnderline)
            html = "<u>$html</u>"
        if (isDeleteLine)
            html = "<del>$html</del>"
    }

    this.text = Html.fromHtml(html, null, CustomerTagHandler(this.context))
}


/**
 * 将给定的字符串中所有给定的关键字进行替换内容
 * @param source 给定的字符串
 * @param keyword 给定的关键字
 * @param replacement 替换的内容
 * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml() 转换为Spanned对象再传递给TextView对象
 */
private fun keywordReplaceAll(source: String, keyword: String?, replacement: String?): String {
    try {
        if (source.trim { it <= ' ' }.isNotEmpty()) {
            if (keyword != null && keyword.trim { it <= ' ' }.isNotEmpty()) {
                if (replacement != null && replacement.trim { it <= ' ' }.isNotEmpty()) {
                    return source.replace(keyword.toRegex(), replacement)
                }
            }
        }
    } catch (e: Exception) {
    }

    return source
}
