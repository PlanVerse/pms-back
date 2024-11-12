package seg.playground.pms_back.common.config.support;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import java.util.Properties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Setter
@Component
@Profile({"local", "dev", "development"})
@ConfigurationProperties(prefix = "ssh")
public class SshTunnelingInitializer {

    private String host;
    private String username;
    private String password;
    private Integer port;
    private Session session;

    @PreDestroy
    public void close() {
        if (this.session != null && this.session.isConnected()) {
            this.session.disconnect();
        }
    }

    public void connect() {
        if (this.session != null && this.session.isConnected()) {
            return;
        }

        try {
            JSch jSch = new JSch();
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            this.session = jSch.getSession(username, host, port);
            this.session.setPassword(password);
            this.session.setConfig(config);
            this.session.connect();
        } catch (JSchException je) {
            this.close();
            je.printStackTrace();
            System.exit(1);
        }
    }
}