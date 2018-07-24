package io.github.anonymousname.debuger;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        String packageName="io.github.anonymousname.behook";
        String className="MainActivity";
        String methodName="print";
        if(lpparam.packageName.equals(packageName)){
            Class clazz =lpparam.classLoader.loadClass(packageName+"."+className);
            XposedHelpers.findAndHookMethod(clazz, methodName, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    print(param,Thread.currentThread());
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    print(param,Thread.currentThread());
                    super.beforeHookedMethod(param);
                }
            });
        }
    }

    public void print(XC_MethodHook.MethodHookParam param,Thread thread){
        XposedBridge.log("InjectLogByNull\n");
        XposedBridge.log("=====================================\n");
        XposedBridge.log("param_Object:\t"+param.thisObject.toString()+ "\n");
        XposedBridge.log("param_Method:\t"+param.method.getName()+ "\n");

        Object[] args = param.args;
        if (args != null) {
            XposedBridge.log("=====================================\n");
            for(int i=0;i<args.length;i++){
                XposedBridge.log("=====================================\n");
                try {
                    XposedBridge.log("Id:\t" + i + "\n");
                    XposedBridge.log("args:\t" +args[i].toString()+ "\n");
                }catch (Exception e){
                    XposedBridge.log("Id:\t" + i + "\n");
                    XposedBridge.log("Error:\n" + e.toString() + "\n");
                }
                XposedBridge.log("=====================================\n");
            }
        }

        Object result = param.getResult();
        if (result != null) {
            XposedBridge.log("Result:\t" + result.toString() + "\n");
        }

        StackTraceElement[] stackTraceElement = thread.getStackTrace();
        if (stackTraceElement != null) {
            XposedBridge.log("=====================================\n");
            for(int i=0;i<stackTraceElement.length;i++){
                XposedBridge.log("=====================================\n");
                try {
                    XposedBridge.log("Id:\t" + i + "\n");
                    XposedBridge.log("thread:\t" + thread.getId() + "\n");
                    XposedBridge.log("ClassName:\t" + stackTraceElement[i].getClassName() + "\n");
                    XposedBridge.log("MethodName:\t" + stackTraceElement[i].getMethodName() + "\n");
                    XposedBridge.log("FileName:\t" + stackTraceElement[i].getFileName() + "\n");
                } catch (Exception e) {
                    XposedBridge.log("Id:\t" + i + "\n");
                    XposedBridge.log("thread:\t" + thread.getId() + "\n");
                    XposedBridge.log("Error:\n" + e.toString() + "\n");
                }
                XposedBridge.log("=====================================\n");
            }
            XposedBridge.log("=====================================\n");
        }
    }
}