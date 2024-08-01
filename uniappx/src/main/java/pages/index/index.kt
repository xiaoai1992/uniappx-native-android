@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uni.UNIEF87C14;
import io.dcloud.uniapp.*;
import io.dcloud.uniapp.extapi.*;
import io.dcloud.uniapp.framework.*;
import io.dcloud.uniapp.runtime.*;
import io.dcloud.uniapp.vue.*;
import io.dcloud.uniapp.vue.shared.*;
import io.dcloud.uts.*;
import io.dcloud.uts.Map;
import io.dcloud.uts.Set;
import io.dcloud.uts.UTSAndroid;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
open class GenPagesIndexIndex : BasePage {
    constructor(instance: ComponentInternalInstance) : super(instance) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache;
        return createElementVNode("view", utsMapOf("class" to "pages"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "uni-center"), utsArrayOf(
                createElementVNode("image", utsMapOf("class" to "image", "mode" to "widthFix", "src" to "/static/images/index/1.png")),
                createElementVNode("image", utsMapOf("class" to "msg", "mode" to "scaleToFill", "src" to "/static/images/index/13.png"))
            )),
            createElementVNode("view", utsMapOf("class" to "s"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "s1"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "s11", "mode" to "scaleToFill", "src" to "/static/images/index/2.png")),
                    createElementVNode("text", utsMapOf("class" to "s1-t1"), "今日交易（元）"),
                    createElementVNode("text", utsMapOf("class" to "s1-t2"), "219,999")
                )),
                createElementVNode("view", utsMapOf("class" to "s2"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "s22", "mode" to "scaleToFill", "src" to "/static/images/index/3.png")),
                    createElementVNode("image", utsMapOf("class" to "s2-t1", "mode" to "scaleToFill", "src" to "/static/images/index/12.png")),
                    createElementVNode("text", utsMapOf("class" to "s2-t2"), "防骗提醒｜熟人借用银行卡，借不借？")
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "c"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/5.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "新闪付")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/6.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "商户服务")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/7.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "磁条卡认证")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/8.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "开通扫码")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/10.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "终端管理")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/9.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "我的额度")
                )),
                createElementVNode("view", utsMapOf("class" to "cc"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "image-1", "mode" to "aspectFit", "src" to "/static/images/index/11.png")),
                    createElementVNode("text", utsMapOf("class" to "text"), "帮助中心")
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "image-w"), utsArrayOf(
                createElementVNode("image", utsMapOf("class" to "image-2", "mode" to "scaleToFill", "src" to "/static/images/index/4.png"))
            ))
        ));
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>>
            get() {
                return normalizeCssStyles(utsArrayOf(
                    styles0
                ), utsArrayOf(
                    GenApp.styles
                ));
            }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("pages" to padStyleMapOf(utsMapOf("position" to "relative", "backgroundColor" to "#F5F5F5", "height" to "100%")), "msg" to utsMapOf(".pages " to utsMapOf("width" to 24, "height" to 24, "position" to "absolute", "right" to 18, "top" to 40)), "s1" to utsMapOf(".pages " to utsMapOf("position" to "relative")), "s2" to utsMapOf(".pages " to utsMapOf("position" to "relative")), "s1-t1" to utsMapOf(".pages " to utsMapOf("position" to "absolute", "fontWeight" to "400", "fontSize" to 12, "color" to "#616780", "top" to 30, "left" to 50)), "s1-t2" to utsMapOf(".pages " to utsMapOf("position" to "absolute", "top" to 56, "left" to 50, "fontWeight" to "bold", "fontSize" to 22, "color" to "#05144D")), "s2-t1" to utsMapOf(".pages " to utsMapOf("width" to 27, "height" to 14, "position" to "absolute", "top" to 24, "left" to 50)), "s2-t2" to utsMapOf(".pages " to utsMapOf("position" to "absolute", "top" to 24, "left" to 90, "fontWeight" to "400", "fontSize" to 12, "color" to "#999999")), "s11" to utsMapOf(".pages " to utsMapOf("width" to 400, "height" to 86, "marginTop" to 12, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "s22" to utsMapOf(".pages " to utsMapOf("width" to 400, "height" to 57, "marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "image" to utsMapOf(".pages " to utsMapOf("width" to "100%", "height" to "234rpx", "marginTop" to -50)), "text" to utsMapOf(".pages " to utsMapOf("fontWeight" to "400", "fontSize" to 11, "color" to "#333333", "textAlign" to "center")), "cc" to utsMapOf(".pages " to utsMapOf("marginBottom" to "40rpx", "marginRight" to "32rpx", "width" to 56)), "c" to utsMapOf(".pages " to utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to "20rpx", "paddingTop" to "20rpx", "paddingRight" to "60rpx", "paddingBottom" to "20rpx", "paddingLeft" to "60rpx")), "image-1" to utsMapOf(".pages .c " to utsMapOf("width" to "64rpx", "height" to "64rpx", "marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "image-w" to utsMapOf(".pages " to utsMapOf("width" to 372, "height" to 72, "marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "image-2" to utsMapOf(".pages " to utsMapOf("width" to "100%", "height" to "100%")));
            }
        var inheritAttrs = true;
        var inject: Map<String, Map<String, Any?>> = utsMapOf();
        var emits: Map<String, Any?> = utsMapOf();
        var props = normalizePropsOptions(utsMapOf());
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf();
        var components: Map<String, CreateVueComponent> = utsMapOf();
    }
}
