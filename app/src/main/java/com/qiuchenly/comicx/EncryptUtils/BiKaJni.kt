package  com.qiuchenly.comicx.EncryptUtils

import android.util.Log

/**
 * 哔咔原生加密工具类,已逆向出其算法
 */
object BiKaJni {
    private var TAG = "BiKaJni"

//    external fun getStringComFromNative(): String
//
//    external fun getStringConFromNative(strArr: Array<String>): String
//
//    external fun getStringFromNative(): String
//
//    external fun getStringSigFromNative(): String

    init {
        //System.loadLibrary("JniTest")
    }

    private var generateSignature: GenerateSignature? = null

/*    fun getStringCom(): Boolean {
        return (getStringComFromNative() + "").equals("1", ignoreCase = true)
    }*/

    fun getStringCon(parameters: Array<String>): String {
        if (generateSignature == null) {
            generateSignature = GenerateSignature()
        }
        var params = ""
        for (str in parameters) {
            params = "$params$str, "
        }
        Log.d(TAG, "getStringCon:$params")
        val conString = getStringConFromNativeLocal(parameters)
        //https://picaapi.picacomic.com/,
        // auth/sign-in,
        // 1550991794,
        // 88149e8e27304a589f199286744ac1b0,
        // POST,
        // C69BAF41DA5ABD1FFEDC6D2FEA56B,
        // 2.1.0.5,
        // 39,


        // auth/sign-in
        // 1550991794
        // 88149e8e27304a589f199286744ac1b0
        // POST
        // C69BAF41DA5ABD1FFEDC6D2FEA56B
        Log.d(TAG, "CONCAT parameters = $conString")
        Log.d(TAG, "CONCAT KEY = " + StringSigFromNativeLocal())
        return generateSignature!!.getSignature(conString, StringSigFromNativeLocal())!!
    }

    /**
     * 本方法的算法来自于JNI逆向汇编源码,大部分计算是冗余代码.
     * 我就是故意写的.
     * @param parameters 参数列表数组
     * @return 返回拼接好的待加密字符串
     */
    fun getStringConFromNativeLocal(parameters: Array<String>): String {
//        v49 = (v4[173])();
        val v49 = parameters[0]
//        v7 = ((*v3)->GetStringUTFChars)(v3, v49, 0LL);
        val v7 = v49
//        v48 = ((*v3)->GetObjectArrayElement)(v3, v5, 1LL);
        val v48 = parameters[1]
//        v8 = ((*v3)->GetStringUTFChars)(v3, v48, 0LL);
        val v8 = v48
//        v46 = ((*v3)->GetObjectArrayElement)(v3, v5, 2LL);
        val v46 = parameters[2]
//        v9 = ((*v3)->GetStringUTFChars)(v3, v46, 0LL);
        val v9 = v46
//        v45 = ((*v3)->GetObjectArrayElement)(v3, v5, 3LL);
        val v45 = parameters[3]
//        v10 = ((*v3)->GetStringUTFChars)(v3, v45, 0LL);
        val v10 = v45
//        v43 = ((*v3)->GetObjectArrayElement)(v3, v5, 4LL);
        val v43 = parameters[4]
//        v11 = ((*v3)->GetStringUTFChars)(v3, v43, 0LL);
        val v11 = v43
//        v41 = ((*v3)->GetObjectArrayElement)(v3, v5, 5LL);
        val v41 = parameters[5]
//        v12 = ((*v3)->GetStringUTFChars)(v3, v41, 0LL);
        val v12 = v41
//        v38 = ((*v3)->GetObjectArrayElement)(v3, v5, 6LL);
        val v38 = parameters[6]

//        v13 = ((*v3)->GetStringUTFChars)(v3, v38, 0LL);
        val v13 = v38
//        v36 = ((*v3)->GetObjectArrayElement)(v3, v5, 7LL);
        val v36 = parameters[7]

//        v14 = ((*v3)->GetStringUTFChars)(v3, v36, 0LL);
        val v14 = v36
//        v47 = v7;
        val v47 = v7
//        v15 = strlen(v7);
        val v15 = v7.length
//        v39 = v8;
        val v39 = v8
//        v16 = strlen(v8);
        val v16 = v8.length
//        v40 = v9;
        val v40 = v9
//        v17 = strlen(v9);
        val v17 = v9.length
//        v44 = v10;
        val v44 = v10
//        v18 = strlen(v10);
        val v18 = v10.length
//        v42 = v11;
        val v42 = v11
//        v19 = strlen(v11);
        val v19 = v11.length
//        v20 = v12;
        val v20 = v12
//        v37 = v12;
        val v37 = v12
//        v21 = v13;
//        v22 = v14;
//        v23 = strlen(v20);
//        v24 = strlen(v21);
//        v25 = strlen(v22);
//        retMessage = malloc(v15 + v16 + v17 + v18 + v19 + v23 + v24 + v25 + 2);
//        v35 = v21;
//        if ( (genKey10)(v3, v6) )
        var retMessage = ""
        if (1 == 1) {
//            v27 = v39;
//            strcpy(retMessage, v39);
            retMessage = v39
//            v28 = v40;
//            strcat(retMessage, v40);
            retMessage += v40
//            v29 = v44;
//            strcat(retMessage, v44);
            retMessage += v44
//            v30 = v42;
//            strcat(retMessage, v42);
            retMessage += v42
//            v31 = v37;
//            strcat(retMessage, v37);
            retMessage += v37
//            v32 = v47;
        } else {
//            v31 = v37;
//            strcpy(retMessage, v37);
//            strcat(retMessage, v22);
//            v27 = v39;
//            strcat(retMessage, v39);
//            v32 = v47;
//            strcat(retMessage, v47);
//            v28 = v40;
//            strcat(retMessage, v40);
//            v30 = v42;
//            strcat(retMessage, v42);
//            strcat(retMessage, v21);
//            v29 = v44;
//            strcat(retMessage, v44);
        }
        return retMessage
    }

    fun StringSigFromNativeLocal() =
        "~d}\$Q7\$eIni=V)9\\RK/P.RM4;9[7|@/CA}b~OW!3?EV`:<>M7pddUBL5n|0/*Cn"//"~n}\$S9\$lGts=U)8zfL/R.PM9;4[3|@/CEsl~Kk!7?BYZ:BAa5zkkRBL7r|1/*Cr"

}