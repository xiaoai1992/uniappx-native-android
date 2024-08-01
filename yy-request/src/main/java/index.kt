@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uts.sdk.modules.yyRequest;
import android.os.Build;
import com.google.gson.Gson;
import com.uenpay.baselib.net.retrofit.UUIDHelper;
import com.uenpay.baselib.utils.crypt.AES;
import com.uenpay.baselib.utils.crypt.CryptUtils;
import com.uenpay.baselib.utils.crypt.HexStringToByte;
import com.uenpay.baselib.utils.crypt.RSA;
import com.uenpay.baselib.utils.xml.CreateXML;
import com.uenpay.baselib.utils.xml.SaxParser;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync;
import io.dcloud.uniapp.extapi.showToast as uni_showToast;
val gJson = Gson();
var tokenId: String = "";
val model: String = Build.MODEL;
val androidVersion: String = Build.VERSION.RELEASE;
var sysInfo: String = "Android_" + androidVersion + "_" + model + "_1.0.8_sxzfnewzft";
var myHttpManager = HttpManager();
typealias Method = (message: UTSJSONObject) -> Unit;
val httpRequest = fun(url: String, reassignedParams: UTSJSONObject, method: Method){
    var params = reassignedParams;
    params = upperJSONKey(params);
    val result = myHttpManager.run(url, params, method);
}
;
fun upperJSONKey(jsonObj: UTSJSONObject): UTSJSONObject {
    val obj = UTSJSONObject();
    for(key in jsonObj){
        if (UTSAndroid.`typeof`( jsonObj[key]) == "object") {
            obj["" + key.toUpperCase()] = upperJSONKey(jsonObj[key] as UTSJSONObject);
        } else {
            obj["" + key.toUpperCase()] = jsonObj[key].toString();
        }
    }
    return obj;
}
val lowJSONKey = fun(jsonObj: UTSJSONObject): UTSJSONObject {
    val obj = UTSJSONObject();
    for(key in jsonObj){
        obj["" + key.toLowerCase()] = jsonObj[key].toString();
    }
    return obj;
}
;
open class HttpManager {
    constructor(){}
    open fun run(url: String, params: UTSJSONObject, method: Method) {
        val postBody: String = requestBodyConverter(params);
        val mediaType: MediaType = MediaType.parse("application/xml; charset=utf-8") as MediaType;
        val body = RequestBody.create(mediaType, postBody);
        val request: Request = Request.Builder().url(url).post(body).build();
        val client = OkHttpClient.Builder().addInterceptor(MyInterceptor()).build();
        val res = client.newCall(request).enqueue(NCallBack(method));
    }
}
open class NCallBack : Callback {
    open var method: Method;
    constructor(method: Method){
        this.method = method;
    }
    override fun onFailure(call: Call, e: IOException) {
        console.log("e", e, " at uni_modules/yy-request/utssdk/app-android/index.uts:115");
    }
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful()) {
            throw IOException("Unexpected code " + response);
        }
        val responseBody: String = response.body()!!.string();
        val responseData: String = responseBodyConverter(responseBody);
        if (responseData != "") {
            val result = JSON.stringify(lowJSONKey(JSON.parse(responseData) as UTSJSONObject));
            val url: String = response.request()!!.url().toString();
            val resultObj: UTSJSONObject = JSON.parse(result) as UTSJSONObject;
            if (url.includes("getTokenId")) {
                tokenId = (resultObj as UTSJSONObject).get("tokenid") as String;
            }
            if (url.includes("appLogin")) {
                val accessToken = (resultObj as UTSJSONObject).get("access_token") as String;
                uni_setStorageSync("access_token", accessToken);
            }
            this.method(resultObj);
        }
    }
}
open class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        console.log("拦截请求.....", " at uni_modules/yy-request/utssdk/app-android/index.uts:150");
        val originalRequest: Request = chain.request();
        val newRequest: Request = originalRequest.newBuilder().addHeader("Authorization", "").addHeader("Content-Type", "application/xml;charset=utf-8").addHeader("Accept", "application/xml").addHeader("X-Mixin-Mode", "mixed").addHeader("sys-info", sysInfo).build();
        val response: Response = chain.proceed(newRequest);
        return response;
    }
}
val requestBodyConverter = fun(requestParam: UTSJSONObject): String {
    console.log("requestParam", requestParam, " at uni_modules/yy-request/utssdk/app-android/index.uts:173");
    val map: HashMap<String, String> = HashMap<String, String>();
    for(key in requestParam){
        map.put(key, requestParam[key] as String);
    }
    val df: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    val date: Date = Date();
    if (tokenId !== "") {
        map.put("TOKENID", tokenId);
    }
    map.put("TRANDATE", df.format(Date()));
    val uuid: String = getNonce();
    UUIDHelper.getInstance().add(uuid);
    map.put("TRANNONCE", uuid);
    map.put("PCSIM", "获取不到");
    var assetManager = UTSAndroid.getAppContext()!!.getAssets();
    var cer = assetManager.open("uen.cer");
    var mRequestParam: String = CreateXML.create(map);
    val PACKAGEMAC: String = CryptUtils.encryptToMD5(mRequestParam);
    map.put("PACKAGEMAC", PACKAGEMAC);
    mRequestParam = CreateXML.create(map);
    val randomKey: ByteArray = CryptUtils.randomBytes(16);
    val signKey: ByteArray = RSA.encryptByPublicKey(randomKey, cer);
    val signStr: String = HexStringToByte.bytesToHexString(signKey);
    val encryParams: ByteArray = AES.encrypt(mRequestParam, randomKey);
    val res = signStr + HexStringToByte.bytesToHexString(encryParams);
    return res;
}
;
val responseBodyConverter = fun(responseParam: String): String {
    if (responseParam !== "") {
        var assetManager = UTSAndroid.getAppContext()!!.getAssets();
        var cer = assetManager.open("uen.cer");
        var result = "";
        val random: ByteArray = RSA.decryptByPublicKey(HexStringToByte.hexStringToByte(responseParam.substring(0, 512)), cer);
        result = String(AES.decrypt(HexStringToByte.hexStringToByte(responseParam.substring(512)), random));
        val map: HashMap<String, String> = SaxParser.parser(result) as HashMap<String, String>;
        if (map != null && map.containsKey("RSPCOD")) {
            val code: String? = map.get("RSPCOD");
            if ("00".equals(code)) {
                val res = gJson.toJson(map);
                return res;
            } else {
                val msg: String? = map.get("RSPMSG");
                console.log("err msg", msg, " at uni_modules/yy-request/utssdk/app-android/index.uts:221");
                uni_showToast(ShowToastOptions(title = msg as String, duration = 6000, icon = "none", success = fun(_) {
                    console.log("uni.showToast success!", " at uni_modules/yy-request/utssdk/app-android/index.uts:227");
                }
                , fail = fun(err){
                    console.log("uni.showToast success: ", err, " at uni_modules/yy-request/utssdk/app-android/index.uts:230");
                }
                ));
                return "";
            }
        }
        return result;
    } else {
        return "";
    }
}
;
val getNonce = fun(): String {
    return UUID.randomUUID().toString();
}
;
