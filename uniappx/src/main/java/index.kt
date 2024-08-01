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
import io.dcloud.uniapp.appframe.AppConfig;
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile;
import io.dcloud.uniapp.extapi.env as uni_env;
import io.dcloud.uniapp.extapi.exit as uni_exit;
import uts.sdk.modules.yyWebview.getFilePath;
import io.dcloud.uniapp.extapi.getFileSystemManager as uni_getFileSystemManager;
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo;
import uts.sdk.modules.yyRequest.httpRequest;
import io.dcloud.uniapp.extapi.setStorage as uni_setStorage;
import io.dcloud.uniapp.extapi.showToast as uni_showToast;
val getContentAfterLastSlash = fun(url: String): String {
    val result = url.match(UTSRegExp("^.*\\/(.*)\$", ""));
    if (result !== null) {
        return result[1] as String;
    }
    return "";
}
;
val downloadH5 = fun(url: String): UTSPromise<Boolean> {
    return UTSPromise(fun(resolve, reject){
        console.log("地址00", uni_env.USER_DATA_PATH, " at common/utils.uts:18");
        console.log("H5项目地址", url, " at common/utils.uts:19");
        val downUrl = url;
        val fileName = getContentAfterLastSlash(downUrl);
        console.log("\u6587\u4EF6\u540D222\uFF1A " + fileName, " at common/utils.uts:22");
        val fileManager: FileSystemManager = uni_getFileSystemManager();
        fileManager.access(AccessOptions(path = "/storage/emulated/0/Android/data/com.hky.uniappx/cache/uni-download/" + fileName, success = fun(res: FileManagerSuccessResult){
            console.log("文件已存在", res, " at common/utils.uts:27");
            resolve(true);
        }
        , fail = fun(res: IFileSystemManagerFail){
            console.log("文件不存在，开始下载...", res, " at common/utils.uts:31");
            val params = DownloadFileOptions(url = downUrl, success = fun(result: DownloadFileSuccess){
                console.log("下载成功", result, " at common/utils.uts:36");
                val path: String = result.tempFilePath;
                val dirPath = path.substring(0, path.lastIndexOf("."));
                console.log("dirPath11111111111111", dirPath, " at common/utils.uts:39");
                uni_setStorage(SetStorageOptions(key = "H5Project", data = dirPath, success = fun(_) {
                    console.log("setStorage success", " at common/utils.uts:45");
                }
                ));
                val _params = UnzipFileOptions(zipFilePath = path, targetPath = dirPath, success = fun(result: FileManagerSuccessResult){
                    console.log("unzip result success", result, " at common/utils.uts:53");
                    resolve(true);
                }
                , fail = fun(result: IFileSystemManagerFail){
                    console.log("unzip result fail", result, " at common/utils.uts:57");
                }
                );
                val fileManager: FileSystemManager = uni_getFileSystemManager();
                val mkdirParams = MkDirOptions(dirPath = dirPath, recursive = false, success = fun(result: FileManagerSuccessResult){
                    console.log("mkdir result success", result, " at common/utils.uts:65");
                    fileManager.unzip(_params);
                }
                , fail = fun(result: IFileSystemManagerFail){
                    console.log("mkdir result fail", result, " at common/utils.uts:69");
                }
                );
                fileManager.mkdir(mkdirParams);
            }
            , fail = fun(result: DownloadFileFail){
                console.log("下载失败", result, " at common/utils.uts:75");
            }
            );
            val task = uni_downloadFile(params);
        }
        ));
    }
    );
}
;
open class GlobalConfig {
    companion object {
        var url: String = "https://hk.uenpay.com";
    }
}
var firstBackTime: Number = 0;
open class GenApp : BaseApp {
    constructor(instance: ComponentInternalInstance) : super(instance) {
        onLaunch(fun(_: OnLaunchOptions) {
            console.log("App Launch", " at App.uvue:9");
        }
        , instance);
        onLoad(fun(_: OnLoadOptions) {
            console.log("run...", " at App.uvue:12");
        }
        , instance);
        onAppShow(fun(_: OnShowOptions) {
            console.log("App Show", " at App.uvue:16");
            this.getSystemInfo();
            this.getH5();
        }
        , instance);
        onHide(fun() {
            console.log("App Hide111", " at App.uvue:21");
        }
        , instance);
        onLastPageBackPress(fun() {
            console.log("App Las3tPageBackPress", " at App.uvue:25");
            if (firstBackTime == 0) {
                uni_showToast(ShowToastOptions(title = "再按一次退1w3出应3用1", position = "bottom"));
                firstBackTime = Date.now();
                setTimeout(fun(){
                    firstBackTime = 0;
                }, 2000);
            } else if (Date.now() - firstBackTime < 2000) {
                firstBackTime = Date.now();
                uni_exit(null);
            }
        }
        , instance);
        onExit(fun() {
            console.log("App Exit", " at App.uvue:42");
        }
        , instance);
    }
    override fun `$initMethods`() {
        this.getH5 = fun() {
            httpRequest("" + GlobalConfig.url + "/ams/appPort/queryH5ProjectInfo", object : UTSJSONObject() {
                var odName = "sxzfnewzft"
            }, fun(res: UTSJSONObject){
                val url = res.get("projecth5link") as String;
                console.log("url", url, " at App.uvue:63");
                if (url !== "") {
                    downloadH5(url).then(fun(result){
                        console.log("downloadH5 result", result, " at App.uvue:66");
                        if (result) {
                            getFilePath();
                        }
                    }
                    );
                }
            }
            );
        }
        ;
        this.getSystemInfo = fun() {
            val result: GetWindowInfoResult = uni_getWindowInfo();
            val safeAreaInsets = result.safeAreaInsets;
            val safeArea = result.safeArea;
            val screenHeight = result.screenHeight;
            val statusBarHeight = result.statusBarHeight;
            val screenWidth = result.screenWidth;
            console.log("statusBarHeight", statusBarHeight, " at App.uvue:79");
            uni_setStorage(SetStorageOptions(key = "windowInfo", data = object : UTSJSONObject() {
                var safeAreaInsets = safeAreaInsets
                var safeArea = safeArea
                var screenHeight = screenHeight
                var screenWidth = screenWidth
                var statusBarHeight = statusBarHeight
            }));
        }
        ;
    }
    open lateinit var getH5: () -> Unit;
    open lateinit var getSystemInfo: () -> Unit;
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>>
            get() {
                return normalizeCssStyles(utsArrayOf(
                    styles0
                ));
            }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("uni-row" to padStyleMapOf(utsMapOf("flexDirection" to "row")), "uni-column" to padStyleMapOf(utsMapOf("flexDirection" to "column")));
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "app", name = "", inheritAttrs = true, inject = Map(), props = Map(), propsNeedCastKeys = utsArrayOf(), emits = Map(), components = Map(), styles = GenApp.styles);
}
, fun(instance): GenApp {
    return GenApp(instance);
}
);
val GenPagesIndexIndexClass = CreateVueComponent(GenPagesIndexIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesIndexIndex.inheritAttrs, inject = GenPagesIndexIndex.inject, props = GenPagesIndexIndex.props, propsNeedCastKeys = GenPagesIndexIndex.propsNeedCastKeys, emits = GenPagesIndexIndex.emits, components = GenPagesIndexIndex.components, styles = GenPagesIndexIndex.styles);
}
, fun(instance): GenPagesIndexIndex {
    return GenPagesIndexIndex(instance);
}
);
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass);
    return object : UTSJSONObject() {
        var app = app
    };
}
fun main(app: IApp) {
    definePageRoutes();
    defineAppConfig();
    (createApp()["app"] as VueApp).mount(app);
}
open class UniAppConfig : AppConfig {
    override var name: String = "sxzfnewzft";
    override var appid: String = "__UNI__EF87C14";
    override var versionName: String = "1.0.0";
    override var versionCode: String = "100";
    override var uniCompilerVersion: String = "4.24";
    constructor(){}
}
fun definePageRoutes() {
    __uniRoutes.push(PageRoute(path = "pages/index/index", component = GenPagesIndexIndexClass, meta = PageMeta(isQuit = true), style = utsMapOf("navigationStyle" to "custom", "navigationBarTitleText" to "")));
}
val __uniTabBar: Map<String, Any?>? = null;
val __uniLaunchPage: Map<String, Any?> = utsMapOf("url" to "pages/index/index", "style" to utsMapOf("navigationStyle" to "custom", "navigationBarTitleText" to ""));
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/index/index";
    __uniConfig.globalStyle = utsMapOf("navigationBarTextStyle" to "black", "navigationBarTitleText" to "uni-app x", "navigationBarBackgroundColor" to "#F8F8F8", "backgroundColor" to "#F8F8F8");
    __uniConfig.tabBar = __uniTabBar as Map<String, Any>?;
    __uniConfig.conditionUrl = "";
    __uniConfig.uniIdRouter = utsMapOf();
    __uniConfig.ready = true;
}
fun getApp(): GenApp {
    return getBaseApp() as GenApp;
}
