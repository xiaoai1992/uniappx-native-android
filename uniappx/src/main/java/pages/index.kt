@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uni.UNI79D8002;
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
import uts.sdk.modules.kuxAudioPlayer.createAudioPlayer;
open class GenPagesIndexIndex : BasePage {
    constructor(instance: ComponentInternalInstance) : super(instance) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexIndex) -> Any? = fun(
        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        __props): Any? {
            val __ins = getCurrentInstance()!!;
            val _ctx = __ins.proxy as GenPagesIndexIndex;
            val _cache = __ins.renderCache;
            val audioPlayer = createAudioPlayer();
            audioPlayer.src = "https://web-ext-storage.dcloud.net.cn/uni-app/ForElise.mp3";
            audioPlayer.onError(fun(error){
                console.log(error, " at pages/index/index.uvue:16");
            }
            );
            val onPlay = fun(){
                audioPlayer.play();
            }
            ;
            val onPause = fun(){
                audioPlayer.pause();
            }
            ;
            val onStop = fun(){
                audioPlayer.stop();
            }
            ;
            return fun(): Any? {
                return createElementVNode("view", null, utsArrayOf(
                    createElementVNode("button", utsMapOf("style" to normalizeStyle(utsMapOf("margin-bottom" to "30px")), "type" to "primary", "onClick" to onPlay), "开始播放", 4),
                    createElementVNode("button", utsMapOf("style" to normalizeStyle(utsMapOf("margin-bottom" to "30px")), "type" to "primary", "onClick" to onPause), "暂停播放", 4),
                    createElementVNode("button", utsMapOf("type" to "primary", "onClick" to onStop), "停止播放")
                ));
            }
            ;
        }
        ;
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
