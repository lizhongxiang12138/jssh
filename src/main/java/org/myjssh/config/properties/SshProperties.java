package org.myjssh.config.properties;


import lombok.*;

import java.util.List;

@Getter
@Setter
public class SshProperties {

    private Ssh ssh;
    private List<DbInfo> dbs;

    @Getter
    @Setter
    public static class Ssh{
        private String host;
        private Integer port;
        private String user;
        private String password;
    }

    @Getter
    @Setter
    public static class DbInfo {
        private String rhost;
        private Integer rport;
        private Integer lport;
    }
}
