package org.myjssh.service;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.myjssh.config.properties.SshProperties;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class SSHService {
    static SshProperties sshProperties;

    static {
        //读取配置文件
        try {
            // 获取hive.properties文件的路径
            InputStream is = SSHService.class.getClassLoader().getResourceAsStream("SSH.yml");
            BufferedInputStream in = new BufferedInputStream(is);
            Yaml props = new Yaml();
            sshProperties = props.<SshProperties>loadAs(in, SshProperties.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sshRun() {
        JSch jsch = new JSch();
        Session session = null;
        try {
//            if (sshPropertiespem_file_path != null) {
//                jsch.addIdentity(pem_file_path);
//            }
            session = jsch.getSession(sshProperties.getSsh().getUser(), sshProperties.getSsh().getHost(), sshProperties.getSsh().getPort());
            session.setPassword(sshProperties.getSsh().getPassword());

            session.setConfig("StrictHostKeyChecking", "no");
            // step1：建立ssh连接
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            //step2： 设置SSH本地端口转发，本地转发到远程
            for(SshProperties.DbInfo db : sshProperties.getDbs()){
                int assinged_port = session.setPortForwardingL(db.getLport(), db.getRhost(), db.getRport());
                System.out.println("localhost:" + assinged_port + " -> " + db.getRhost() + ":" + db.getRport());
            }

        } catch (Exception e) {
            if (null != session) {
                //关闭ssh连接
                session.disconnect();
            }
            e.printStackTrace();
        }
    }
}
