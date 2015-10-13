package com.glsct.api.constant.services;

/**
 * �߼�����2xx
 * ��������1xx
 * �ɹ���0
 * Created by Administrator on 2015/10/6.
 */
public interface ServiceStatusCode {
    /**
     * ��������
     */
    public static final int Params_Except = 100;

    /**
     * ��¼ʧ��
     */
    public static final int Login_Except = 101;


    public static final int Busi_Except = 200;

    /**
     * 没有更多的数据
     */
    public static final int No_More_Data = 201;

    /**
     * �������쳣
     */
    public static final int Server_Except = 1;

    /**
     * ִ�гɹ�
     */
    public static final int Sucess = 0;

    /**
     * δ֪�쳣
     */
    public static final int Unknow_Except = -1;
}
