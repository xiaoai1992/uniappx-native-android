@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uts.sdk.modules.yyWebview;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.dcloud.uni_modules.yyWebview.R;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
import io.dcloud.uniapp.extapi.getStorage as uni_getStorage;
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync;
var filePathListObj: HashMap<String, String> = HashMap<String, String>();
var directoryPath: String = "";
var webViewUrl = "";
var pickImgKey: Int = 0;
var f: Uri? = null;
var webView: WebView? = null;
var startTime: Number = 0;
val MY_PERMISSIONS_REQUEST_CODE: Int = 0;
val initWebView = fun(){
    webView = WebView(UTSAndroid.getAppContext() as Context);
    val settings = (webView as WebView).getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setAllowFileAccess(true);
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
    settings.setBlockNetworkImage(false);
    console.log("WebSettings.LOAD_NO_CACHE", WebSettings.LOAD_NO_CACHE, " at uni_modules/yy-webview/utssdk/app-android/index.uts:58");
    settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    val userAgentContent = settings.getUserAgentString();
    settings.setUserAgentString("" + userAgentContent + " sxzfnewzft");
}
;
val runBlock1 = run {
    initWebView();
}
val setStatusBarBackground = fun(activity: Activity){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = activity.getWindow();
        val decorView: View = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(Color.WHITE);
    }
}
;
val getContentAfterLastSlash = fun(url: String): String {
    var match = url.replace(UTSRegExp(".*\\/", ""), "");
    if (match.indexOf("?") !== -1) {
        match = match.substring(0, match.indexOf("?"));
    }
    return match;
}
;
val resolveResourceType = fun(url: String): String {
    val typeList = url.match(UTSRegExp("\\.(\\w+)(?=\\?|\$)", ""));
    if (typeList != null) {
        return typeList[1] as String;
    }
    return "";
}
;
open class FileReader {
    public open fun listFilesInDirectory(directoryPath: String): Unit {
        val directory: File = File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            val files = directory.listFiles();
            if (files != null) {
                var file: Any;
                for(file in files){
                    val path = file.getPath();
                    val name = getContentAfterLastSlash(path);
                    filePathListObj.put(name, path);
                }
            }
        } else {
            console.log("Directory does not exist or is not a directory.", " at uni_modules/yy-webview/utssdk/app-android/index.uts:130");
        }
    }
}
val getFilePath = fun(){
    val params = GetStorageOptions(key = "H5Project", success = fun(result){
        val fileReader = FileReader();
        console.log("获取地址...", result.data, " at uni_modules/yy-webview/utssdk/app-android/index.uts:140");
        val path = result.data as String;
        directoryPath = path;
        fileReader.listFilesInDirectory(path);
    }
    , fail = fun(err){
        console.log("获取失败", err, " at uni_modules/yy-webview/utssdk/app-android/index.uts:146");
    }
    );
    uni_getStorage(params);
}
;
val customResponse = fun(mimeType: String, path: String, time: Number): WebResourceResponse {
    val file: File = File(path);
    val fileStream: FileInputStream = FileInputStream(file);
    return WebResourceResponse(mimeType, "utf-8", fileStream);
}
;
open class NewWebViewClient : WebViewClient {
    constructor() : super() {}
    override fun onPageFinished(view: WebView, url: String): Unit {
        super.onPageFinished(view, url);
    }
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url);
        return true;
    }
    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        var reqTime: Number = System.currentTimeMillis();
        if (directoryPath == "" || filePathListObj.size == 0) {
            return null;
        }
        val url: String = request.getUrl().toString();
        if (url.includes("#")) {
            try {
                return customResponse("text/html", "" + directoryPath + "/index.html", reqTime);
            }
             catch (e: Throwable) {
                console.log(e, " at uni_modules/yy-webview/utssdk/app-android/index.uts:189");
            }
        }
        val name = getContentAfterLastSlash(url);
        val type = resolveResourceType(url);
        val path = filePathListObj.get(name);
        if (name == "" || type == "" || path == null) {
            return null;
        }
        var mimeType = "";
        when (type) {
            "js" -> 
                mimeType = "application/javascript";
            "css" -> 
                mimeType = "text/css";
            "png" -> 
                mimeType = "image/png";
            "jpg" -> 
                mimeType = "image/jpeg";
        }
        return customResponse(mimeType, path as String, reqTime);
    }
}
open class JSBridge {
    private var mContext: Context;
    private var v: WebView;
    private var imgAct: ImgActivity;
    constructor(v: WebView, context: Context){
        this.v = v;
        this.mContext = context;
        this.imgAct = ImgActivity(context, v);
    }
    public open fun showToast(message: String): Unit {
        Toast.makeText(this.v.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public open fun goBack(): Unit {
        UTSAndroid.getUniActivity()!!.finish();
    }
    @JavascriptInterface
    public open fun jumpToNative(info: String): Unit {
        if (info != "") {
            try {
                val infoData = JSON.parse(info);
                val id = (infoData as UTSJSONObject)["id"];
                when (id) {
                    "1001" -> 
                        {
                            UTSAndroid.getUniActivity()!!.finish();
                            return;
                        }
                }
            } catch (e: Throwable) {
                console.log("e", e, " at uni_modules/yy-webview/utssdk/app-android/index.uts:254");
            }
        } else {
            console.log("请传入参数。。。", " at uni_modules/yy-webview/utssdk/app-android/index.uts:257");
        }
    }
    @JavascriptInterface
    public open fun getClientInfo(): String {
        val obj = object : UTSJSONObject() {
            var data = object : UTSJSONObject() {
                var XAppInfo = UTSJSONObject()
            }
        };
        return JSON.stringify(obj);
    }
    @JavascriptInterface
    public open fun getAppTokenJson(): String {
        val token = uni_getStorageSync("access_token");
        val obj = object : UTSJSONObject() {
            var data = object : UTSJSONObject() {
                var token = token
            }
        };
        return JSON.stringify(obj);
    }
    @JavascriptInterface
    public open fun getUserInfo(): String {
        val obj = object : UTSJSONObject() {
            var data = object : UTSJSONObject() {
                var odName = "sxzfnewzft"
            }
        };
        return JSON.stringify(obj);
    }
    @JavascriptInterface
    public open fun uploadPic(info: String): Unit {
        console.log("调用相机", " at uni_modules/yy-webview/utssdk/app-android/index.uts:294");
        console.log(info, " at uni_modules/yy-webview/utssdk/app-android/index.uts:295");
        if (info != "") {
            try {
                val infoData = JSON.parse(info);
                val type = (infoData as UTSJSONObject)["type"];
                val imgType = (infoData as UTSJSONObject)["type"];
                console.log("type", type, " at uni_modules/yy-webview/utssdk/app-android/index.uts:301");
                when (type) {
                    "album" -> 
                        {
                            this.imgAct.openAlbum();
                            return;
                        }
                    "camera" -> 
                        {
                            if (ContextCompat.checkSelfPermission(UTSAndroid.getUniActivity() as Activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UTSAndroid.getUniActivity() as Activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UTSAndroid.getUniActivity() as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                val pList = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                ActivityCompat.requestPermissions(UTSAndroid.getUniActivity() as Activity, pList, MY_PERMISSIONS_REQUEST_CODE);
                            }
                            this.imgAct.openCamera();
                            return;
                        }
                }
            }
             catch (e: Throwable) {
                console.log("e", e, " at uni_modules/yy-webview/utssdk/app-android/index.uts:324");
            }
        }
    }
    @JavascriptInterface
    public open fun webToLogin(): Unit {
        console.log("webToLogin..........", " at uni_modules/yy-webview/utssdk/app-android/index.uts:332");
    }
}
open class DemoActivity : Activity {
    constructor() : super() {}
    override fun onCreate(savedInstanceState: Bundle?): Unit {
        console.log("on created!!!!!!!!!!!!!!!!!", " at uni_modules/yy-webview/utssdk/app-android/index.uts:342");
        super.onCreate(savedInstanceState);
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        this.overridePendingTransition(R.layout.from_right, R.layout.out_left);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarBackground(this);
        this.setContentView(webView as WebView);
        val jsBridge: JSBridge = JSBridge((webView as WebView), this);
        (webView as WebView).addJavascriptInterface(jsBridge, "android");
        (webView as WebView).loadUrl(webViewUrl);
        (webView as WebView).setWebViewClient(NewWebViewClient());
        (webView as WebView).setWebChromeClient(WebChromeClient());
    }
    override fun onDestroy() {
        if (webView != null) {
            (webView as WebView).loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            (webView as WebView).clearHistory();
            ((webView as WebView).parent as ViewGroup).removeView(webView);
            (webView as WebView).destroy();
            webView = null;
            initWebView();
        }
        super.onDestroy();
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        console.log("pickImgKey3333", pickImgKey, " at uni_modules/yy-webview/utssdk/app-android/index.uts:381");
        if (requestCode == pickImgKey && resultCode == RESULT_OK) {
            var imageUri: Uri = Uri.parse("http://123");
            if (data != null) {
                if (pickImgKey == 0) {
                    imageUri = data.getData() as Uri;
                    console.log("uri", imageUri, " at uni_modules/yy-webview/utssdk/app-android/index.uts:389");
                }
            } else {
                console.log("赋值。。。", " at uni_modules/yy-webview/utssdk/app-android/index.uts:392");
                if (f != null) {
                    imageUri = f as Uri;
                }
                console.log("uri", imageUri, " at uni_modules/yy-webview/utssdk/app-android/index.uts:394");
            }
            try {
                val inputStream: InputStream = getContentResolver().openInputStream(imageUri) as InputStream;
                val yourSelectedImage: Bitmap = BitmapFactory.decodeStream(inputStream);
                val base64String: String = getBase64String(yourSelectedImage);
                (webView as WebView).evaluateJavascript("javascript:uploadImgH5CallBack('" + base64String + "')", null);
            } catch (e: Throwable) {
                console.log("e", e, " at uni_modules/yy-webview/utssdk/app-android/index.uts:409");
            }
        } else {
            console.log("关闭相机！！！", " at uni_modules/yy-webview/utssdk/app-android/index.uts:412");
        }
    }
    private fun getBase64String(bitmap: Bitmap): String {
        val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream();
        var options: Int = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
        val _byArray = byteArrayOutputStream.toByteArray();
        while(_byArray.size / 1024 > 200){
            byteArrayOutputStream.reset();
            if (options > 10) {
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            } else {
                options -= 1;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
                break;
            }
        }
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
}
@Suppress("DEPRECATION")
open class IntentRunable : Runnable {
    override fun run() {
        console.log("IntentRunable = " + Thread.currentThread().getName(), " at uni_modules/yy-webview/utssdk/app-android/index.uts:443");
        var intent = Intent(UTSAndroid.getUniActivity(), DemoActivity().javaClass);
        UTSAndroid.getUniActivity()!!.startActivity(intent);
    }
}
fun gotoDemoActivity(url: String): Boolean {
    startTime = System.currentTimeMillis();
    startTime;
    webViewUrl = url;
    var hasXActivityIntegration = true;
    Looper.prepare();
    try {
        var packageManager = UTSAndroid.getUniActivity()!!.getPackageManager();
        var intent = Intent(UTSAndroid.getUniActivity(), DemoActivity().javaClass);
        var resolveInfo = packageManager.queryIntentActivities(intent, 0);
        console.log(resolveInfo.size, " at uni_modules/yy-webview/utssdk/app-android/index.uts:459");
        if (resolveInfo.size == 0) {
            hasXActivityIntegration = false;
        }
    }
     catch (e: Exception) {
        console.log("e", e.message, " at uni_modules/yy-webview/utssdk/app-android/index.uts:465");
        console.log("e", JSON.stringify(e), " at uni_modules/yy-webview/utssdk/app-android/index.uts:466");
        hasXActivityIntegration = false;
    }
    if (!hasXActivityIntegration) {
        return false;
    }
    UTSAndroid.getUniActivity()!!.runOnUiThread(IntentRunable());
    Looper.loop();
    return true;
}
open class ImgActivity : Activity {
    private var context: Context;
    private var v: WebView;
    constructor(c: Context, v: WebView){
        this.context = c;
        this.v = v;
    }
    public open fun openAlbum(): Unit {
        pickImgKey = 0;
        val intent: Intent = Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        UTSAndroid.getUniActivity()!!.startActivityForResult(intent, pickImgKey);
    }
    public open fun openCamera(): Unit {
        pickImgKey = 1;
        val intent: Intent = Intent();
        val fileName = "" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg";
        var file: File = File(UTSAndroid.getAppContext()!!.getExternalCacheDir()!!.getPath(), fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val packageName = this.context.getPackageName();
            f = UTSAndroid.getFileProviderUri(file);
        } else {
            f = Uri.fromFile(file);
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, f);
        if (intent.resolveActivity(this.context.getPackageManager()) != null) {
            UTSAndroid.getUniActivity()!!.startActivityForResult(intent, pickImgKey);
        }
    }
}
