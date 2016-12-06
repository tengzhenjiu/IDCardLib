package com.megvii.idcardlib;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class UpLoad {
	static Boolean isAlive;
	static Boolean isError;
	static String results;

	public static void imageOCR(String filePath) {
		isAlive = true;
		isError = false;
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		String url = "https://api.faceid.com/faceid/v1/ocridcard";
		params.addBodyParameter("api_key", "giXLvrtOprJEhVhxHd30FLqIIiTxP-Vd");
		params.addBodyParameter("api_secret", "0E6Nn9McJI7ar_ZsZXb1XqcuL86G0se6");
		params.addBodyParameter("image", new File(filePath));// 身份证照片图片地址
		params.addBodyParameter("legality", 1 + "");// 传入1可以判断身份证是否
													// 被编辑/是真实身份证/是复印件/是屏幕翻拍/是临时身份证
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			// 请求失败调用次方法
			@Override
			public void onFailure(HttpException error, String msg) {
				isError = true;
				int exceptionCode = error.getExceptionCode();
				if (exceptionCode == 0) {
					String errorMessage = "请检查网络连接是否正常！";
					System.out.println(errorMessage);
				}
				isAlive = false;
			}

			// 请求成功调用此方法
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				isError = false;
				System.out.println("上传成功");
				results = arg0.result;
				isAlive = false;
			}
		});
	}
}
