package nat.phc.blog.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 09:12 2021/2/23
 */
public class UdpAndTcpUtils {

    /**
     * 出站的配置
     */
    @Bean
    @ServiceActivator(inputChannel = "udpOut")
    public UnicastSendingMessageHandler handler() {
        return new UnicastSendingMessageHandler("localhost", 11111);
    }

    /**
     * 入站配置
     */
    @Bean
    public UnicastReceivingChannelAdapter upIn() {
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(11111);
        adapter.setOutputChannelName("udpChannel");
        return adapter;
    }
}
