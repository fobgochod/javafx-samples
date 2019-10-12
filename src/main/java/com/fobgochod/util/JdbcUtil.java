package com.fobgochod.util;

import com.fobgochod.domain.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author zhouxiao
 * @date 2019/5/17
 */
public class JdbcUtil {

    private static final String hash = "20191012";
    private static final String targetId = "superUser";
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

    public static void initSql(String url, String username, String password, String filePath) {
        List<Role> roles = new ArrayList<>();
        List<String> inserts = new ArrayList<>();

        List<String> policies = new ArrayList<>();
        List<String> policyOnRoles = new ArrayList<>();
        List<String> statements = new ArrayList<>();
        List<String> statementTargets = new ArrayList<>();

        conn = getConnection(url, username, password);

        try {
            ps = conn.prepareStatement(String.format("select sid, tenant_sid, id, name from role where id = '%s'", targetId));
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

                String insertPolicy = String.format("insert into policy(sid, hash, tenant_sid, id, name) values(%s, '%s', %s, 'role_%s_DigiwinCloud', 'role_%s_DigiwinCloud');", policySid, hash, role.getTenantSid(), targetId, targetId);
                String insertPolicyOnRole = String.format("inset into policyonrole(sid, hash, policy_sid, role_sid) values(%s, '%s', %s, %s);", SnowFlake.getInstance().newId(), hash, policySid, role.getSid());
                String insertStatementAllow = String.format("insert into statement(sid, hash, effect, policy_sid) values(%s, '%s', 'allow', %s);", statementAllowSid, hash, policySid);
                String insertStatementDeny = String.format("insert into statement(sid, hash, effect, policy_sid) values(%s, '%s', 'deny', %s);", SnowFlake.getInstance().newId(), hash, policySid);
                String insertStatementTargetModule = String.format("insert into statementtarget(sid, hash, is_cascade, statement_sid, target_sid, type) values(%s, '%s', 0, %s, 43143759843904, 'module');", SnowFlake.getInstance().newId(), hash, statementAllowSid);
                String insertStatementTargetAction = String.format("insert into statementtarget(sid, hash, is_cascade, statement_sid, target_sid, type) values(%s, '%s', 0, %s, 43143760798272, 'action');", SnowFlake.getInstance().newId(), hash, statementAllowSid);

                inserts.add(insertPolicy);
                inserts.add(insertPolicyOnRole);
                inserts.add(insertStatementAllow);
                inserts.add(insertStatementDeny);
                inserts.add(insertStatementTargetModule);
                inserts.add(insertStatementTargetAction);


                policies.add(String.format("(%s, '%s', %s, 'role_%s_DigiwinCloud', 'role_%s_DigiwinCloud'),", policySid, hash, role.getTenantSid(), targetId, targetId));
                policyOnRoles.add(String.format("(%s, '%s', %s, %s),", SnowFlake.getInstance().newId(), hash, policySid, role.getSid()));
                statements.add(String.format("(%s, '%s', 'allow', %s),", statementAllowSid, hash, policySid));
                statements.add(String.format("(%s, '%s', 'deny', %s),", SnowFlake.getInstance().newId(), hash, policySid));
                statementTargets.add(String.format("(%s, '%s', 0, %s, 43143759843904, 'module'),", SnowFlake.getInstance().newId(), hash, statementAllowSid));
                statementTargets.add(String.format("(%s, '%s', 0, %s, 43143760798272, 'action'),", SnowFlake.getInstance().newId(), hash, statementAllowSid));
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

            printSqlFile(mapSql, map, filePath);
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

    public static void initSql2(String url, String username, String password, String filePath) {
        List<Role> roles = new ArrayList<>();

        List<String> policies = new ArrayList<>();
        List<String> policyOnRoles = new ArrayList<>();
        List<String> statements = new ArrayList<>();

        conn = getConnection(url, username, password);
        try {
            ps = conn.prepareStatement(String.format("select sid, tenant_sid, id, name from role where id = '%s' and sid = 43058565358144", targetId));
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
                policies.add(String.format("(%s, '%s', %s, 'role_%s_DigiwinCloud', 'role_%s_DigiwinCloud', 1984619653),", policySid, hash, role.getTenantSid(), targetId, targetId));
                policyOnRoles.add(String.format("(%s, '%s', %s, %s),", SnowFlake.getInstance().newId(), hash, policySid, role.getSid()));
                statements.add(String.format("(%s, '%s', 0, 'allow', %s, 43143760798272, 'action'),", SnowFlake.getInstance().newId(), hash, policySid));
            }
            Map<String, String> mapSql = new LinkedHashMap<>(3);
            mapSql.put("policy", "insert into policy(sid, hash, tenant_sid, id, name, sys_sid) values");
            mapSql.put("policyonrole", "insert into policyonrole(sid, hash, policy_sid, role_sid) values");
            mapSql.put("statement", "insert into statement(sid, hash, is_cascade, effect, policy_sid, target_sid, type) values");

            Map<String, List<String>> mapValue = new LinkedHashMap<>(3);
            mapValue.put("policy", policies);
            mapValue.put("policyonrole", policyOnRoles);
            mapValue.put("statement", statements);

            printSqlFile(mapSql, mapValue, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printSqlFile(Map<String, String> mapSql, Map<String, List<String>> mapValue, String filePath) {
        for (Map.Entry<String, List<String>> entry : mapValue.entrySet()) {
            int i = 0;
            StringBuilder batchSql = new StringBuilder();

            for (String value : entry.getValue()) {
                batchSql.append("\n");
                if (i % len == 0) {
                    batchSql.append(mapSql.get(entry.getKey()));
                    batchSql.append("\n");
                }
                batchSql.append(value);
                i++;
                if (i % len == 0) {
                    batchSql.deleteCharAt(batchSql.length() - 1);
                    batchSql.append(";");
                    batchSql.append("\n\n");
                }
            }
            batchSql.deleteCharAt(batchSql.length() - 1);
            batchSql.append(";");
            FileUtil.write(FileUtil.getFile(filePath), batchSql.toString());
        }
    }
}
