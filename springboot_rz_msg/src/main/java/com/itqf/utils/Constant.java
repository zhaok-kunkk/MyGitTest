package com.itqf.utils;
/**
*author：李丽婷
*company：千锋互联
*date:2018年7月16日 上午11:14:57
*file:Constant.java
*desc:项目中所有的常量
*/
public class Constant {
	
	public static final String KAPTCHA_KEY = "kaptcha";//验证码存储在session中的key
	public static final String JOB_KEY_PREFIX = "JOB_";
	public static final String TRIGGER_KEY_PREFIX = "TRIGGER_";
	public static final String  JOBDATAMAP_KEY = "SCHEDULEJOB";

	
	//定义一个枚举
	 /**
     * 定时任务状态
     *
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL((byte)0),
        /**
         * 暂停
         */
        PAUSE((byte)1);

        private byte value;

        private ScheduleStatus(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }


}
