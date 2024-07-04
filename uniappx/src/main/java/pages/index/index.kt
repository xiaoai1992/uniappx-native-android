@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uni.UNI3B5CA8D;
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
import io.dcloud.uniapp.extapi.loadFontFace as uni_loadFontFace;
open class GenPagesIndexIndex : BasePage {
    constructor(instance: ComponentInternalInstance) : super(instance) {
        onLoad(fun(_: OnLoadOptions) {
            uni_loadFontFace(LoadFontFaceOptions(global = true, family = "iconfont", source = "url('/static/iconfont.ttf')", success = fun(_){
                console.log("global loadFontFace uni.ttf success", " at pages/index/index.uvue:15");
            }
            , fail = fun(error){
                console.warn("global loadFontFace uni.ttf fail", error.errMsg, " at pages/index/index.uvue:18");
            }
            ));
        }
        , instance);
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this;
        val _cache = this.`$`.renderCache;
        val _component_test_common = resolveEasyComponent("test-common", GenUniModulesTestCommonComponentsTestCommonTestCommonClass);
        return createElementVNode(Fragment, null, utsArrayOf(
            createElementVNode("view", null, utsArrayOf(
                createElementVNode("image", utsMapOf("class" to "logo", "src" to "/static/wdjy.png")),
                createElementVNode("view", utsMapOf("class" to "text-area"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "title"), toDisplayString(_ctx.title), 1)
                )),
                createElementVNode("view", null, utsArrayOf(
                    createElementVNode("text", utsMapOf("style" to normalizeStyle(utsMapOf("font-family" to "iconfont"))), toDisplayString(_ctx.uniIcon), 5),
                    createElementVNode("text", null, "\\ue60d")
                ))
            )),
            createVNode(_component_test_common)
        ), 64);
    }
    open var title: String by `$data`;
    open var uniIcon: String by `$data`;
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("title" to "Hello", "uniIcon" to "\ue60d");
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
                return utsMapOf("logo" to padStyleMapOf(utsMapOf("height" to 100, "width" to 100, "marginTop" to 100, "marginRight" to "auto", "marginBottom" to 25, "marginLeft" to "auto")), "title" to padStyleMapOf(utsMapOf("fontSize" to 18, "color" to "#8f8f94", "textAlign" to "center")));
            }
        var inheritAttrs = true;
        var inject: Map<String, Map<String, Any?>> = utsMapOf();
        var emits: Map<String, Any?> = utsMapOf();
        var props = normalizePropsOptions(utsMapOf());
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf();
        var components: Map<String, CreateVueComponent> = utsMapOf();
    }
}
