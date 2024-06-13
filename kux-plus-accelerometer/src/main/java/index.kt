@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uts.sdk.modules.kuxPlusAccelerometer;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
open class Acceleration (
    @JsonNotNull
    open var xAxis: Number,
    @JsonNotNull
    open var yAxis: Number,
    @JsonNotNull
    open var zAxis: Number,
) : UTSObject()
typealias AccelerationSuccessCallback = (acceleration: Acceleration) -> Unit;
typealias AccelerationErrorCallback = (error: UTSError) -> Unit;
open class AccelerometerOption (
    @JsonNotNull
    open var frequency: Number,
) : UTSObject()
interface IAccelerometer {
    fun getCurrentAcceleration(successCB: AccelerationSuccessCallback)
    fun getCurrentAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?)
    fun watchAcceleration(successCB: AccelerationSuccessCallback): Number
    fun watchAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?): Number
    fun watchAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?, options: AccelerometerOption?): Number
    fun clearWatch(watchId: Number)
}
open class AccelerometerListener : SensorEventListener {
    private var _callback: (event: SensorEvent) -> Unit;
    constructor(callback: (event: SensorEvent) -> Unit){
        this._callback = callback;
    }
    override fun onSensorChanged(event: SensorEvent): Unit {
        this._callback(event);
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int): Unit {}
}
open class AccelerometerManager : IAccelerometer {
    private var _sensorManager: SensorManager? = null;
    private var _accelerometerSensor: Sensor;
    private var _x: Number = 0;
    private var _y: Number = 0;
    private var _z: Number = 0;
    private var _currentWatchId: Number = -1;
    private var _watchIdMap: Map<Number, SensorEventListener> = Map();
    private var _lastSuccessCallbackTime: Number = 0;
    constructor(){
        val context = UTSAndroid.getAppContext()!! as Context;
        this._sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        this._accelerometerSensor = this._sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!;
    }
    override fun getCurrentAcceleration(successCB: AccelerationSuccessCallback): Unit {
        return this.getCurrentAcceleration(successCB as AccelerationSuccessCallback, null);
    }
    override fun getCurrentAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?): Unit {
        try {
            var accelerometerListener: SensorEventListener? = null;
            accelerometerListener = AccelerometerListener(fun(event){
                this._x = UTSNumber.from(event.values[0]);
                this._y = UTSNumber.from(event.values[1]);
                this._z = UTSNumber.from(event.values[2]);
                successCB(Acceleration(xAxis = this._x, yAxis = this._y, zAxis = this._z));
                this._sensorManager?.unregisterListener(accelerometerListener);
            }
            );
            this._sensorManager!!.registerListener(accelerometerListener, this._accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
         catch (err: Throwable) {
            errorCB?.invoke(err as UTSError);
        }
    }
    override fun watchAcceleration(successCB: AccelerationSuccessCallback): Number {
        return this.watchAcceleration(successCB as AccelerationSuccessCallback, null, null);
    }
    override fun watchAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?): Number {
        return this.watchAcceleration(successCB as AccelerationSuccessCallback, errorCB as AccelerationErrorCallback?, null);
    }
    override fun watchAcceleration(successCB: AccelerationSuccessCallback, errorCB: AccelerationErrorCallback?, options: AccelerometerOption?): Number {
        try {
            this._currentWatchId++;
            var accelerometerListener: SensorEventListener? = null;
            val interval = options?.frequency ?: 500;
            console.log(interval, " at uni_modules/kux-plus-accelerometer/utssdk/app-android/accelerometerManager.uts:74");
            accelerometerListener = AccelerometerListener(fun(event){
                this._x = UTSNumber.from(event.values[0]);
                this._y = UTSNumber.from(event.values[1]);
                this._z = UTSNumber.from(event.values[2]);
                val currentTime = System.currentTimeMillis();
                this._watchIdMap.set(this._currentWatchId, accelerometerListener!!);
                if ((currentTime - this._lastSuccessCallbackTime) > interval) {
                    this._lastSuccessCallbackTime = currentTime;
                    successCB(Acceleration(xAxis = this._x, yAxis = this._y, zAxis = this._z));
                }
            }
            );
            this._sensorManager!!.registerListener(accelerometerListener, this._accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            return this._currentWatchId;
        }
         catch (err: Throwable) {
            errorCB?.invoke(err as UTSError);
            return this._currentWatchId;
        }
    }
    override fun clearWatch(watchId: Number): Unit {
        val watchListener = this._watchIdMap.get(watchId);
        if (watchListener != null) {
            this._sensorManager?.unregisterListener(watchListener);
        }
    }
    open fun destory() {
        this._sensorManager = null;
        this._watchIdMap.clear();
    }
}
fun useAccelerometer(): IAccelerometer {
    return AccelerometerManager();
}
