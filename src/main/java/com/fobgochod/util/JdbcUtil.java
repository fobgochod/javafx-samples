package com.fobgochod.util;

import com.fobgochod.domain.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author zhouxiao
 * @date 2019/5/17
 */
public class JdbcUtil {

    public static final String hash = "20190520";
    private static final int len = 1024;

    private static Connection conn;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;


    public static Connection getConnection(String url, String username, String password) {
        try {
            // 1、加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2、得到连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void data(String url, String username, String password) {
        List<Role> roles = new ArrayList<>();
        List<String> inserts = new ArrayList<>();

        List<String> policies = new ArrayList<>();
        List<String> policyOnRoles = new ArrayList<>();
        List<String> statements = new ArrayList<>();
        List<String> statementTargets = new ArrayList<>();

        conn = getConnection(url, username, password);

        try {
            ps = conn.prepareStatement("select sid, tenant_sid, id, name from role where id='endUser' and sid not in (41325034943040,41319959827008)");
            rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setSid(rs.getLong(1));
                role.setTenantSid(rs.getLong(2));
                role.setId(rs.getString(3));
                role.setName(rs.getString(4));
                roles.add(role);
            }

            for (Role role : roles) {

                long policySid = SnowFlake.getInstance().newId();
                long statementAllowSid = SnowFlake.getInstance().newId();

                String insertPolicy = String.format("insert into policy(sid, hash, tenant_sid, id, name) values(%s, '%s', %s, 'role_endUser_DigiwinCloud', 'role_endUser_DigiwinCloud');", policySid, hash, role.getTenantSid());
                String insertPolicyOnRole = String.format("inset into policyonrole(sid, hash, policy_sid, role_sid) values(%s, '%s', %s, %s);", SnowFlake.getInstance().newId(), hash, policySid, role.getSid());
                String insertStatementAllow = String.format("insert into statement(sid, hash, effect, policy_sid) values(%s, '%s', 'allow', %s);", statementAllowSid, hash, policySid);
                String insertStatementDeny = String.format("insert into statement(sid, hash, effect, policy_sid) values(%s, '%s', 'deny', %s);", SnowFlake.getInstance().newId(), hash, policySid);
                String insertStatementTargetModule = String.format("insert into statementtarget(sid, hash, is_cascade, statement_sid, target_sid, type) values(%s, '%s', 0, %s, 42452332737088, 'module');", SnowFlake.getInstance().newId(), hash, statementAllowSid);
                String insertStatementTargetAction = String.format("insert into statementtarget(sid, hash, is_cascade, statement_sid, target_sid, type) values(%s, '%s', 0, %s, 42452332794432, 'action');", SnowFlake.getInstance().newId(), hash, statementAllowSid);

                inserts.add(insertPolicy);
                inserts.add(insertPolicyOnRole);
                inserts.add(insertStatementAllow);
                inserts.add(insertStatementDeny);
                inserts.add(insertStatementTargetModule);
                inserts.add(insertStatementTargetAction);


                policies.add(String.format("(%s, '%s', %s, 'role_endUser_DigiwinCloud', 'role_endUser_DigiwinCloud'),", policySid, hash, role.getTenantSid()));
                policyOnRoles.add(String.format("(%s, '%s', %s, %s),", SnowFlake.getInstance().newId(), hash, policySid, role.getSid()));
                statements.add(String.format("(%s, '%s', 'allow', %s),", statementAllowSid, hash, policySid));
                statements.add(String.format("(%s, '%s', 'deny', %s),", SnowFlake.getInstance().newId(), hash, policySid));
                statementTargets.add(String.format("(%s, '%s', 0, %s, 42452332737088, 'module'),", SnowFlake.getInstance().newId(), hash, statementAllowSid));
                statementTargets.add(String.format("(%s, '%s', 0, %s, 42452332794432, 'action'),", SnowFlake.getInstance().newId(), hash, statementAllowSid));
            }

            Map<String, String> mapSql = new HashMap<>(4);
            mapSql.put("policy", "insert into policy(sid, hash, tenant_sid, id, name) values");
            mapSql.put("policyonrole", "insert into policyonrole(sid, hash, policy_sid, role_sid) values");
            mapSql.put("statement", "insert into statement(sid, hash, effect, policy_sid) values");
            mapSql.put("statementtarget", "insert into statementtarget(sid, hash, is_cascade, statement_sid, target_sid, type) values");

            Map<String, List<String>> map = new HashMap<>(4);
            map.put("policy", policies);
            map.put("policyonrole", policyOnRoles);
            map.put("statement", statements);
            map.put("statementtarget", statementTargets);

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                int i = 0;
                StringBuilder sb = new StringBuilder();

                for (String policy : entry.getValue()) {
                    sb.append("\n");
                    if (i % len == 0) {
                        sb.append(mapSql.get(entry.getKey()));
                        sb.append("\n");
                    }
                    sb.append(policy);
                    i++;
                    if (i % len == 0) {
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append(";");
                        sb.append("\n\n");
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(";");
                FileUtil.write(FileUtil.getFile(String.format("C:\\Users\\Seven\\Desktop\\%s.sql", "endUser")), sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
