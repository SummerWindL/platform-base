package com.platform.common.enumeration;


/**
 *文件类型
 *文件类型及文件夹名称
 *@version 2.0
 *@see [相关类/方法]
 *@since [产品/模块版本]
 */
public enum EnumFileType {

//	fileType=10000, 未知
//	fileType=10001, 头像文件
//	fileType=10002, 语音留言文件
//	fileType=10003, 视频TS文件
//	fileType=10004, 图片文件
//	fileType=10005, 日志文件
//	fileType=20000, 心电数据文件

	FILE_TYPE_TEMP("10000","temp"),
	FILE_TYPE_ICON("10001","icon"),
	FILE_TYPE_AUDIO("10002","audio"),
	FILE_TYPE_VIDEO("10003","video"),
	FILE_TYPE_IMAGE("10004","image"),
	FILE_TYPE_LOG("10005","log"),
	FILE_TYPE_ECG("20000","ecg");

	private String fileType;

	private String fileTypeDesc;

	EnumFileType(String fileType, String fileTypeDesc) {
		this.fileType = fileType;
		this.fileTypeDesc = fileTypeDesc;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeDesc() {
		return fileTypeDesc;
	}

	public void setFileTypeDesc(String fileTypeDesc) {
		this.fileTypeDesc = fileTypeDesc;
	}

	public static boolean isSupportType(String fileType) {
		for (EnumFileType enumFileType : values()) {
			if (enumFileType.getFileType().equals(fileType)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getFileTypeDesc(String fileType) {
		for (EnumFileType enumFileType : values()) {
			if (enumFileType.getFileType().equals(fileType)) {
				return enumFileType.getFileTypeDesc();
			}
		}
		return null;
	}

}
