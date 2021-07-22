package com.liming.licensebuilder.license;

import com.liming.licensebuilder.entity.LicenseCheckModel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author ltf
 * @date 2021-07-20 16:25
 */
@Slf4j
public abstract class AbstractServerInfos {

    /**
     * 获取需要额外校验的服务器参数
     * @return
     */
    public LicenseCheckModel getServerInfos(){
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
        }catch (Exception e){
            log.error("获取服务器硬件信息失败",e);
        }

        return result;
    }
    /**
     * 获取IP地址
     * @return java.util.List<java.lang.String>
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     * @return java.util.List<java.lang.String>
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * 获取当前服务器所有符合条件的InetAddress
     * @return
     * @throws Exception
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result  = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        // 遍历所有网络接口
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface iface = networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            Enumeration<InetAddress> inetAddresses = iface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddr = inetAddresses.nextElement();
                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }
    /**
     * 获取某个网络接口的Mac地址
     * @param
     * @return void
     */
    protected String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }
                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }
            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
            log.error("获取某个网络接口的Mac地址异常", e.getMessage());
        }
        return null;
    }
}
