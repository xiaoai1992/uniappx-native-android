@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uni.UNIA7C19B9;
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
import uts.sdk.modules.kuxPlusAccelerometer.AccelerometerOption;
import uts.sdk.modules.kuxPlusAccelerometer.useAccelerometer;
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
            val title = ref("Hello333");
            val plus = IPlus(accelerometer = useAccelerometer());
            val getCurrentAcceleration = fun(){
                plus.accelerometer.getCurrentAcceleration(fun(a){
                    console.log("Acceleration\nx:" + a.xAxis + "\ny:" + a.yAxis + "\nz:" + a.zAxis, " at pages/index/index.uvue:24");
                }
                );
            }
            ;
            getCurrentAcceleration();
            val watchId = plus.accelerometer.watchAcceleration(fun(a){
                console.log("Acceleration\nx:" + a.xAxis + "\ny:" + a.yAxis + "\nz:" + a.zAxis, " at pages/index/index.uvue:31");
            }
            , fun(err){
                console.log(err, " at pages/index/index.uvue:33");
            }
            , AccelerometerOption(frequency = 2000));
            console.log("10秒后自动关闭监听", " at pages/index/index.uvue:36");
            setTimeout(fun(){
                plus.accelerometer.clearWatch(watchId);
            }
            , 10000);
            return fun(): Any? {
                return createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "logo", "src" to "/static/logo.png")),
                    createElementVNode("view", utsMapOf("class" to "text-area"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "title"), toDisplayString(unref(title)), 1)
                    ))
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
                return utsMapOf("content" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "logo" to padStyleMapOf(utsMapOf("height" to "200rpx", "width" to "200rpx", "marginTop" to "200rpx", "marginBottom" to "50rpx")), "title" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "color" to "#8f8f94")));
            }
        var inheritAttrs = true;
        var inject: Map<String, Map<String, Any?>> = utsMapOf();
        var emits: Map<String, Any?> = utsMapOf();
        var props = normalizePropsOptions(utsMapOf());
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf();
        var components: Map<String, CreateVueComponent> = utsMapOf();
    }
}
