package com.example.wsq.library.tools;

import android.content.Context;

import com.example.wsq.library.utils.RSAUtils;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class RequestParamParse {

    private static RequestParamParse requestParam = null;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private RequestParamParse(){

    }
    public static RequestParamParse getInstance (Context context){

            if(requestParam == null){
                requestParam = new RequestParamParse();

                String key = onContent(context,"rsa/publicKey.properties");
                publicKey = RSAUtils.keyStrToPublicKey(key);
                String pkey = onContent(context,"rsa/privateKey.properties");
                privateKey = RSAUtils.keyStrToPrivate(pkey);

            }

        return requestParam;
    }



    /**
     * 加密
     * @param param
     * @return
     */
    public  static String onEncrypt(Map<String, String> param) {

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        param.put("aaa", System.currentTimeMillis()+"");
        sortMap.putAll(param);
        Iterator<Map.Entry<String, String>> it = sortMap.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String value = entry.getValue();
            String valueHex = str2HexStr(value);
            //取值长度
            int length = valueHex.length();
            String hexLength = Integer.toHexString(length);

            for (int i = 0; ; i++) {
                if (hexLength.length() <3) {
                    hexLength = "0" + hexLength;
                }else {
                    break;
                }
            }
            sb.append(hexLength+valueHex);

        }


        return RSAUtils.encryptDataByPublicKey(sb.toString().getBytes(), publicKey);
    }

    /**
     * 解密返回的参数
     * @param str
     */
    public static String onDecrypt(String str) {

        String encrypt = RSAUtils.decryptedToStrByPrivate(str, privateKey);
        Logger.d(encrypt);
        int start = 0;
        StringBuffer sb = new StringBuffer();

        for (int i = 0; ; i++) {
            if (start >= encrypt.length()) {
                break;
            }
            int length = onSubLength(encrypt, start);
            start += 3;
            String plaintext = onSubParam(encrypt, start, start+length);
            sb.append(plaintext +"   ");
            start += length;
        }
        return sb.toString();
    }

    private static int onSubLength(String str, int start) {

        String slength = str.substring(start, start+3);
        try {
            int length = Integer.parseInt(slength, 16);
            return length;
        }catch (NumberFormatException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }
    private static String onSubParam(String str, int start, int end) {
        String encrypt = str.substring(start, end);
        String string = hexStr2Str(encrypt);

        return string;
    }
    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
    public static String str2HexStr(String origin) {
        byte[] bytes = origin.getBytes();
        String hex = bytesToHexString(bytes);
        return hex;
    }
    public static String hexStr2Str(String hex) {
        byte[] bb = hexStringToBytes(hex);
        String rr = new String(bb);
        return rr;
    }
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static String onContent(Context context, String fileName){
        String content="";
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";

            while((line = bufReader.readLine()) != null)
                content += line;

            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}
